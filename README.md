DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
    `Pr-id` INT NOT NULL AUTO_INCREMENT,
    `Promo_ID` TEXT,
    `Active` INT,
    `Created_Date` TEXT,
    `Created_Time` TEXT,
    PRIMARY KEY (`Pr-id`)
);

DROP TABLE IF EXISTS `promotion_items`;
CREATE TABLE `promotion_items` (
    `Pr-id` INT NOT NULL AUTO_INCREMENT,
    `Promo_ID` TEXT,
    `Item_ID` TEXT,
    `Qty` DOUBLE,
    `Discount` DOUBLE,
    `DiscType` TEXT,
    PRIMARY KEY (`Pr-id`)
);
