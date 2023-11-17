CREATE TABLE IF NOT EXISTS prueba1 (
  idprueba1 INTEGER PRIMARY KEY AUTOINCREMENT,
  prueba1col TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS prueba2 (
  idprueba2 INTEGER PRIMARY KEY AUTOINCREMENT,
  prueba2col TEXT UNIQUE,
  prueba2col1 DATE
);

CREATE TABLE IF NOT EXISTS prueba1_has_prueba2 (
  prueba1_idprueba1 INTEGER NOT NULL,
  prueba2_idprueba2 INTEGER NOT NULL,
  PRIMARY KEY (prueba1_idprueba1, prueba2_idprueba2),
  FOREIGN KEY (prueba1_idprueba1) REFERENCES prueba1 (idprueba1),
  FOREIGN KEY (prueba2_idprueba2) REFERENCES prueba2 (idprueba2)
);