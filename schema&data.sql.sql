DROP DATABASE IF EXISTS  pay_my_buddy;
CREATE DATABASE pay_my_buddy;
USE pay_my_buddy;

CREATE TABLE user (
                id INT AUTO_INCREMENT NOT NULL,
                email VARCHAR(254) NOT NULL,
				password VARCHAR(64),
                first_name VARCHAR(50) NOT NULL,
                last_name VARCHAR(50) NOT NULL,
                balance DECIMAL(7,2) NOT NULL,
                PRIMARY KEY (id)
);


CREATE UNIQUE INDEX user_idx
 ON user
 ( email );

CREATE INDEX user_idx1
 ON user
 ( last_name ASC );

CREATE TABLE bank (
                iban VARCHAR(34) NOT NULL,
                name VARCHAR(50) NOT NULL,
                user_id INT NOT NULL,
                PRIMARY KEY (iban)
);


CREATE TABLE friendship (
                user_id INT NOT NULL,
                friend_id INT NOT NULL,
                PRIMARY KEY (user_id, friend_id)
);


CREATE TABLE transfer (
                id INT AUTO_INCREMENT NOT NULL,
                user_id INT NOT NULL,
                description VARCHAR(90) NOT NULL,
                amount DECIMAL(7,2) NOT NULL,
				charges DECIMAL(7,2),
                date DATETIME,
                PRIMARY KEY (id)
);


CREATE INDEX transfer_idx
 ON transfer
 ( date DESC );

CREATE TABLE friend_transfer (
                transfer_id INT NOT NULL,
                friend_id INT NOT NULL,
                PRIMARY KEY (transfer_id)
);


CREATE TABLE bank_transfer (
                transfer_id INT NOT NULL,
                iban VARCHAR(34) NOT NULL,
                PRIMARY KEY (transfer_id) 
);


ALTER TABLE transfer ADD CONSTRAINT user_transfer_fk
FOREIGN KEY (user_id)
REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE friendship ADD CONSTRAINT user_frienship_fk
FOREIGN KEY (friend_id)
REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE friendship ADD CONSTRAINT user_frienship_fk1
FOREIGN KEY (user_id)
REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE bank ADD CONSTRAINT user_bank_fk
FOREIGN KEY (user_id)
REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE friend_transfer ADD CONSTRAINT user_friend_transfer_fk
FOREIGN KEY (friend_id)
REFERENCES user (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE bank_transfer ADD CONSTRAINT bank_bank_transfer_fk
FOREIGN KEY (iban)
REFERENCES bank (iban)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE bank_transfer ADD CONSTRAINT transfer_bank_transfer_fk
FOREIGN KEY (transfer_id)
REFERENCES transfer (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE friend_transfer ADD CONSTRAINT transfer_friend_transfer_fk
FOREIGN KEY (transfer_id)
REFERENCES transfer (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

INSERT INTO user (email, password, first_name, last_name, balance)
VALUES ('mail1@bla.org', '$2y$10$aupWKZpYlSZl.yS25xRqaOwzPvwugVVBGYL/9jlDn8B80IsN4hHsS', 'first1', 'last1', 50),
('mail2@bla.org', '$2y$10$ZVUd8sAl3I7LPgB.GcTNVeFpdNOpawaWHtuhyiuj9t0w7KXllIZR6', 'first2', 'last2', 0),
('mail3@bla.org', '$2y$10$L5r1Ohwg6rkL1kSODbJVO.p5W1EfzSbyaaCdFkNGSpOvT9O3x8Tfi', 'first3', 'last3', 50),
('mail4@bla.org', '$2y$10$AesfrQDkgJ5DVR4V2/e6O.cnsxbYoe9LSikGdlTZnmJsW9FnyyMrG', 'first4', 'last4', 50),
('mail5@bla.org', '$2y$10$Jn0dxynMO5jV/4xD8Q8fOeElhH2ulvG8LP/lNE3p.OICVpt5QPS6u', 'first5', 'last5', 50);

INSERT INTO bank (user_id, iban, name)
VALUES (1, '11iban', 'bank11'),
(1, '12iban', 'bank12'),
(2, '21iban', 'bank21'),
(3, '31iban', 'bank31'),
(3, '32iban', 'bank32'),
(3, '33iban', 'bank33'),
(4, '41iban', 'bank41'),
(5, '51iban', 'bank51');

INSERT INTO friendship (user_id, friend_id)
VALUES (1, 3),
(1, 5),
(2, 5),
(4, 2),
(4, 5);

INSERT INTO transfer (user_id, description, amount, date)
VALUES (1, 'deposit', 3000, '2020-10-17'),
(1, 'birthday gift to first5', 50, '2020-10-17'),
(2, 'deposit', 700, '2021-01-01 12:13:03'),
(3, 'deposit', 100, '2021-03-01 12:17:03'),
(5, 'withdrawal', -50, '2021-03-01 15:17:03'),
(2, 'cine refund', 13, '2021-04-01 15:17:03'),
(2, 'withdrawal', -300, '2021-04-21 15:17:03'),
(4, 'coffee refund', 1, '2021-04-21 15:17:03'),
(1, 'transfer2', 50, '2020-10-11'),
(1, 'transfer3', 50, '2020-10-13'),
(1, 'transfer4', 50, '2020-10-18'),
(1, 'transfer5', 50, '2020-10-12'),
(1, 'transfer6', 50, '2020-10-16'),
(1, 'transfer7', 50, '2020-10-11'),
(1, 'transfer8', 50, '2020-10-11');

INSERT INTO bank_transfer (transfer_id, iban)
VALUES (1, '12iban'),
(3, '21iban'),
(4, '33iban'),
(5, '51iban'),
(7, '21iban');

INSERT INTO friend_transfer (transfer_id, friend_id)
VALUES (2, 5),
(6, 4),
(8, 2),
(9, 2),
(10, 2),
(11, 2),
(12, 2),
(13, 2),
(14, 2),
(15, 2);