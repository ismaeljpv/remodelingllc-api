INSERT INTO company (description, email, phone_number, name, location) VALUES ('We are a family business of real estate remodeling that has been working in the community of Madison and its surroundings since 2020.', 'remodelingllc24x7@gmail.com', '+584129984536', 'Remodeling 24x7 LLC', 'A108 Maddison, New York, NY 53502232');
INSERT INTO goal (description, company_id) VALUES ('Our goal is to achieve excellence with each of our clients through an great quality service and communication', 1);
INSERT INTO goal (description, company_id) VALUES ('We have a qualified and experienced team that is here to serve any remodeling requirement', 1);
INSERT INTO goal (description, company_id) VALUES ('In our business, our clients are our main priority, therefore we will always try to satisfy all their needs to achieve success', 1);

INSERT INTO user (username, password, last_login, status, firstname, lastname, email) VALUES ('admin', '$2a$10$cbN5tfrOvwm2EUDWjYYc0.y.3gopATIJLZf1SoAHTGzjEx77Ft2aq', '2021-07-25 17:03:08', 'A', 'Ismael', 'Pena', 'ismaeljpv14@gmail.com');

INSERT INTO role (role, status) VALUES ('ADMIN', 'A');
INSERT INTO role (role, status) VALUES ('MAINTAINER', 'A');

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);

INSERT INTO post (title, description, tags, user_id, status, thumbnail_extension, project_date, client) VALUES ('Desk Construction', 'The Jhonson Family wanted a desk for their house an we did our best to give then the perfect desk for it. ', 'Construction;Desk', 1, 'A', 'image/jpeg', '2021-06-01 00:00:00', 'Jhonson Family');
INSERT INTO post (title, description, tags, user_id, status, thumbnail_extension, project_date, client) VALUES ('Front Yard Entrance', 'This front courtyard was made for the Storm Family who wanted to elevate their front entrance to a new level of style and we give then what they was looking for.', 'Construction;Remodeling;Courtyard', 1, 'A', 'image/jpeg', '2021-06-02 00:00:00', 'Storm Family');
INSERT INTO post (title, description, tags, user_id, status, thumbnail_extension, project_date, client) VALUES ('Balcony', 'The Crinsom''s wanted a balconie for their second floor and we deliver some of our best work.', 'Remodeling;2floor;Balcony', 1, 'A', 'image/jpeg', '2021-06-02 00:00:00', 'Crinsom Family');

INSERT INTO services (description, service, status, thumbnail_extension) VALUES ('We build your upper and lower decks fitting any requirement.', 'Deck', 'A', 'image/jpeg');
INSERT INTO services (description, service, status, thumbnail_extension) VALUES ('We design the perfect courtyard for your house. ', 'Courtyards', 'A', 'image/jpeg');