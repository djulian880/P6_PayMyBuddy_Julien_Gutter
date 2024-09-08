-- MySQL Script generated by MySQL Workbench
-- Wed Sep  4 10:02:56 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema PayMyBuddyTestDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema PayMyBuddyTestDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `PayMyBuddyTestDB` DEFAULT CHARACTER SET utf8 ;
USE `PayMyBuddyTestDB` ;

-- -----------------------------------------------------
-- Table `PayMyBuddyTestDB`.`Connection`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PayMyBuddyTestDB`.`Connection` ;

CREATE TABLE IF NOT EXISTS `PayMyBuddyTestDB`.`Connection` (
  `id_Connection` INT NOT NULL AUTO_INCREMENT,
  `User_id_1` INT NOT NULL,
  `User_id_2` INT NOT NULL,
  PRIMARY KEY (`id_Connection`),
  INDEX `fk_Connection_User1_idx` (`User_id_2` ASC) VISIBLE,
  INDEX `fk_Connection_User_idx` (`User_id_1` ASC) VISIBLE,
  CONSTRAINT `fk_Connection_User`
    FOREIGN KEY (`User_id_1`)
    REFERENCES `PayMyBuddyTestDB`.`User` (`id_User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Connection_User1`
    FOREIGN KEY (`User_id_2`)
    REFERENCES `PayMyBuddyTestDB`.`User` (`id_User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PayMyBuddyTestDB`.`Transaction`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PayMyBuddyTestDB`.`Transaction` ;

CREATE TABLE IF NOT EXISTS `PayMyBuddyTestDB`.`Transaction` (
  `id_Transaction` INT NOT NULL AUTO_INCREMENT,
  `description` VARCHAR(45) NULL,
  `amount` DECIMAL(12,2) NULL,
  `sender_Id` INT NOT NULL,
  `receiver_Id` INT NOT NULL,
  PRIMARY KEY (`id_Transaction`),
  INDEX `fk_Transaction_User1_idx` (`sender_Id` ASC) VISIBLE,
  INDEX `fk_Transaction_User2_idx` (`receiver_Id` ASC) VISIBLE,
  CONSTRAINT `fk_Transaction_User1`
    FOREIGN KEY (`sender_Id`)
    REFERENCES `PayMyBuddyTestDB`.`User` (`id_User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Transaction_User2`
    FOREIGN KEY (`receiver_Id`)
    REFERENCES `PayMyBuddyTestDB`.`User` (`id_User`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `PayMyBuddyTestDB`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `PayMyBuddyTestDB`.`User` ;

CREATE TABLE IF NOT EXISTS `PayMyBuddyTestDB`.`User` (
  `id_User` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `email` VARCHAR(90) NOT NULL,
  `password` VARCHAR(250) NOT NULL,
  PRIMARY KEY (`id_User`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS paymybuddyUser;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'paymybuddyUser' IDENTIFIED BY 'dontpaypal';

GRANT SELECT ON TABLE `PayMyBuddyTestDB`.* TO 'paymybuddyUser';
GRANT SELECT, INSERT, TRIGGER ON TABLE `PayMyBuddyTestDB`.* TO 'paymybuddyUser';
GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE `PayMyBuddyTestDB`.* TO 'paymybuddyUser';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

insert into paymybuddytestdb.user (user.id_user,user.username,user.email,user.password) values ("1","test","test@mail.com","passtest"),("2";"test2","test2@mail.com","passtest");
