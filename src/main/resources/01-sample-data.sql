
--Test data with username:'email' and password: password
INSERT INTO employee (name, email, password, role, age)
VALUES
  ('Juan', 'juan@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'backend', 30),
  ('Ana', 'ana@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'frontend', 28),
  ('Miguel', 'miguel@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'backend', 35),
  ('Elena', 'elena@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'frontend', 32),
  ('David', 'david@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'backend', 28),
  ('Sofía', 'sofia@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'frontend', 37),
  ('Alex', 'alex@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'backend', 30),
  ('Olivia', 'olivia@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'frontend', 29),
  ('Daniel', 'daniel@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'backend', 34),
  ('Sara', 'sara@gmail.com', '$2a$12$BlrFkNCXquIg9I/9etjsLOdTtehUACQkvwktwSPT3z.eUyyd4dHKy', 'frontend', 31);


INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('Inception', 2020, 'Christopher Nolan', 'Sci-fi', 148, null, 8.8, 1, 2);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('Tenet', 2020, 'Christopher Nolan', 'Action', 150, null, 7.5, 2, 1);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('Soul', 2020, 'Pete Docter', 'Animation', 100, null, 8.1, 1, 2);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('Dune', 2021, 'Denis Villeneuve', 'Sci-fi', 155, null, 8.9, 6, 1);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('No Time to Die', 2021, 'Cary Joji Fukunaga', 'Action', 163, null, 7.4, 1, 2);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('Cruella', 2021, 'Craig Gillespie', 'Crime', 134, null, 7.4, 2, 1);


INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('Stranger Things', 2020, 'Duffer Brothers', 'Drama, Fantasy, Horror', null, 3, 8.7, 1, 4);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('The Mandalorian', 2020, 'Jon Favreau', 'Action, Adventure, Fantasy', null, 2, 8.7, 5, 1);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('Loki', 2021, 'Michael Waldron', 'Action, Adventure, Fantasy', null, 1, 8.5, 8, 2);

INSERT INTO content (title, year, director, genre, length_minutes, seasons, rating, id_proposed_by_employee, id_registered_by_employee)
VALUES ('The Witcher', 2021, 'Lauren Schmidt Hissrich', 'Action, Adventure, Drama', null, 2, 8.2, 8, 8);