-- Drop existing tables if they exist
DROP TABLE IF EXISTS `promotion`;
DROP TABLE IF EXISTS `promotion_items`;

-- Create promotion table
CREATE TABLE `promotion` (
    `Pr-id` INT NOT NULL AUTO_INCREMENT,
    `Promo_ID` TEXT,
    `Active` INT,
    `Created_Date` TEXT,
    `Created_Time` TEXT,
    PRIMARY KEY (`Pr-id`)
);

-- Create promotion_items table
CREATE TABLE `promotion_items` (
    `Pr-id` INT NOT NULL AUTO_INCREMENT,
    `Promo_ID` TEXT,
    `Item_ID` TEXT,
    `Qty` DOUBLE,
    `Discount` DOUBLE,
    `DiscType` TEXT,
    PRIMARY KEY (`Pr-id`)
);
