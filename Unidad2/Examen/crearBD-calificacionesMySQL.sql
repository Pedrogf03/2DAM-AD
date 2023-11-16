-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema calificaciones
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema calificaciones
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `calificaciones` DEFAULT CHARACTER SET utf8 ;
USE `calificaciones` ;

-- -----------------------------------------------------
-- Table `calificaciones`.`Alumno`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calificaciones`.`Alumno` (
  `expedienteAlu` INT NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`expedienteAlu`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `calificaciones`.`Modulo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calificaciones`.`Modulo` (
  `ciclo` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `codigoModulo` INT PRIMARY KEY
  );


-- -----------------------------------------------------
-- Table `calificaciones`.`matriculado`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calificaciones`.`matriculado` (
  `expedienteAlu` INT NOT NULL,
  `codigoModulo` INT NOT NULL,
  PRIMARY KEY (`expedienteAlu`, `codigoModulo`),
  INDEX `fk_Alumno_has_Modulo_Alumno_idx` (`expedienteAlu` ASC) VISIBLE,
  INDEX `fk_matriculado_Modulo1_idx` (`codigoModulo` ASC) VISIBLE,
  CONSTRAINT `fk_Alumno_has_Modulo_Alumno`
    FOREIGN KEY (`expedienteAlu`)
    REFERENCES `calificaciones`.`Alumno` (`expedienteAlu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_matriculado_Modulo1`
    FOREIGN KEY (`codigoModulo`)
    REFERENCES `calificaciones`.`Modulo` (`codigoModulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `calificaciones`.`Examen`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calificaciones`.`Examen` (
  `numeroExamen` VARCHAR(45) NOT NULL,
  `denominacion` VARCHAR(45) NOT NULL,
  `enunciado` VARCHAR(45) NOT NULL,
  `fecha` DATE NOT NULL,
  `trimestral` TINYINT NOT NULL,
  `codigoModulo` INT NOT NULL,
  PRIMARY KEY (`numeroExamen`),
  INDEX `fk_Examen_Modulo1_idx` (`codigoModulo` ASC) VISIBLE,
  CONSTRAINT `fk_Examen_Modulo1`
    FOREIGN KEY (`codigoModulo`)
    REFERENCES `calificaciones`.`Modulo` (`codigoModulo`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `calificaciones`.`realiza`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `calificaciones`.`realiza` (
  `expedienteAlu` INT NOT NULL,
  `calificacion` INT NOT NULL,
  `numeroExamen` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`expedienteAlu`, `numeroExamen`),
  INDEX `fk_Alumno_has_Examen_Alumno1_idx` (`expedienteAlu` ASC) VISIBLE,
  INDEX `fk_realiza_Examen1_idx` (`numeroExamen` ASC) VISIBLE,
  CONSTRAINT `fk_Alumno_has_Examen_Alumno1`
    FOREIGN KEY (`expedienteAlu`)
    REFERENCES `calificaciones`.`Alumno` (`expedienteAlu`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_realiza_Examen1`
    FOREIGN KEY (`numeroExamen`)
    REFERENCES `calificaciones`.`Examen` (`numeroExamen`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

INSERT INTO Alumno (expedienteAlu, nombre) VALUES 
(1234, 'Pedro'),
(2345, 'Paco'),
(3456, 'Jesus');

INSERT INTO Modulo (nombre, ciclo, codigoModulo) VALUES 
('Acceso a Datos', 'DAM', 1),
('Diseño de Interfaces', 'DAM', 2),
('Programación Web en Entorno Cliente', 'DAW', 3),
('Diseño de Interfaces Web', 'DAW', 4);

INSERT INTO Examen (numeroExamen, denominacion, enunciado, fecha, trimestral, codigoModulo) VALUES
(1, 'Lorem', 'Ipsum', '2023-11-16', false, 1),
(2, 'Lorem', 'Ipsum', '2023-12-4', true, 1),
(3, 'Lorem', 'Ipsum', '2023-12-5', false, 2);

DELIMITER $$
CREATE PROCEDURE matricular (alu INT, modulo INT)
BEGIN

	INSERT INTO Matriculado (expedienteAlu, codigoModulo) VALUES (alu, modulo);
    
    SELECT * FROM Matriculado WHERE expedienteAlu = alu AND codigoModulo = modulo;
	
END$$
DELIMITER ;