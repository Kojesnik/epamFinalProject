-- MySQL dump 10.13  Distrib 8.0.18, for macos10.14 (x86_64)
--
-- Host: localhost    Database: courierPicker
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `courier_offer`
--

DROP TABLE IF EXISTS `courier_offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier_offer` (
  `id_offer` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `status` enum('approved','not_approved','deleted') NOT NULL,
  `comment` tinytext,
  PRIMARY KEY (`id_offer`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `courier_offer_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier_offer`
--

LOCK TABLES `courier_offer` WRITE;
/*!40000 ALTER TABLE `courier_offer` DISABLE KEYS */;
INSERT INTO `courier_offer` VALUES (1,1,'approved','Call me +37529111222333'),(2,1,'approved','Have big variety of transport'),(3,1,'approved','Can deliver food in very interesting way'),(4,1,'approved','some comment'),(5,1,'approved','CALL ME +375209999999'),(6,1,'not_approved','Can deliver technics'),(7,4,'approved','Have new way of delivery');
/*!40000 ALTER TABLE `courier_offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier_offer_goods`
--

DROP TABLE IF EXISTS `courier_offer_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier_offer_goods` (
  `id_offer` int(11) NOT NULL,
  `id_goods` int(11) NOT NULL,
  PRIMARY KEY (`id_offer`,`id_goods`),
  KEY `id_goods` (`id_goods`),
  CONSTRAINT `courier_offer_goods_ibfk_1` FOREIGN KEY (`id_offer`) REFERENCES `courier_offer` (`id_offer`),
  CONSTRAINT `courier_offer_goods_ibfk_2` FOREIGN KEY (`id_goods`) REFERENCES `goods` (`id_goods`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier_offer_goods`
--

LOCK TABLES `courier_offer_goods` WRITE;
/*!40000 ALTER TABLE `courier_offer_goods` DISABLE KEYS */;
INSERT INTO `courier_offer_goods` VALUES (3,1),(7,1),(2,2),(4,2),(7,2),(1,3),(2,3),(5,3),(7,3),(5,4),(6,4),(1,6),(5,6);
/*!40000 ALTER TABLE `courier_offer_goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier_offer_transport`
--

DROP TABLE IF EXISTS `courier_offer_transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier_offer_transport` (
  `id_offer` int(11) NOT NULL,
  `id_transport` int(11) NOT NULL,
  PRIMARY KEY (`id_offer`,`id_transport`),
  KEY `id_transport` (`id_transport`),
  CONSTRAINT `courier_offer_transport_ibfk_1` FOREIGN KEY (`id_offer`) REFERENCES `courier_offer` (`id_offer`),
  CONSTRAINT `courier_offer_transport_ibfk_2` FOREIGN KEY (`id_transport`) REFERENCES `transport` (`id_transport`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier_offer_transport`
--

LOCK TABLES `courier_offer_transport` WRITE;
/*!40000 ALTER TABLE `courier_offer_transport` DISABLE KEYS */;
INSERT INTO `courier_offer_transport` VALUES (2,1),(2,2),(5,2),(1,3),(2,3),(4,3),(3,4),(5,4),(7,5),(1,6),(5,6),(6,6);
/*!40000 ALTER TABLE `courier_offer_transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `courier_offer_user`
--

DROP TABLE IF EXISTS `courier_offer_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `courier_offer_user` (
  `id_offer` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `status` varchar(15) NOT NULL,
  `accept_date` date DEFAULT NULL,
  `finish_date` date DEFAULT NULL,
  `comment` tinytext,
  PRIMARY KEY (`id_offer`,`id_user`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `courier_offer_user_ibfk_1` FOREIGN KEY (`id_offer`) REFERENCES `courier_offer` (`id_offer`),
  CONSTRAINT `courier_offer_user_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `courier_offer_user`
--

LOCK TABLES `courier_offer_user` WRITE;
/*!40000 ALTER TABLE `courier_offer_user` DISABLE KEYS */;
INSERT INTO `courier_offer_user` VALUES (1,3,'not_accepted',NULL,NULL,'OK, lets work. I need courier to deliver cosmetics for my shop'),(5,3,'accepted','2019-12-12',NULL,'lets work');
/*!40000 ALTER TABLE `courier_offer_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `goods` (
  `id_goods` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id_goods`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `goods`
--

LOCK TABLES `goods` WRITE;
/*!40000 ALTER TABLE `goods` DISABLE KEYS */;
INSERT INTO `goods` VALUES (1,'food'),(2,'clothes'),(3,'medicine'),(4,'technics'),(5,'home'),(6,'cosmetics');
/*!40000 ALTER TABLE `goods` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transport`
--

DROP TABLE IF EXISTS `transport`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transport` (
  `id_transport` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) NOT NULL,
  PRIMARY KEY (`id_transport`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transport`
--

LOCK TABLES `transport` WRITE;
/*!40000 ALTER TABLE `transport` DISABLE KEYS */;
INSERT INTO `transport` VALUES (1,'bicycle'),(2,'rollers'),(3,'car'),(4,'drone'),(5,'atv'),(6,'motorbike');
/*!40000 ALTER TABLE `transport` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(15) NOT NULL,
  `password` varchar(40) NOT NULL,
  `email` varchar(30) NOT NULL,
  `firstname` varchar(15) NOT NULL,
  `lastname` varchar(15) NOT NULL,
  `role` enum('user','admin','courier') NOT NULL,
  `state` enum('active','blocked') NOT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'kojesC','3af04f00a35d00376c7d6173a8602505df8114fd','kojes@gmail.com','Misha','Kolesnik','courier','active'),(2,'admin','3af04f00a35d00376c7d6173a8602505df8114fd','kolesnik.mish@gmail.com','Misha','Kolesnik','admin','active'),(3,'applenik','3af04f00a35d00376c7d6173a8602505df8114fd','koruzin@gmail.com','Nikita','Koruzin','user','active'),(4,'misha111','3af04f00a35d00376c7d6173a8602505df8114fd','proooo@gmail.com','Misha','Kolesnik','courier','active');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_offer`
--

DROP TABLE IF EXISTS `user_offer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_offer` (
  `id_offer` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `needed_couriers_number` int(11) NOT NULL,
  `active_couriers_number` int(11) NOT NULL,
  `status` enum('approved','not_approved','deleted') NOT NULL,
  `comment` tinytext,
  PRIMARY KEY (`id_offer`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `user_offer_ibfk_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_offer`
--

LOCK TABLES `user_offer` WRITE;
/*!40000 ALTER TABLE `user_offer` DISABLE KEYS */;
INSERT INTO `user_offer` VALUES (1,3,5,0,'not_approved','Need 5 couriers to deliver medicine'),(2,3,2,0,'approved','2 couriers fast'),(3,3,1,1,'approved','1 courier for home goods'),(4,3,3,0,'approved','no comment'),(5,3,4,0,'approved','4 couriers'),(6,3,2,1,'approved','Новый магазин открылся');
/*!40000 ALTER TABLE `user_offer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_offer_courier`
--

DROP TABLE IF EXISTS `user_offer_courier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_offer_courier` (
  `id_offer` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `status` varchar(15) NOT NULL,
  `accept_date` date DEFAULT NULL,
  `finish_date` date DEFAULT NULL,
  `comment` tinytext,
  PRIMARY KEY (`id_offer`,`id_user`),
  KEY `id_user` (`id_user`),
  CONSTRAINT `user_offer_courier_ibfk_1` FOREIGN KEY (`id_offer`) REFERENCES `user_offer` (`id_offer`),
  CONSTRAINT `user_offer_courier_ibfk_2` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_offer_courier`
--

LOCK TABLES `user_offer_courier` WRITE;
/*!40000 ALTER TABLE `user_offer_courier` DISABLE KEYS */;
INSERT INTO `user_offer_courier` VALUES (2,1,'not_accepted',NULL,NULL,'comment courier'),(3,1,'accepted','2019-12-12',NULL,'lets work'),(4,1,'not_accepted',NULL,NULL,'comment'),(5,1,'not_accepted',NULL,NULL,'comment'),(5,4,'not_accepted',NULL,NULL,'my comment'),(6,1,'accepted',NULL,NULL,'Готов работать');
/*!40000 ALTER TABLE `user_offer_courier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_offer_goods`
--

DROP TABLE IF EXISTS `user_offer_goods`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_offer_goods` (
  `id_offer` int(11) NOT NULL,
  `id_goods` int(11) NOT NULL,
  PRIMARY KEY (`id_offer`,`id_goods`),
  KEY `id_goods` (`id_goods`),
  CONSTRAINT `user_offer_goods_ibfk_1` FOREIGN KEY (`id_offer`) REFERENCES `user_offer` (`id_offer`),
  CONSTRAINT `user_offer_goods_ibfk_2` FOREIGN KEY (`id_goods`) REFERENCES `goods` (`id_goods`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_offer_goods`
--

LOCK TABLES `user_offer_goods` WRITE;
/*!40000 ALTER TABLE `user_offer_goods` DISABLE KEYS */;
INSERT INTO `user_offer_goods` VALUES (4,1),(6,2),(1,3),(5,3),(2,4),(3,5);
/*!40000 ALTER TABLE `user_offer_goods` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-12-16 15:19:27
