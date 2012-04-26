package blogito

import org.springframework.dao.DataIntegrityViolationException

class EntryController {

	def scaffold = Entry
	
	def beforeInterceptor = [action:this.&auth, except:["index", "list", "show", "atom", "search"]]
	
	def auth() {
	    if(!session.user) {
		    redirect(controller:"user", action:"login")
		    return false
		}
	}
	
	def search = {
		// render Entry.search(params.q, params)
		def searchResults = Entry.search(params.q, params)
		flash.message = "${searchResults.total} results found for search: ${params.q}"
		flash.q = params.q
		return [searchResults:searchResults.results, resultCount:searchResults.total]
	}

    def save = {
      def entryInstance = new Entry(params)
      entryInstance.author = User.get(session.user.id)
	  
	  // handle uploaded file
	  def uploadedFile = request.getFile('payload')
	  if(!uploadedFile.empty) {
        println "Class: ${uploadedFile.class}"
        println "Name: ${uploadedFile.name}"
        println "OriginalFileName: ${uploadedFile.originalFilename}"
        println "Size: ${uploadedFile.size}"
        println "ContentType: ${uploadedFile.contentType}"
		
		def webRootDir = servletContext.getRealPath("/")
		def userDir = new File(webRootDir, "/payload/${session.user.login}")
		userDir.mkdirs()
		uploadedFile.transferTo (new File(userDir, uploadedFile.originalFilename))
		entryInstance.filename = uploadedFile.originalFilename
	  }
	  
      if (!entryInstance.hasErrors() && entryInstance.save()) {
        flash.message = "Entry ${entryInstance.id} created"
        redirect(action:show,id:entryInstance.id)
      } else {
        render(view:'create',model:[entryInstance:entryInstance])
      }
    }
	
	def edit = {
		def entryInstance = Entry.get( params.id )
		
		//limit editing to the original author
		if (!checkAccess(flash, entryInstance, "Edit")) return
	
		if(!entryInstance) {
			flash.message = "Entry not found with id ${params.id}"
			redirect(action:list)
		} else {
			return [ entryInstance : entryInstance ]
		}
	}
	
	def list = {
		if(!params.max) params.max = 10
		flash.id = params.id
		if(!params.id) params.id = "No User Supplied"
		flash.title = params.title
		if(!params.title) params.title = ""
  
		def author = User.findByLogin(params.id)
		def entryList
		def entryCount
		if(author) {
		  def query = { 
			  and { 
				  eq('author', author)
			      like("title", params.title.decodeUnderscore() + '%')
		      }
		  }
		  entryList = Entry.createCriteria().list(params, query)
		  entryCount = Entry.createCriteria().count(query)
		} else {
		  entryList = Entry.list( params )
		  entryCount = Entry.count()
		}
		
		[ entryInstanceList:entryList, entryCount:entryCount  ]
	}
	
	def delete() {
		def entryInstance = Entry.get(params.id)

		if (!entryInstance) {
//			flash.message = message(code: 'default.not.found.message', args: [message(code: 'trip.label', default: 'Trip'), params.id])
			flash.message = "Entry not found with id ${params.id}"
			redirect(action: "list")
			return
		}
		
		//limit editing to the original author
		if (!checkAccess(flash, entryInstance, "delete")) return

		try {
			entryInstance.delete(flush: true)
			flash.message = "Entry deleted"
			redirect(action: "list")
		} catch (DataIntegrityViolationException e) {
			flash.message = "Failed to delete Entry"
			redirect(action: "show", id: params.id)
		}
	}

	def atom = {
		if(!params.max) params.max = 10
		def list = Entry.list(params)
		def lastUpdated = list[0].lastUpdated
		def server = request.getServerName()
		def port = request.getServerPort()
		def scheme = request.getScheme()
		[ entryInstanceList:list, lastUpdated:lastUpdated, scheme:scheme, port:port, server:server]
		
	}
	
	
	
	private checkAccess(Map flash, Entry entryInstance, String attemptedAction) {
		if( !(session.user.login == entryInstance.author.login) ){
			flash.message = "Sorry, you can only ${attemptedAction} your own entries."
			redirect(action:list)
			return false
		}
		return true
	}

}
