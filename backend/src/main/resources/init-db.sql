-- Script d'initialisation de la base de données MDD
-- À exécuter dans MySQL Workbench ou ligne de commande MySQL

-- Créer la base de données
CREATE DATABASE IF NOT EXISTS mdd_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Créer l'utilisateur dédié
CREATE USER IF NOT EXISTS 'mdd_user'@'localhost' IDENTIFIED BY 'password';

-- Donner les privilèges
GRANT ALL PRIVILEGES ON mdd_db.* TO 'mdd_user'@'localhost';
FLUSH PRIVILEGES;

-- Utiliser la base de données
USE mdd_db;

-- Vérification
SELECT 'Base de données mdd_db créée avec succès !' AS message;
SHOW TABLES;
