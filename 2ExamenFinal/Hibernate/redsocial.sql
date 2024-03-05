-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema redsocial
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema redsocial
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `redsocial` DEFAULT CHARACTER SET utf8mb3 ;
USE `redsocial` ;

-- -----------------------------------------------------
-- Table `redsocial`.`lider`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `redsocial`.`lider` (
  `idLider` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `fecha_alta` DATE NOT NULL,
  PRIMARY KEY (`idLider`),
  UNIQUE INDEX `lidercol_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `redsocial`.`seguidor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `redsocial`.`seguidor` (
  `idSeguidor` INT NOT NULL AUTO_INCREMENT,
  `nick` VARCHAR(45) NOT NULL,
  `correo` VARCHAR(100) NOT NULL,
  `pais` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSeguidor`),
  UNIQUE INDEX `nick_UNIQUE` (`nick` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `redsocial`.`seguidoreslider`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `redsocial`.`seguidoreslider` (
  `idLider` INT NOT NULL,
  `idSeguidor` INT NOT NULL,
  `fecha_seguido` DATE NOT NULL,
  `numMegusta` INT NOT NULL,
  PRIMARY KEY (`idLider`, `idSeguidor`),
  INDEX `fk_lider_has_seguidor_seguidor1_idx` (`idSeguidor` ASC) VISIBLE,
  INDEX `fk_lider_has_seguidor_lider_idx` (`idLider` ASC) VISIBLE,
  CONSTRAINT `fk_lider_has_seguidor_lider`
    FOREIGN KEY (`idLider`)
    REFERENCES `redsocial`.`lider` (`idLider`),
  CONSTRAINT `fk_lider_has_seguidor_seguidor1`
    FOREIGN KEY (`idSeguidor`)
    REFERENCES `redsocial`.`seguidor` (`idSeguidor`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
