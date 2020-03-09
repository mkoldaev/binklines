CREATE TABLE `paras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(55) DEFAULT NULL,
  `status` varchar(55) DEFAULT NULL,
  `quoteAsset` varchar(20) DEFAULT NULL,
  `baseAsset` varchar(20) DEFAULT NULL,
  `ticksize` varchar(20) DEFAULT NULL,
  `stepsize` varchar(20) DEFAULT NULL,
  `minprice` varchar(20) DEFAULT NULL,
  `maxprice` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0;
