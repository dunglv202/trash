CREATE TABLE `recipients` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) NOT NULL,
    `phone_number` VARCHAR(20) NOT NULL,
    `address` VARCHAR(100) NOT NULL
);

CREATE TABLE `orders` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `recipient_id` INT NOT NULL,
    `additional_notes` TEXT,
    `payment_method` VARCHAR(80) NOT NULL,
    `date_created` TIMESTAMP,
    `status` VARCHAR(150) NOT NULL,
    `user_id` INT NOT NULL,

    CONSTRAINT `fk_orders_recipients` FOREIGN KEY (`recipient_id`) REFERENCES `recipients` (`id`),
    CONSTRAINT `fk_orders_users` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);

CREATE TABLE `order_items` (
    `id` INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    `product_id` INT NOT NULL,
    `quantity` INT NOT NULL,
    `order_id` INT NOT NULL,

    CONSTRAINT `fk_order_items_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
    CONSTRAINT `fk_order_items_orders` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
);