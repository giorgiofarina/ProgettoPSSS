-- MySQL Script generated by MySQL Workbench
-- Thu Sep 24 16:39:49 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema Prova2
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Prova2
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Prova2` DEFAULT CHARACTER SET utf8 ;
USE `Prova2` ;

-- -----------------------------------------------------
-- Table `Prova2`.`Utente`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`Utente` (
  `idUtente` INT NOT NULL AUTO_INCREMENT,
  `Password` VARCHAR(45) NOT NULL,
  `Nome` VARCHAR(45) NOT NULL,
  `Cognome` VARCHAR(45) NOT NULL,
  `Email` VARCHAR(45) NOT NULL,
  `Docente` INT NULL,
  `MediaScoreLezioni` FLOAT NULL,
  `ContoPaypal` VARCHAR(45) NULL,
  `Corriculum` LONGBLOB NULL,
  PRIMARY KEY (`idUtente`),
  UNIQUE INDEX `Email_UNIQUE` (`Email` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prova2`.`Topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`Topic` (
  `idTopic` INT NOT NULL AUTO_INCREMENT,
  `Name` VARCHAR(45) NULL,
  PRIMARY KEY (`idTopic`),
  UNIQUE INDEX `Name_UNIQUE` (`Name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prova2`.`SubscriptionUtenteTopic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`SubscriptionUtenteTopic` (
  `idSubscriptionUtenteTopic` INT NOT NULL AUTO_INCREMENT,
  `Utente_idUtente` INT NOT NULL,
  `Topic_idTopic` INT NOT NULL,
  `Data` DATE NULL,
  UNIQUE INDEX `idSubscriptionUtenteTopic_UNIQUE` (`idSubscriptionUtenteTopic` ASC),
  INDEX `fk_SubscriptionUtenteTopic_Utente1_idx` (`Utente_idUtente` ASC),
  INDEX `fk_SubscriptionUtenteTopic_Topic1_idx` (`Topic_idTopic` ASC),
  PRIMARY KEY (`Topic_idTopic`, `Utente_idUtente`),
  CONSTRAINT `fk_SubscriptionUtenteTopic_Utente1`
    FOREIGN KEY (`Utente_idUtente`)
    REFERENCES `Prova2`.`Utente` (`idUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SubscriptionUtenteTopic_Topic1`
    FOREIGN KEY (`Topic_idTopic`)
    REFERENCES `Prova2`.`Topic` (`idTopic`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prova2`.`Lezione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`Lezione` (
  `idLezione` INT NOT NULL AUTO_INCREMENT,
  `NomeLezione` VARCHAR(45) NOT NULL,
  `MediaScoreLezione` FLOAT NULL,
  `NMaxStudenti` INT NULL,
  `Utente_idUtente` INT NOT NULL,
  `prezzo` FLOAT NULL,
  `Topic_idTopic` INT NOT NULL,
  UNIQUE INDEX `idLezione_UNIQUE` (`idLezione` ASC),
  PRIMARY KEY (`idLezione`, `Utente_idUtente`),
  INDEX `fk_Lezione_Utente1_idx` (`Utente_idUtente` ASC),
  INDEX `fk_Lezione_Topic1_idx` (`Topic_idTopic` ASC),
  CONSTRAINT `fk_Lezione_Utente1`
    FOREIGN KEY (`Utente_idUtente`)
    REFERENCES `Prova2`.`Utente` (`idUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Lezione_Topic1`
    FOREIGN KEY (`Topic_idTopic`)
    REFERENCES `Prova2`.`Topic` (`idTopic`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prova2`.`VideoCall`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`VideoCall` (
  `NomeRoom` VARCHAR(45) NOT NULL,
  `Password` VARCHAR(45) NULL,
  PRIMARY KEY (`NomeRoom`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prova2`.`Pagamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`Pagamento` (
  `DataPagamento` TIMESTAMP NULL,
  `idPagamento` INT NULL AUTO_INCREMENT,
  UNIQUE INDEX `idPagamento_UNIQUE` (`idPagamento` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prova2`.`Recensione`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`Recensione` (
  `idRecensione` INT NULL AUTO_INCREMENT,
  `Valutazione` INT NULL,
  UNIQUE INDEX `idRecensione_UNIQUE` (`idRecensione` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Prova2`.`FasciaOraria`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Prova2`.`FasciaOraria` (
  `DataLezione` DATE NOT NULL,
  `OrarioLezione` INT NOT NULL,
  `Visible` INT NOT NULL DEFAULT 0,
  `Lezione_idLezione` INT NOT NULL,
  `Lezione_Utente_idUtente` INT NOT NULL,
  PRIMARY KEY (`DataLezione`, `OrarioLezione`, `Lezione_Utente_idUtente`),
  INDEX `fk_FasciaOraria_Lezione1_idx` (`Lezione_idLezione` ASC, `Lezione_Utente_idUtente` ASC),
  CONSTRAINT `fk_FasciaOraria_Lezione1`
    FOREIGN KEY (`Lezione_idLezione` , `Lezione_Utente_idUtente`)
    REFERENCES `Prova2`.`Lezione` (`idLezione` , `Utente_idUtente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
