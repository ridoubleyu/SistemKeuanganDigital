-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.4.3 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for demo_db
CREATE DATABASE IF NOT EXISTS `demo_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `demo_db`;

-- Dumping structure for table demo_db.anggaran
CREATE TABLE IF NOT EXISTS `anggaran` (
  `anggaran_id` bigint NOT NULL AUTO_INCREMENT,
  `jumlah_target` double DEFAULT NULL,
  `jumlah_terpakai` double DEFAULT NULL,
  `kategori` varchar(255) DEFAULT NULL,
  `periode` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`anggaran_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table demo_db.anggaran: ~1 rows (approximately)
INSERT INTO `anggaran` (`anggaran_id`, `jumlah_target`, `jumlah_terpakai`, `kategori`, `periode`, `user_id`) VALUES
	(1, 100000, 20000, 'makan', '2026-04', 1),
	(2, 300000, 50000, 'transport', '2026-05', 1);

-- Dumping structure for table demo_db.kategori
CREATE TABLE IF NOT EXISTS `kategori` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nama` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table demo_db.kategori: ~3 rows (approximately)
INSERT INTO `kategori` (`id`, `nama`) VALUES
	(1, 'makanan'),
	(2, ''),
	(3, 'minuman');

-- Dumping structure for table demo_db.tabungan
CREATE TABLE IF NOT EXISTS `tabungan` (
  `tabungan_id` bigint NOT NULL AUTO_INCREMENT,
  `jumlah_terkumpul` double DEFAULT NULL,
  `nama_target` varchar(255) DEFAULT NULL,
  `target_jumlah` double DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`tabungan_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table demo_db.tabungan: ~1 rows (approximately)
INSERT INTO `tabungan` (`tabungan_id`, `jumlah_terkumpul`, `nama_target`, `target_jumlah`, `user_id`) VALUES
	(1, 250000, 'konser', 250000, 1);

-- Dumping structure for table demo_db.transaksi
CREATE TABLE IF NOT EXISTS `transaksi` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `jenis` varchar(255) DEFAULT NULL,
  `jumlah` double DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `kategori_id` bigint DEFAULT NULL,
  `kategori` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK30hgfce55urfal57asogd0c8i` (`kategori_id`),
  CONSTRAINT `FK30hgfce55urfal57asogd0c8i` FOREIGN KEY (`kategori_id`) REFERENCES `kategori` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table demo_db.transaksi: ~4 rows (approximately)
INSERT INTO `transaksi` (`id`, `jenis`, `jumlah`, `nama`, `kategori_id`, `kategori`) VALUES
	(4, 'Pemasukan', 1500000, 'frelance', NULL, NULL),
	(5, 'Pengeluaran', 5000, 'makan', NULL, NULL),
	(6, 'Pengeluaran', 20000, 'jajan', NULL, NULL),
	(7, 'Pengeluaran', 100000, 'baju', NULL, NULL),
	(8, 'Pengeluaran', 15000, 'makan', NULL, NULL),
	(9, 'Pengeluaran', 15000, 'makan', NULL, 'Makan'),
	(10, 'Pengeluaran', 20000, 'makan', NULL, 'Makan'),
	(11, 'Pengeluaran', 20000, 'seblak', NULL, 'Makan'),
	(12, 'Pengeluaran', 50000, 'gojek', NULL, 'Transport');

-- Dumping structure for table demo_db.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `nama` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Dumping data for table demo_db.users: ~2 rows (approximately)
INSERT INTO `users` (`id`, `email`, `nama`, `password`) VALUES
	(1, 'astria@gmail.com', 'astriasapitri', '123'),
	(2, 'januari@gmail.com', 'januari', '456');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
