package blogito

import java.text.SimpleDateFormat

class DateTagLib {
	
	public static final String INCOMING_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss"
	public static final String ATOM_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'-07:00'"
  
	def atomDate = {attrs, body ->
	  def b = attrs.body ?: body()
	  def d = new SimpleDateFormat(INCOMING_DATE_FORMAT).parse(b.toString())
	  out << new SimpleDateFormat(ATOM_DATE_FORMAT).format(d)
	}

	def longDate = {attrs, body ->
		//parse the incoming date
		def b = attrs.body ?: body()
		def d = new SimpleDateFormat(INCOMING_DATE_FORMAT).parse(b.toString())
			
		//if no format attribute is supplied, use this
//		def pattern = attrs["format"] ?: "EEEE, MMM d, yyyy"
//		def pattern = attrs["format"] ?: ATOM_DATE_FORMAT
		def pattern = ATOM_DATE_FORMAT
		out << new SimpleDateFormat(pattern).format(d)
	  }
	
}
