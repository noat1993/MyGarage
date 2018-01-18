

from firebase import firebase


FIREBASE_URL = r'https://mygarage-2e104.firebaseio.com'

def getUsername(username):
	fb = firebase.FirebaseApplication(FIREBASE_URL, None)
	return fb.get('/Customers/',username)

def checkUser():
	username = raw_input("Enter customer id:")
	result = getUsername(username)
	print "-----------------------------"
	if result:
		print "EXIST"
	else:
		print "DOES NOT EXIST"
	print "-----------------------------"

def showUsers():
	fb = firebase.FirebaseApplication(FIREBASE_URL, None)
	result = fb.get('/Customers', None)
	print "-----------------------------"
	if result:
		print "ID".ljust(20)+"Email".ljust(40)
		for v in result.values():
			print v['id_number'].ljust(20)+v['email'].ljust(40)
	else:
		print "No customers" 
	print "-----------------------------"

def removeAll():
	fb = firebase.FirebaseApplication(FIREBASE_URL, None)
	fb.delete('/','Customers')

def removeUser():
	username = raw_input("Enter id:")
	result = getUsername(username)
	if not result:
		print "-----------------------------"
		print "ERROR: The customer does not exist"
		print "-----------------------------"
		return
	fb = firebase.FirebaseApplication(FIREBASE_URL, None)
	fb.delete('/Customers/',username)

def addUser():
	username = raw_input("Enter id:")
	result = getUsername(username)
	if result:
		print "-----------------------------"
		print "ERROR: The customer is already exist"
		print "-----------------------------"
		return

	fb = firebase.FirebaseApplication(FIREBASE_URL, None)
	result = fb.patch('/Customers', { username : { "id_number" : username}})
	print "-----------------------------"
	if result:
		print "The customer "+result.keys()[0]+" has been added succesfuly"
	else:
		print "ERROR: Add customer failed, please try again"
	print "-----------------------------"

def setUserDetails():
	username = raw_input("Enter id:")
	result = getUsername(username)
	if not result:
		print "-----------------------------"
		print "ERROR: The id does not exist"
		print "-----------------------------"
		return

	city = raw_input("Enter city:")
	cid = raw_input("Enter id:")
	fname = raw_input("Enter first name:")
	lname = raw_input("Enter last name:")
	phone = raw_input("Enter phone:")
	while not phone.isdigit():
		print "ERROR: Phone number must be a number"
		phone = raw_input("Enter phone:")

	print "-----------------------------"
	print username
	print city
	print cid
	print fname
	print lname
	print phone
	print "-----------------------------"

	fb = firebase.FirebaseApplication(FIREBASE_URL, None)
	result = fb.patch('/Customers/'+username, { "city": city,"id_number" : cid , "fname" : fname, "lname" : lname, "phone" : phone})
	if result:
		print "Customer "+result.keys()[0]+" details has been set succesfuly"
	else:
		print "ERROR: Set customer details failed, please try again"

def printMenu():
	print "==============================================================="
	print "=========================== Commands =========================="
	print "==============================================================="
	print "add customer             add a new customer"
	print "remove customer          remove a customer"
	print "show customers           print all the customers"
	print "check customer           check if customer exist"
	print "remove all               remove all the customer"
	print "set details              set additional details for customer"
	print "exit                     exit program"
	print "==============================================================="

def main():
	while True:
		printMenu()
		cmd = raw_input("Enter command:\n")
		if cmd == "add customer":
			addUser()
		elif cmd == "remove customer":
			removeUser()
		elif cmd == "show customers":
			showUsers()
		elif cmd == "check customer":
			checkUser()
		elif cmd == "remove all customers":
			removeAll()
		elif cmd == "set details":
			setUserDetails()
		elif cmd == "exit":
			break
		else:
			print "Invalid command"


if __name__ == "__main__":
	main()