DROP SCHEMA IF EXISTS `libraryDemo`;
CREATE SCHEMA IF NOT EXISTS `libraryDemo`;

CREATE USER IF NOT EXISTS 'userLibrary'@'localhost' IDENTIFIED BY 'userLibraryPW';
GRANT SELECT, INSERT, CREATE, INSERT, ALTER, REFERENCES, DROP ON `libraryDemo` . * TO 'userLibrary'@'localhost';

DROP SCHEMA IF EXISTS `libraryDemoTest`;
CREATE SCHEMA IF NOT EXISTS `libraryDemoTest`;

CREATE USER IF NOT EXISTS 'userLibraryTest'@'localhost' IDENTIFIED BY 'userLibraryTestPW';
GRANT SELECT, INSERT, CREATE, INSERT, ALTER, REFERENCES, DROP ON `libraryDemoTest` . * TO 'userLibraryTest'@'localhost';
