CREATE TABLE IF NOT EXISTS todo (
    id INT(10) unsigned NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(30) NOT NULL
);

DROP TABLE IF EXISTS account;
CREATE TABLE account (
    username varchar(100) NOT NULL PRIMARY KEY,
    password varchar(100) NOT NULL
);

-- username: user, password: password
INSERT INTO account (username, password) VALUES ('user', '$2a$10$jgChVCwtacBuodAKDvC7T.31DVwEiyq3B9molk0wcXmVxJBJIxsnq');
-- username: account, password: password
INSERT INTO account (username, password) VALUES ('admin', '$2a$10$jgChVCwtacBuodAKDvC7T.31DVwEiyq3B9molk0wcXmVxJBJIxsnq')