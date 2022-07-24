
INSERT INTO app_role (role_id, role_name) VALUES (1, 'ADMIN');
INSERT INTO app_role (role_id, role_name) VALUES (2, 'USER');

-- password test1234
INSERT INTO app_user (user_id, first_name, last_name, password, username)
VALUES (1, 'Admin', 'Admin', '$2a$10$5AWyzymSnNypg9BkMOyKE.zA05GtRKHCoWimh.q2w.KAO5koBYPM6', 'admin.admin');
-- password test1234
INSERT INTO app_user (user_id, first_name, last_name, password, username)
VALUES (2, 'John', 'Doe', '$2a$10$5AWyzymSnNypg9BkMOyKE.zA05GtRKHCoWimh.q2w.KAO5koBYPM6', 'john.doe');

INSERT INTO user_role(user_id, role_id)
VALUES (1, 1);
INSERT INTO user_role(user_id, role_id)
VALUES (1, 2);
INSERT INTO user_role(user_id, role_id)
VALUES (2, 2);