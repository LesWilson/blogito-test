package blogito

class User {

  String login
  String password
  String name
  String role = "author"
  
  static constraints = {
    login(unique:true)
    password(password:true)
    name()
	role(inList:["author","admin"])
  }
  
  static hasMany = [entries:Entry]
  
  String toString() {
    name
  }
}
