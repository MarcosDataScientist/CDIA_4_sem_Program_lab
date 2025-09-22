USE contact_management;


CREATE TABLE contacts (
	id BIGINT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(100) NOT NULL,
	phone VARCHAR(100) NOT NULL UNIQUE,
	email VARCHAR(150) NOT NULL UNIQUE,
    address VARCHAR(150),
    birth_date DATE
);