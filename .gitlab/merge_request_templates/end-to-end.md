# Merge Request
**Issue:** 

# Checklist
* Did you read through your own code before submitting: false
* Do you have 100% method coverage with testing if applicable: false
* Do you have a minimum of 85% line coverage with testing: false
* Did you fix all checkstyle errors: false
* Did you try out your own code, e.g. use your own GUI/functionality: false
* Did you do a spell check if applicable: false
* Did you manage your imports properly: false
* Did you leave a comment on your issue: false
* Are you merging to the right branch: false
* Should the source branch be deleted: false

# Details
*Please specify any details regarding your merge request*

# Branch used
*Please specify branch used*

# How to test
*Please specify how reviewers should test your functionality*

# End-to-end testing:

# Functions:
- Building admin account
  - Login to `building_admin@tudelft.nl` with password `1234`
  - Add a new building
  - Update the building's attributes, delete it, add again
  - Add a new room
  - Update the room's attributes, delete it, add again
  - Try to add a new restaurant / bike (should not work)
  
- Bike admin account
  - Login to `bike_admin@tudelft.nl` with password `1234`
  - *Add new bikes*
  - *Update some bike's attributes, delete it, add again*
  - Try to add a new restaurant / building (should not work)

- Restaurant account
  - Login to `restaurant@tudelft.nl` with password `1234`
  - *Add a new restaurant*
  - *Update the restaurant's attributes, delete it, add again*
  - *Add a new menu*
  - *Update the menu's attributes, delete it, add again*
  - *Add a new dish*
  - *Update the dishes attributes, delete it, add again*
  - *Add a new allergy*
  - *Update the allergies attributes, delete it, add again*
  - Try to add a new bike / building (should not work)

- Admin account
  - Login to `admin@tudelft.nl` with password `1234`
  - Add a new building
  - Update the building's attributes, delete it, add again
  - Add a new room
  - Update the room's attributes, delete it, add again
  - *Add new bikes*
  - *Update some bike's attributes, delete it, add again*
  - *Add a new restaurant*
  - *Update the restaurant's attributes, delete it, add again*
  - *Add a new menu*
  - *Update the menu's attributes, delete it, add again*
  - *Add a new dish*
  - *Update the dishes attributes, delete it, add again*
  - *Add a new allergy*
  - *Update the allergies attributes, delete it, add again*

- Normal user:
  - Register
  - Login to the account
  - Activate the account using the confirmation code sent to the email
  - Check the user information in the profile
  - Change the password
  - Login to the account again
  - *Reserve a room*
  - *Book a bike*
  - *Order food*
  - *See your own reservations*
  - *Look at the reservations in the calendar*
  - *Add a custom event to the calendar*
  - *Share the custom event with other users*
  - Logout
  - Use the "Forgot password" functionality
  - Login using the password sent to the email

# Conditions
- Building admin account
  - Login to `building_admin@tudelft.nl` with password `1234`
  - Add a new building 
    - [ ] Warning when missing a field
    - [ ] Error when the house number is not an integer
    - [ ] Successfully added alert
    - [ ] Name already exists error
  - Update the building's attributes, delete it, add again
    - [ ] All of building's fields can be changed to other valid values
    - [ ] Warning when the house number is not an integer
    - [ ] Name already exists error
  - Add a new room
    - [ ] Warning when missing a field
    - [ ] Error when the capacity or plugs is not an integer
    - [ ] Error when faculty specific, screen or projector is not a boolean
    - [ ] Only limited to valid buildings
    - [ ] Successfully added alert
    - [ ] Name already exists error
  - Update the room's attributes, delete it, add again
    - [ ] All of room's fields can be changed to other valid values
    - [ ] Warning when the capacity or plugs is not an integer
    - [ ] Warning when faculty specific, screen or projector is not a boolean
    - [ ] Only limited to valid buildings
    - [ ] Name already exists error
  - Try to add a new restaurant / bike (should not work)
    - [ ] Should have an "Unauthorized" warning

- Bike admin account
  - Login to `bike_admin@tudelft.nl` with password `1234`
  - *Add new bikes*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid buildings*
    - [ ] *Error when the amount of bikes is not an integer*
    - [ ] *Successfully added alert*
  - *Update the bike's attributes, delete it, add again*
    - [ ] *All of bike's fields can be changed to other valid values*
    - [ ] *Only limited to valid buildings*
  - Try to add a new restaurant / building (should not work)
    - [ ] Should have an "Unauthorized" warning

- Restaurant account
  - Login to `restaurant@tudelft.nl` with password `1234`
  - *Add a new restaurant*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid buildings*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the restaurant's attributes, delete it, add again*
    - [ ] *All of restaurant's fields can be changed to other valid values*
    - [ ] *Only limited to valid buildings*
    - [ ] *Name already exists error*
  - *Add a new menu*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid restaurants*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the menu's attributes, delete it, add again*
    - [ ] *All of menu's fields can be changed to other valid values*
    - [ ] *Only limited to valid restaurants*
    - [ ] *Name already exists error*
  - *Add a new dish*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid menus*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the dishes attributes, delete it, add again*
    - [ ] *All of dishes fields can be changed to other valid values*
    - [ ] *Only limited to valid menus*
    - [ ] *Name already exists error*
  - *Add a new allergy*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid dishes*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the allergies attributes, delete it, add again*
    - [ ] *All of allergies fields can be changed to other valid values*
    - [ ] *Only limited to valid dishes*
    - [ ] *Name already exists error*
  - Try to add a new building / bike (should not work)
    - [ ] Should have an "Unauthorized" warning

- Admin account
  - Login to `admin@tudelft.nl` with password `1234`
  - Add a new building 
    - [ ] Warning when missing a field
    - [ ] Error when the house number is not an integer
    - [ ] Successfully added alert
    - [ ] Name already exists error
  - Update the building's attributes, delete it, add again
    - [ ] All of building's fields can be changed to other valid values
    - [ ] Warning when the house number is not an integer
    - [ ] Name already exists error
  - Add a new room
    - [ ] Warning when missing a field
    - [ ] Error when the capacity or plugs is not an integer
    - [ ] Error when faculty specific, screen or projector is not a boolean
    - [ ] Only limited to valid buildings
    - [ ] Successfully added alert
    - [ ] Name already exists error
  - Update the room's attributes, delete it, add again
    - [ ] All of room's fields can be changed to other valid values
    - [ ] Warning when the capacity or plugs is not an integer
    - [ ] Warning when faculty specific, screen or projector is not a boolean
    - [ ] Only limited to valid buildings
    - [ ] Name already exists error
  - *Add new bikes*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid buildings*
    - [ ] *Error when the amount of bikes is not an integer*
    - [ ] *Successfully added alert*
  - *Update the bike's attributes, delete it, add again*
    - [ ] *All of bike's fields can be changed to other valid values*
    - [ ] *Only limited to valid buildings*
  - *Add a new restaurant*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid buildings*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the restaurant's attributes, delete it, add again*
    - [ ] *All of restaurant's fields can be changed to other valid values*
    - [ ] *Only limited to valid buildings*
    - [ ] *Name already exists error*
  - *Add a new menu*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid restaurants*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the menu's attributes, delete it, add again*
    - [ ] *All of menu's fields can be changed to other valid values*
    - [ ] *Only limited to valid restaurants*
    - [ ] *Name already exists error*
  - *Add a new dish*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid menus*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the dishes attributes, delete it, add again*
    - [ ] *All of dishes fields can be changed to other valid values*
    - [ ] *Only limited to valid menus*
    - [ ] *Name already exists error*
  - *Add a new allergy*
    - [ ] *Warning when missing a field*
    - [ ] *Only limited to valid dishes*
    - [ ] *Successfully added alert*
    - [ ] *Name already exists error*
  - *Update the allergies attributes, delete it, add again*
    - [ ] *All of allergies fields can be changed to other valid values*
    - [ ] *Only limited to valid dishes*
    - [ ] *Name already exists error*

- Normal user
  - Register
    - [ ] Invalid email warning
    - [ ] TU Delft email only
    - [ ] Missing name / surname / faculty fields warnings
    - [ ] Passwords not matching
    - [ ] Successful register
  - Login to the account
    - [ ] Missing email or password error
    - [ ] Wrong password error
    - [ ] Successful login
  - Activate the account using the confirmation code sent to the email
    - [ ] Input the wrong code error
    - [ ] Successful activation
  - Check the user information in the profile
    - [ ] User information must match the one used when registering
  - Change the password
    - [ ] Passwords do not match error
    - [ ] Successful change, should be logged out automatically
  - Login to the account again
    - [ ] Unsuccessful login with the old password
    - [ ] Successful login with the new password
  - *Reserve a room*
    - [ ] *Missing fields warning*
    - [ ] *Only limited to valid rooms*
    - [ ] *Successful reservation*
  - *Book a bike*
    - [ ] *Missing fields warning*
    - [ ] *Only limited to valid locations*
    - [ ] *Successful reservation*
  - *Order food*
    - [ ] *Missing fields warning*
    - [ ] *Only limited to valid restaurants, dishes, allergies*
    - [ ] *Successful order*
  - *See your own reservations*
    - [ ] *Lists all current orders and reservations*
  - *Look at the reservations in the calendar*
    - [ ] *The orders and reservations are correctly represented in the calendar view*
  - *Add a custom event to the calendar*
    - [ ] *An event can be added to the calendar*
  - *Share the custom event with other users*
    - [ ] *Share functionality works*
  - Logout
  - Use the "Forgot password" functionality
    - [ ] Missing fields warning
    - [ ] Invalid email warning
    - [ ] Successful password reset
  - Login using the password sent to the email
    - [ ] Unsuccessful login with the old password
    - [ ] Successful login with the new password sent in the email