-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema empresa
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema empresa
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `empresa` DEFAULT CHARACTER SET utf8 ;
USE `empresa` ;

-- -----------------------------------------------------
-- Table `empresa`.`Direccion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empresa`.`Direccion` (
  `idDireccion` INT NOT NULL,
  `calle` VARCHAR(100) NOT NULL,
  `numero` VARCHAR(45) NOT NULL,
  `cp` CHAR(5) NOT NULL,
  `provincia` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idDireccion`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empresa`.`InfoFinanciera`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empresa`.`InfoFinanciera` (
  `idInfo` INT NOT NULL,
  `presupuesto` INT NOT NULL,
  `ingresos` INT NOT NULL,
  `gastos` INT NOT NULL,
  PRIMARY KEY (`idInfo`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empresa`.`Departamento`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empresa`.`Departamento` (
  `idDepartamento` INT NOT NULL,
  `denominacion` VARCHAR(50) NOT NULL,
  `idDireccion` INT NOT NULL,
  `idInfo` INT NOT NULL,
  `jefe` INT NOT NULL,
  PRIMARY KEY (`idDepartamento`),
  INDEX `fk_Departamento_Direccion1_idx` (`idDireccion` ASC) VISIBLE,
  INDEX `fk_Departamento_InfoFinanciera1_idx` (`idInfo` ASC) VISIBLE,
  INDEX `fk_Departamento_Empleado1_idx` (`jefe` ASC) VISIBLE,
  CONSTRAINT `fk_Departamento_Direccion1`
    FOREIGN KEY (`idDireccion`)
    REFERENCES `empresa`.`Direccion` (`idDireccion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Departamento_InfoFinanciera1`
    FOREIGN KEY (`idInfo`)
    REFERENCES `empresa`.`InfoFinanciera` (`idInfo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Departamento_Empleado1`
    FOREIGN KEY (`jefe`)
    REFERENCES `empresa`.`Empleado` (`idEmpleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empresa`.`Proyecto`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empresa`.`Proyecto` (
  `codigo` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `desc` VARCHAR(100) NOT NULL,
  `fechaInicio` DATE NOT NULL,
  `fechFin` DATE NOT NULL,
  `responsable` INT NOT NULL,
  `idDpto` INT NOT NULL,
  PRIMARY KEY (`codigo`),
  INDEX `fk_Proyecto_Empleado1_idx` (`responsable` ASC) VISIBLE,
  INDEX `fk_Proyecto_Departamento1_idx` (`idDpto` ASC) VISIBLE,
  CONSTRAINT `fk_Proyecto_Empleado1`
    FOREIGN KEY (`responsable`)
    REFERENCES `empresa`.`Empleado` (`idEmpleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Proyecto_Departamento1`
    FOREIGN KEY (`idDpto`)
    REFERENCES `empresa`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empresa`.`Vehiculo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empresa`.`Vehiculo` (
  `idVehiculo` INT NOT NULL,
  `matricula` CHAR(8) NOT NULL,
  `modelo` VARCHAR(45) NOT NULL,
  `marca` VARCHAR(45) NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `idDpto` INT NOT NULL,
  PRIMARY KEY (`idVehiculo`),
  INDEX `fk_Vehiculo_Departamento1_idx` (`idDpto` ASC) VISIBLE,
  CONSTRAINT `fk_Vehiculo_Departamento1`
    FOREIGN KEY (`idDpto`)
    REFERENCES `empresa`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empresa`.`Empleado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empresa`.`Empleado` (
  `idEmpleado` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(200) NOT NULL,
  `salario` INT NOT NULL,
  `idDireccion` INT NOT NULL,
  `idDpto` INT NOT NULL,
  `codProyecto` INT NOT NULL,
  `idVehiculo` INT NOT NULL,
  PRIMARY KEY (`idEmpleado`),
  INDEX `fk_Empleado_Direccion_idx` (`idDireccion` ASC) VISIBLE,
  INDEX `fk_Empleado_Departamento1_idx` (`idDpto` ASC) VISIBLE,
  INDEX `fk_Empleado_Proyecto1_idx` (`codProyecto` ASC) VISIBLE,
  INDEX `fk_Empleado_Vehiculo1_idx` (`idVehiculo` ASC) VISIBLE,
  CONSTRAINT `fk_Empleado_Direccion`
    FOREIGN KEY (`idDireccion`)
    REFERENCES `empresa`.`Direccion` (`idDireccion`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Departamento1`
    FOREIGN KEY (`idDpto`)
    REFERENCES `empresa`.`Departamento` (`idDepartamento`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Proyecto1`
    FOREIGN KEY (`codProyecto`)
    REFERENCES `empresa`.`Proyecto` (`codigo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Empleado_Vehiculo1`
    FOREIGN KEY (`idVehiculo`)
    REFERENCES `empresa`.`Vehiculo` (`idVehiculo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `empresa`.`Tarea`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `empresa`.`Tarea` (
  `idTarea` INT NOT NULL,
  `denominacion` VARCHAR(50) NOT NULL,
  `descripcion` VARCHAR(100) NOT NULL,
  `prioridad` ENUM('BAJA', 'MEDIA', 'ALTA') NOT NULL,
  `idEmpleado` INT NOT NULL,
  PRIMARY KEY (`idTarea`),
  INDEX `fk_Tarea_Empleado1_idx` (`idEmpleado` ASC) VISIBLE,
  CONSTRAINT `fk_Tarea_Empleado1`
    FOREIGN KEY (`idEmpleado`)
    REFERENCES `empresa`.`Empleado` (`idEmpleado`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
