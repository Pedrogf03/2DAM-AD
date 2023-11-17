CREATE TABLE IF NOT EXISTS prueba1 (
  idprueba1 INT PRIMARY KEY AUTO_INCREMENT,
  prueba1col VARCHAR(45) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS prueba2 (
  idprueba2 INT PRIMARY KEY AUTO_INCREMENT,
  prueba2col VARCHAR(45) UNIQUE NOT NULL,
  prueba2col1 DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS prueba1_has_prueba2 (
  prueba1_idprueba1 INT NOT NULL,
  prueba2_idprueba2 INT NOT NULL,
  PRIMARY KEY (prueba1_idprueba1, prueba2_idprueba2),
  FOREIGN KEY (prueba1_idprueba1) REFERENCES prueba1 (idprueba1),
  FOREIGN KEY (prueba2_idprueba2) REFERENCES prueba2 (idprueba2)
);