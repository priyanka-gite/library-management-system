INSERT INTO role (id, role_name)
VALUES (100, 'ROLE_ADMIN'),
       (101, 'ROLE_USER');


INSERT INTO users (id, username, email, password, address, mobile_number)
VALUES (100, 'admin', 'admin@test.com','$2a$10$mVK5sW5qfmYqh78Vbp8gMek4zSbMyH3amB8gmnun6h5fLGJjXoiPO','amsterdam','9100934983'), --admin123
       (101, 'tester','tester@test.com', '$2a$10$.UWm42OE8sEVP8rA.0sOC.Dwug4OEOjXLozPiuGIEeXx4tFdKSi46','utrecht','9100934984'); --test123

INSERT INTO user_role (role_id, user_id)
VALUES (100,100),
       (101,101);

INSERT INTO authors (id, email, gender, name)
VALUES (100,'jk.r@test.com','female','JK Rowling'),
       (101,'kh.h@test.com','male','Khalid Hosseini'),
       (102,'st.m@test.com','female','Stephanie Meyers');

INSERT INTO books (id, category, isbn, title, number_of_copies,number_of_copies_borrow)
VALUES (100, 'fantasy', '1338878921', 'Harry Potter', 5, 0),
       (101, 'novel','978-1-59448-950-1','A Thousand Splendid suns',10,0),
       (102, 'romance','978-0316015844','Twilight 1',3,1),
       (103, 'romance','978-0316015845','Twilight 2',3,1);

INSERT INTO book_author(author_id, book_id)
VALUES (100, 100),
       (101, 101),
       (102, 102),
       (102, 103);

INSERT INTO subscriptions(id,start_date, end_date, max_book_limit, number_of_books_borrowed, subscription_type)
VALUES (100, '2024-01-01', '2024-12-31', 5, 2, 1);

update users
set subscription_id = 100
where id = 101;

INSERT INTO reservations(id,user_id, return_date, reservation_date, is_returned)
VALUES (100, 101, '2024-02-01','2024-02-15', false);

INSERT INTO book_reservation(book_id, reservation_id)
VALUES (102, 100),
       (103, 100);