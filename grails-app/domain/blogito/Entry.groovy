package blogito

class Entry {

	String title
	String summary
	String filename  
//  byte[] file       this will save the file to the database	
	Date dateCreated
	Date lastUpdated
	
    static constraints = {
		title()
		summary(maxSize:1000)
		filename(blank:true, nullable:true)
		dateCreated()
		lastUpdated()
    }
	
	static mapping = {
		sort "lastUpdated":"desc"
	}
	
	static belongsTo = [author:User]
	
	static searchable = true
	
	String toString() {
		"${title} (${lastUpdated})"
	}
}
