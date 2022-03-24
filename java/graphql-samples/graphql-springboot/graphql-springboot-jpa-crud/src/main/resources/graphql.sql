DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS product;

CREATE TABLE `category`
(
    `id`   int unsigned NOT NULL AUTO_INCREMENT,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `product`
(
    `id`          int unsigned NOT NULL AUTO_INCREMENT,
    `name`        varchar(255) NOT NULL,
    `code`        varchar(255) NOT NULL,
    `price`       double       NOT NULL,
    `category_id` int unsigned NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;