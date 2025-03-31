USE `ait127810`;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(45) DEFAULT NULL,
  `product_price` varchar(45) DEFAULT NULL,
  `product_quantity` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

--
-- Data for table `employee`
--

INSERT INTO `product` VALUES 
	(1,'Iphone 16','4999.99','100'),
	(2,'Samsung S25','3499.99','150'),
	(3,'Samsung Buds 3','549.00','500')

