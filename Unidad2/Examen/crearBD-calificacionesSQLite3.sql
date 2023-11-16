CREATE TABLE Alumno (
  expedienteAlu INTEGER PRIMARY KEY,
  nombre TEXT NOT NULL
);

CREATE TABLE Modulo (
  ciclo TEXT NOT NULL,
  nombreModulo TEXT PRIMARY KEY
);

CREATE TABLE matriculado (
  expedienteAlu INTEGER NOT NULL,
  nombreModulo TEXT NOT NULL,
  PRIMARY KEY (expedienteAlu, nombreModulo),
  FOREIGN KEY (expedienteAlu) REFERENCES Alumno (expedienteAlu),
  FOREIGN KEY (nombreModulo) REFERENCES Modulo (nombreModulo)
);

CREATE TABLE Examen (
  numeroExamen TEXT PRIMARY KEY,
  denominacion TEXT NOT NULL,
  enunciado TEXT NOT NULL,
  fecha DATE NOT NULL,
  trimestral TINYINT NOT NULL,
  nombreModulo TEXT NOT NULL,
  FOREIGN KEY (nombreModulo) REFERENCES Modulo (nombreModulo)
);

CREATE TABLE realiza (
  expedienteAlu INTEGER NOT NULL,
  calificacion INTEGER NOT NULL,
  numeroExamen TEXT NOT NULL,
  PRIMARY KEY (expedienteAlu, numeroExamen),
  FOREIGN KEY (expedienteAlu) REFERENCES Alumno (expedienteAlu),
  FOREIGN KEY (numeroExamen) REFERENCES Examen (numeroExamen)
);

INSERT INTO Alumno (expedienteAlu, nombre) VALUES 
(1234, 'Pedro'),
(2345, 'Paco'),
(3456, 'Jesus');

INSERT INTO Modulo (nombreModulo, ciclo) VALUES 
('Acceso a Datos', 'DAM'),
('Diseño de Interfaces', 'DAM'),
('Programación Web en Entorno Cliente', 'DAW'),
('Diseño de Interfaces Web', 'DAW');

INSERT INTO Examen (numeroExamen, denominacion, enunciado, fecha, trimestral, nombreModulo) VALUES
(1, 'Lorem', 'Ipsum', '2023-11-16', false, 'Acceso a Datos'),
(2, 'Lorem', 'Ipsum', '2023-12-4', true, 'Acceso a Datos'),
(3, 'Lorem', 'Ipsum', '2023-12-5', false, 'Diseño de Interfaces');