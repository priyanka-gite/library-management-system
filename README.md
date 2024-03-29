# library-management-system

Backend Project

Our Library Management System is a user-friendly and feature-rich application designed to
revolutionize the way libraries operate and engage with their users. This digital solution is crafted
to streamline library tasks, enhance user experiences, and create an efficient ecosystem for both
librarians and readers. The application provides a centralized and organized catalogue of all
library resources, enabling quick and easy navigation through an extensive collection of books,
journals. With an intuitive and user-friendly interface, users can effortlessly explore the library's
offerings, search for materials, and access a wealth of information at their fingertips.

In this upgraded system, everyone who uses the library will have their own account. This
personal account will make it easier for people to handle their library-related tasks. The main aim
is to fix the issues we face with the current system and create a more user-friendly experience for
everyone who regularly borrows books from the library, including myself and fellow library-goers.
1. To rent a book from library a person has to be a subscriber.
2. There are two types of Subscription available (adult subscription & kid’s subscription).
3. Subscription will have date of start of subscription, date of ending of subscription or cancel the
   subscription.
4. An admin can add a subscriber(user) with name, age, contact details, payment details and
   type of subscription.
5. An admin can add and book with details like title, author, edition, number of copies, book
   category.
6. An admin can search a book and check whether a book is rented out to a subscriber or not, if
   it is rented out, an admin can see the name of the subscriber, admin can also change the status
   of the book depending on its rented or available.
7. A user can see his/her own personal details and subscription.
8. A user can cancel or take new subscription.
9. A user can search (title, author) and check whether a particular book is available or not.
10. A user can reserve a book if available.
11. When a book is reserved, reservation class will be created which will contain book details,
    details of renter, start date and renewal date.
12. Security: User (username, password) security/role (To determine the authorization within the
    app)

User Roles

There are two types of roles in this application User and Admin.
Users are the subscribers of the library. Admin are referred to the library staff. They both
have access to the specific endpoints which are mentioned below in the document.

ROLE_USER
Users are the subscribers of the library. They are able to make their own account and also
update their details. Users can reserve a book also update the reservation based on the type
of their subscription those are kids and adults’ subscription. On a kid’s subscription a user is
unable to reserve an adult book.

ROLE_ADMIN
Admins are the librarians. They have most of the access to the application. They are able to
add or update a book. See the reservation or update when the book is returned

Link to Github
https://github.com/priyanka-gite/library-management-system
