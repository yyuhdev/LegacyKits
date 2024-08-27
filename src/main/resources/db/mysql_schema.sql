SET DEFAULT_STORAGE_ENGINE = INNODB;

SET FOREIGN_KEY_CHECKS = 1;

CREATE TABLE IF NOT EXISTS kits (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL,
    kitId INT NOT NULL,
    contents TEXT NOT NULL,
    name VARCHAR(8) NOT NULL
    ) CHARACTER SET utf8
    COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS enderchests (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL,
    kitId INT NOT NULL,
    contents TEXT NOT NULL,
    name VARCHAR(8) NOT NULL
    ) CHARACTER SET utf8
    COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS settings (
    uuid VARCHAR(36) NOT NULL PRIMARY KEY,
    kit INT NOT NULL,
    kitMessages BOOLEAN NOT NULL,
    smartAutoKit BOOLEAN NOT NULL
) CHARACTER SET utf8
    COLLATE utf8_unicode_ci;