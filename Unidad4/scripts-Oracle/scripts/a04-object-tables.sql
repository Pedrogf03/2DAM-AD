

---------------------TABLAS DE OBJETOS -- 

--Una vez definidos los objetos podemos utilizarlos para definir:

--*nuevos tipos

--*columnas de tablas (cuyo dominio sera el que representa el type del objeto)

--*tablas que almacenan objetos. Una tabla de objetos es una tabla que almacena un objeto en cada fila; se accede a los atributos de esos objetos como si se tratasen de columnas de la tabla



--1) Se define una tabla 'Alumnos' para almacenar objetos de tipo 'Persona'; esta tabla contiene ademas una columna 'codigo' que se usara como clave primaria

CREATE TABLE ALUMNOS OF PERSONA (
  CODIGO PRIMARY KEY
);


--Se accede a los atributos de cada objeto como si fueran columnas de la tabla
DESC ALUMNOS;




--2) Insercion en la tabla de objetos 'Alumnos'. Notese que puesto que uno de los valores a insertar es de tipo 'Direccion', hay que invocar a su constructor

--INSERCION DE DATOS
INSERT INTO ALUMNOS VALUES (1, 'Juan Perez ', DIRECCION ('C/Los manantiales 5', 'GUADALAJARA', 19005), '18/12/1991');


INSERT INTO ALUMNOS (CODIGO, NOMBRE, DIREC, FECHA_NAC) 
VALUES (2, 'Julia Bre�a',DIRECCION ('C/Los espartales 25', 'GUADALAJARA', 19004),'18/12/1987');




--El siguiente bloque PL/SQL inserta una fila en la tabla ALUMNOS:
DECLARE
  DIR DIRECCION := DIRECCION('C/Sevilla 20', 'GUADALAJARA', 19004);
  PER PERSONA := PERSONA(5, 'MANUEL',DIR, '20/10/1987');
BEGIN  
  INSERT INTO ALUMNOS VALUES(PER); --insertar 
  COMMIT; 
END;
/


--3) Consultas sobre la tabla

--Para acceder a una variable de instancia del objeto, se hace como si fuera una columna, empleando la notacion 'AliasTabla.Campo'. Si esa variable de instancia es a su vez otro objeto, se utiliza la misma notacion
--Alumnos que viven en 'Guadalajara'

select * from Alumnos A where A.DIREC.CIUDAD='GUADALAJARA';

--Obtener el codigo y direccion de todos los alumnos.
--Para seleccionar columnas individuales, si la columna es un tipo OBJECT, se necesita definir un alias para la tabla. En una BD con tipos y objetos se recomienda usar alias para el nombre de las tablas

select CODIGO, A.DIREC from Alumnos A;

--Obtener el nombre y la calle de todos los alumnos
--Es posible invocar a métodos de objetos (es obligatorio escribir los paréntesis aunque el metodo no tenga argumentos)

select Nombre, A.Direc.GET_CALLE() from Alumnos A;



--4) Actualizacion de filas en una tabla de objetos

--Modificamos el nombre de la ciudad a minuscula para aquellos alumnos que vivan en 'Guadalajara'
update Alumnos A set A.DIREC.CIUDAD=LOWER(A.DIREC.CIUDAD) where A.DIREC.CIUDAD='GUADALAJARA';


--MODIFICAR LA DIRECCION COMPLETA
DECLARE
   D DIRECCION:= DIRECCION 
       ('C/Galiano 5','Guadalajara',19004);
BEGIN
    UPDATE ALUMNOS 
       SET DIREC = D WHERE NOMBRE ='Juan Pérez'; 
  COMMIT;
END;
/



--5) Borrado de filas en una tabla de objetos

--Borrar a los alumnos de 'guadalajara' (en minuscula)

delete ALUMNOS A where A.DIREC.CIUDAD='guadalajara';



-------------------
--6) Cursores


--Mostrar el nombre y la calle de los alumnos, utilizando un cursor

DECLARE
  CURSOR C1 IS SELECT * FROM ALUMNOS;
BEGIN
  FOR I IN C1 LOOP
    DBMS_OUTPUT.PUT_LINE(I.NOMBRE || ' - Calle: ' || I.DIREC.CALLE);
  END LOOP;
END;
/





------- ACTIVIDAD 3 -----------------------------------
 --*Crear la tabla ALUMNOS2 del tipo T_ALUMNO e inserta objetos en ella. Realiza luego una consulta que visualice el nombre del alumno y la nota media.
 
 CREATE TABLE ALUMNOS2 OF T_ALUMNO (
    CODIGO PRIMARY KEY
 );
 /

--El siguiente bloque PL/SQL inserta una fila en la tabla ALUMNOS:
DECLARE
  DIR DIRECCION := DIRECCION('C/Sevilla 20', 'GUADALAJARA', 19004);
  PER PERSONA := PERSONA(5, 'MANUEL',DIR, '20/10/1987');
  ALU T_ALUMNO := T_ALUMNO(5, PER, 5, 5, 5);
BEGIN  
  INSERT INTO ALUMNOS2 VALUES(ALU); 
  COMMIT; 
END;
/

DECLARE
  CURSOR C1 IS SELECT * FROM ALUMNOS2;
BEGIN
  FOR A IN C1 LOOP
    DBMS_OUTPUT.PUT_LINE(A.P.NOMBRE || ' - Calle: ' || A.P.DIREC.CALLE);
  END LOOP;
END;
/












