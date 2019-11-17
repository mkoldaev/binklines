CREATE TABLE `kline_15i` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) DEFAULT NULL,
  `time_open` varchar(45) DEFAULT NULL,
  `time_close` varchar(45) DEFAULT NULL,
  `price_open` varchar(45) DEFAULT NULL,
  `max_price` varchar(45) DEFAULT NULL,
  `low_price` varchar(45) DEFAULT NULL,
  `price_close` varchar(45) DEFAULT NULL,
  `volume` varchar(90) DEFAULT NULL,
  `count_trades` varchar(90) DEFAULT NULL,
  `open_milliseconds` bigint(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `kline_1d` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) DEFAULT NULL,
  `time_open` varchar(45) DEFAULT NULL,
  `time_close` varchar(45) DEFAULT NULL,
  `price_open` varchar(45) DEFAULT NULL,
  `max_price` varchar(45) DEFAULT NULL,
  `low_price` varchar(45) DEFAULT NULL,
  `price_close` varchar(45) DEFAULT NULL,
  `volume` varchar(90) DEFAULT NULL,
  `count_trades` varchar(90) DEFAULT NULL,
  `open_milliseconds` bigint(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=75026 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `kline_1h` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) DEFAULT NULL,
  `time_open` varchar(45) DEFAULT NULL,
  `time_close` varchar(45) DEFAULT NULL,
  `price_open` varchar(45) DEFAULT NULL,
  `max_price` varchar(45) DEFAULT NULL,
  `low_price` varchar(45) DEFAULT NULL,
  `price_close` varchar(45) DEFAULT NULL,
  `volume` varchar(90) DEFAULT NULL,
  `count_trades` varchar(90) DEFAULT NULL,
  `open_milliseconds` bigint(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97511 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `kline_1i` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) DEFAULT NULL,
  `time_open` varchar(45) DEFAULT NULL,
  `time_close` varchar(45) DEFAULT NULL,
  `price_open` varchar(45) DEFAULT NULL,
  `max_price` varchar(45) DEFAULT NULL,
  `low_price` varchar(45) DEFAULT NULL,
  `price_close` varchar(45) DEFAULT NULL,
  `volume` varchar(90) DEFAULT NULL,
  `count_trades` varchar(90) DEFAULT NULL,
  `open_milliseconds` int(13) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `kline_1m` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) DEFAULT NULL,
  `time_open` varchar(45) DEFAULT NULL,
  `time_close` varchar(45) DEFAULT NULL,
  `price_open` varchar(45) DEFAULT NULL,
  `max_price` varchar(45) DEFAULT NULL,
  `low_price` varchar(45) DEFAULT NULL,
  `price_close` varchar(45) DEFAULT NULL,
  `volume` varchar(90) DEFAULT NULL,
  `count_trades` varchar(90) DEFAULT NULL,
  `open_milliseconds` bigint(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `kline_1w` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) DEFAULT NULL,
  `time_open` varchar(45) DEFAULT NULL,
  `time_close` varchar(45) DEFAULT NULL,
  `price_open` varchar(45) DEFAULT NULL,
  `max_price` varchar(45) DEFAULT NULL,
  `low_price` varchar(45) DEFAULT NULL,
  `price_close` varchar(45) DEFAULT NULL,
  `volume` varchar(90) DEFAULT NULL,
  `count_trades` varchar(90) DEFAULT NULL,
  `open_milliseconds` bigint(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `kline_5i` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(45) DEFAULT NULL,
  `time_open` varchar(45) DEFAULT NULL,
  `time_close` varchar(45) DEFAULT NULL,
  `price_open` varchar(45) DEFAULT NULL,
  `max_price` varchar(45) DEFAULT NULL,
  `low_price` varchar(45) DEFAULT NULL,
  `price_close` varchar(45) DEFAULT NULL,
  `volume` varchar(90) DEFAULT NULL,
  `count_trades` varchar(90) DEFAULT NULL,
  `open_milliseconds` bigint(15) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
