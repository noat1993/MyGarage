

from firebase import firebase

FIREBASE_URL = r'https://mygarage-2e104.firebaseio.com'

fb = firebase.FirebaseApplication(FIREBASE_URL, None)

# insert one customer
fb.patch('/Customers', { "123" : { "id_number" : "123", "city":"tel aviv","email":"myemail@gmail.com","fname":"tal","lname":"avi","phone":"187263876"}})


# insert one employee that is admin
fb.patch('/Employes', { "6iNvKrpRjYg6CFYGJOmBwq1psuW2" : { "city":"tel aviv","email":"myemail@gmail.com","employee_id":"1","fname":"tal","id_number":"123","lname":"guy","phone":"187263876", "is_admin" : "true"}})

# insert one employee that is not admin
fb.patch('/Employes', { "AAPAIwZCnNPXAZs7NE1qFcHGw953" : { "city":"tel aviv","email":"myemail@gmail.com","employee_id":"2","fname":"tal","id_number":"123","lname":"guy","phone":"187263876", "is_admin" : "false"}})

# insert treatments to the employees
fb.patch('/Treatments', { "key1" : {
  "car_number" : "1",
  "customerID" : "1",
  "date" : "1",
  "employee_id" : "1",
  "issue" : "1",
  "price" : "1"
}})

fb.patch('/Treatments', { "key2" : {
  "car_number" : "2",
  "customerID" : "2",
  "date" : "2",
  "employee_id" : "2",
  "issue" : "2",
  "price" : "2"
}})

