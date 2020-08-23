
CREATE TABLE `kline_1d` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `max_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `low_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `volume` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `count_trades` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `open_milliseconds` bigint(15) NOT NULL,
  `close_milliseconds` bigint(15) NOT NULL,
  `price_avg` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `kline_1h` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `max_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `low_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `volume` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `count_trades` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `open_milliseconds` bigint(15) NOT NULL,
  `close_milliseconds` bigint(15) NOT NULL,
  `price_avg` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `kline_1m` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `max_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `low_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `volume` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `count_trades` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `open_milliseconds` bigint(15) DEFAULT NULL,
  `close_milliseconds` bigint(15) NOT NULL,
  `price_avg` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `kline_1w` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `time_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_open` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `max_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `low_price` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `price_close` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  `volume` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `count_trades` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `open_milliseconds` bigint(15) DEFAULT NULL,
  `close_milliseconds` bigint(15) NOT NULL,
  `price_avg` varchar(45) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

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
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4;
