-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: klines
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `kline_15m`
--

DROP TABLE IF EXISTS `kline_15m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kline_15m` (
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
) ENGINE=InnoDB AUTO_INCREMENT=146218 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kline_1d`
--

DROP TABLE IF EXISTS `kline_1d`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=99810 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kline_1h`
--

DROP TABLE IF EXISTS `kline_1h`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=139643 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kline_1m`
--

DROP TABLE IF EXISTS `kline_1m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=4881 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kline_1min`
--

DROP TABLE IF EXISTS `kline_1min`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kline_1min` (
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
) ENGINE=InnoDB AUTO_INCREMENT=285406 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kline_1w`
--

DROP TABLE IF EXISTS `kline_1w`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
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
) ENGINE=InnoDB AUTO_INCREMENT=20396 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kline_4h`
--

DROP TABLE IF EXISTS `kline_4h`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kline_4h` (
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
) ENGINE=InnoDB AUTO_INCREMENT=132614 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `kline_5m`
--

DROP TABLE IF EXISTS `kline_5m`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kline_5m` (
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
) ENGINE=InnoDB AUTO_INCREMENT=141885 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `paras`
--

DROP TABLE IF EXISTS `paras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paras` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `para` varchar(55) DEFAULT NULL,
  `status` varchar(55) DEFAULT NULL,
  `quoteAsset` varchar(20) DEFAULT NULL,
  `baseAsset` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=284 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-18 21:41:16
