### DB Updates

             DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion` (
`Promo_ID` INT NOT NULL AUTO_INCREMENT,
`Active` INT,
`Created_Date` TEXT,
`Created_Time` TEXT,
PRIMARY KEY(`Promo_ID`)
);

            DROP TABLE IF EXISTS `promotion_items`;
CREATE TABLE `promotion_items` (
`Promo_ID` INT NOT NULL AUTO_INCREMENT,
`Item_ID` TEXT,
`Active` INT,
`Created_Date` TEXT,
`Created_Time` TEXT,
PRIMARY KEY(`Promo_ID`)
);
