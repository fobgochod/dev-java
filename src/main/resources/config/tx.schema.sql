DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id      INT(11) NOT NULL,
    name    VARCHAR(40) NULL DEFAULT NULL,
    balance INT(11) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS book;
CREATE TABLE book
(
    id    INT(11) NOT NULL,
    name  VARCHAR(100) NULL DEFAULT NULL,
    price INT(11) NULL DEFAULT NULL,
    stock INT(11) NULL DEFAULT NULL,
    PRIMARY KEY (id)
);

INSERT INTO book (id, name, price, stock)
VALUES (1, '神秘岛', 100, 10);
INSERT INTO user (id, name, balance)
VALUES (2, 'zhangsan', 1000);
