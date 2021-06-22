CREATE table user (
id bigint NOT NULL primary key AUTO_INCREMENT,
user_id varchar(15) NOT NULL,
password varchar(10) NOT NULL,
name varchar(255) NOT NULL,
email varchar(255) NOT NULL
);