
--TABLAS ANIDADAS:

--Una tabla anidada es una coleccion formada por un conjunto de elementos, todos del mismo tipo, no siendo necesario especificar el tamaña maximo maximo de una tabla anidada. 
--La tabla anidada esta contenida en una columna y el tipo de esta columna debe ser un tipo de objeto existente en la base de datos



--1) DEFINICION DE UNA TABLA QUE CONTIENE UNA TABLA ANIDADA

--La 'TABLA_ANIDADA' es un tipo que permitira almacenar objetos de tipo 'DIRECCION'
CREATE TYPE TABLA_ANIDADA AS TABLE OF DIRECCION;
/


--Definicion de una columna, en una tabla, utilizando como tipo el 'TABLE' definido anteriormente

CREATE TABLE EJEMPLO_TABLA_ANIDADA (
 ID NUMBER(2),
 APELLIDOS VARCHAR2(35),
 DIREC TABLA_ANIDADA
) NESTED TABLE DIREC STORE AS DIREC;
/

--La clausula 'NESTED TABLE' identifica el nombre de la columna que contendra la tabla anidada.
--La clausula 'STORE AS' especifica el nombre de la tabla ('DIREC_ANIDADA') en la que se van a almacenar las direcciones que se representan en el atributo 'DIREC' de cualquier objeto de la tabla 'EJEMPLO_TABLA_ANIDADA'. 

desc DIRECCION;
desc direc;
desc direccion;
desc tabla_anidada;
desc EJEMPLO_TABLA_ANIDADA;

--2) INSERTAR datos en una tabla y en su tabla anidada

--Se inserta el id, el nombre y la tabla anidada vacia:

insert into EJEMPLO_TABLA_ANIDADA 
    values (5, 'PEREZ', TABLA_ANIDADA());


insert into EJEMPLO_TABLA_ANIDADA 
    values (15, 'PEREZ', null);

--Se inserta el id, el nombre, y va
INSERT INTO EJEMPLO_TABLA_ANIDADA VALUES 
(1, 'RAMOS', 
  TABLA_ANIDADA (
    DIRECCION ('C/Los manantiales 5', 'GUADALAJARA', 19004),
    DIRECCION ('C/Los manantiales 10', 'GUADALAJARA', 19004),
    DIRECCION ('C/Av de Paris 25', 'CÃ?CERES', 10005),
    DIRECCION ('C/Segovia 23-3A', 'TOLEDO', 45005)
  )
);

INSERT INTO EJEMPLO_TABLA_ANIDADA VALUES (2, 'MARTÃ?N', 
  TABLA_ANIDADA (
    DIRECCION ('C/Huesca 5', 'ALCALÃ? DE H', 28804),
    DIRECCION ('C/Madrid 20', 'ALCORCÃ“N', 28921)
  )
);


    
--3) CONSULTA


--Consulta de todas las tuplas de la tabla
SELECT * FROM EJEMPLO_TABLA_ANIDADA;


--
--CONSULTA TODAS LAS DIRECCIONES DE CADA ID (de cada persona)
--SI LA TABLA ANIDADA ES NULA NO SE MUESTRAN LAS FILAS
SELECT  ID, APELLIDOS, DIRECCION.* 
FROM EJEMPLO_TABLA_ANIDADA,  TABLE(DIREC) DIRECCION;

--DIRECCIONES DEL ID 1     
SELECT  ID, APELLIDOS, DIRECCION.* 
FROM EJEMPLO_TABLA_ANIDADA, TABLE(DIREC) DIRECCION 
WHERE ID=1;

--SOLO LA CALLE
SELECT  ID, APELLIDOS, DIRECCION.CALLE 
FROM EJEMPLO_TABLA_ANIDADA, TABLE(DIREC) DIRECCION 
WHERE ID=1;



--4) CONSULTAS CON CURSORES

--USANDO CURSOR PARA SELECCIONAR CALLES
-- POR CADA APELLIDO SUS CALLES, 1 FILA POR APELLIDO
SELECT ID, APELLIDOS, CURSOR(SELECT TT.CALLE  FROM TABLE(DIREC) TT )
FROM EJEMPLO_TABLA_ANIDADA ;


-- CONSULTAS CON CURSORES
--Numero de direcciones de cada persona

SELECT ID, APELLIDOS, CURSOR(SELECT count(TT.CALLE) FROM TABLE(T.DIREC) TT)
FROM EJEMPLO_TABLA_ANIDADA T;

SELECT ID, APELLIDOS, count(*) FROM EJEMPLO_TABLA_ANIDADA T, TABLE(T.DIREC) GROUP BY ID, APELLIDOS;

----Si queremos seleccionar el numero 
--de direcciones de cada persona DE LA CIUDAD DE GUADALAJARA
SELECT ID, APELLIDOS, CURSOR (SELECT count(*) FROM  TABLE(DIREC) where ciudad ='GUADALAJARA') FROM EJEMPLO_TABLA_ANIDADA;
--


--apellidos que tienen 2 direcciones en la ciudad de GUADALAJARA
SELECT ID, APELLIDOS, CURSOR (SELECT count(*) FROM  TABLE(DIREC) where ciudad ='GUADALAJARA')
FROM EJEMPLO_TABLA_ANIDADA where (SELECT count(*) FROM TABLE(DIREC) where ciudad ='GUADALAJARA') = 2;

--
SELECT ID, APELLIDOS, (SELECT count(*) FROM TABLE(EJEMPLO_TABLA_ANIDADA.DIREC) where ciudad ='GUADALAJARA')
FROM EJEMPLO_TABLA_ANIDADA where (SELECT count(*) FROM TABLE(EJEMPLO_TABLA_ANIDADA.DIREC) where ciudad ='GUADALAJARA') = 2;
--

--5) CONSULTAS CON SELECT (sin cursores)

--Sin cursores: Utilizando la tabla anidada en la consulta, 
--              la consulta resulta mas sencilla:

--apellidos que tienen 2 direcciones en la ciudad de GUADALAJARA
SELECT ID, APELLIDOS, count(*)
FROM EJEMPLO_TABLA_ANIDADA T, table(T.DIREC) tt
where tt.ciudad ='GUADALAJARA' 
group by ID, APELLIDOS;


--acceso a filas de la columna contenidas en la tabla anidada


-- Calle, ciudad y codigo postal de la persona con ID 1

-- (Calle de la persona con ID 1 
SELECT CALLE 
FROM (SELECT T.DIREC 
      FROM EJEMPLO_TABLA_ANIDADA T
      WHERE ID=1) J;



-- Direccion (Calle, ciudad y codigo postal) de la persona con ID 1
-- que vive en Guadalajara
DESC DIRECCION;

SELECT CALLE, CIUDAD,CODIGO_POST 
FROM ( SELECT DIREC 
       FROM EJEMPLO_TABLA_ANIDADA 
       where ID=1 )   
WHERE CIUDAD='GUADALAJARA';



--Obtener todas las direcciones de cada persona

SELECT  ID, APELLIDOS, DIRECCION.* 
FROM EJEMPLO_TABLA_ANIDADA, TABLE(DIREC) DIRECCION ;

-- EL SIGUIENTE EJEMPLO OBTIENE LAS DIRECCIONES COMPLETAS DEL IDENTIFIDOR 1

SELECT  ID, DIRECCION.* 
FROM EJEMPLO_TABLA_ANIDADA, TABLE(DIREC) DIRECCION 
WHERE ID=1;




--6)INSERCION EN LA TABLA

--Para insertar en la tabla anidada, seleccionamos la tabla anidada deseada desde
--la tabla contenedora

--Insertar una nueva direccion para una persona que ya tenia direcciones asignadas
INSERT INTO TABLE (SELECT DIREC
                   FROM EJEMPLO_TABLA_ANIDADA 
                   WHERE ID = 1) 
VALUES (DIRECCION('C/Los manantiales 15', 'GUADALAJARA', 19005));


SELECT  ID, APELLIDOS, DIRECCION.* 
FROM EJEMPLO_TABLA_ANIDADA , TABLE(DIREC) DIRECCION
WHERE ID=1;
	
 
--Insertar una direccion para una persona que no tenia asignada ninguna direccion
--pero tenia asignada una tabla anidada DIREC que no es NOT NULL

SELECT * FROM EJEMPLO_TABLA_ANIDADA;
--
INSERT INTO TABLE (SELECT DIREC
                   FROM EJEMPLO_TABLA_ANIDADA
                   WHERE ID = 5) 
VALUES (DIRECCION('C/Sevilla 24, 1A', 'GUADALAJARA', 19003) );


--INSERTAR EN COLUMNA CON VALOR NULO
--Insertamos los datos de una nueva persona, pero el valor de la tabla anidada DIREC es NULL
INSERT INTO EJEMPLO_TABLA_ANIDADA VALUES (6,'GIL', null);

INSERT INTO TABLE  --ERROR porque la columna es nula
(SELECT DIREC 
 FROM EJEMPLO_TABLA_ANIDADA
 WHERE ID = 6) 
VALUES (DIRECCION ('C/Madrid 5', 'OROPESA', 45560));


--Si la columna DIREC que contiene la tabla anidada es nula y queremos añadir una direccion
--a esa persona, hay que ACTUALIZAR la tabla tupla de la tabla contedora para crear una
--tabla anidada; despues podremos insertar en la tabla anidada

--Actualizamos, para el usuario con codigo 6, si valor de DIREC creando una tabla anidada
Update EJEMPLO_TABLA_ANIDADA
set direc = TABLA_ANIDADA (DIRECCION ('C/Madrid 5', 'OROPESA', 45560))
Where ID = 6 ;

--añado otra direccion mas para ese usuario
INSERT INTO TABLE 
  (SELECT DIREC FROM EJEMPLO_TABLA_ANIDADA WHERE ID = 6) 
VALUES (DIRECCION('C/Toledo 34, 8A', 'GUADALAJARA', 19003) );


--
DESC USER_NESTED_TABLES;

SELECT * FROM USER_NESTED_TABLES;


----------------------------------------
--7) MODIFICACION EN LA TABLA ANIDADA
     
UPDATE TABLE (SELECT DIREC 
              FROM EJEMPLO_TABLA_ANIDADA 
              WHERE ID = 1) PRIMERA
SET VALUE(PRIMERA) = DIRECCION ('C/Pilon 11', 'TOLEDO', 45589)
WHERE VALUE(PRIMERA)=DIRECCION('C/Los manantiales 5', 'GUADALAJARA', 19004);

select *  from (SELECT DIREC FROM EJEMPLO_TABLA_ANIDADA e WHERE ID = 1);

ROLLBACK;
--Modificamos (para el ID 1) todas las direcciones que tengan la ciudad de
--GUADALJARA, le damos el valor MADRID:

UPDATE TABLE (SELECT DIREC 
              FROM EJEMPLO_TABLA_ANIDADA 
              WHERE ID = 1) PRIMERA
SET PRIMERA.ciudad =  'madrid'
WHERE PRIMERA.ciudad='GUADALAJARA';




--8) BORRADO EN LA TABLA ANIDADA
DELETE FROM TABLE (SELECT DIREC 
                   FROM EJEMPLO_TABLA_ANIDADA 
                   WHERE ID = 1) PRIMERA
WHERE VALUE(PRIMERA)=DIRECCION('C/Los manantiales 10', 'GUADALAJARA', 19004);

ROLLBACK;

-- BORRAMOS  TODAS LAS DIRECCIONES CON CIUDAD = 'GUADALAJARA'
DELETE FROM TABLE (SELECT DIREC 
                   FROM EJEMPLO_TABLA_ANIDADA 
                   WHERE ID = 1) PRIMERA
WHERE PRIMERA.CIUDAD ='GUADALAJARA';

--OBTENER SOLO LAS DIRECCIONES DE GUADALAJARA
SELECT CALLE, CIUDAD, CODIGO_POST 
FROM ( SELECT DIREC FROM EJEMPLO_TABLA_ANIDADA WHERE ID =1)
WHERE CIUDAD ='GUADALAJARA';


--------------------------------------------
--8)PROCEDIMIENTO

CREATE OR REPLACE PROCEDURE VER_DIREC(IDENT NUMBER) AS
  CURSOR C1 IS 
        SELECT CALLE FROM
       (SELECT T.DIREC FROM EJEMPLO_TABLA_ANIDADA T WHERE ID = IDENT);
BEGIN
  FOR I IN C1 LOOP
    DBMS_OUTPUT.PUT_LINE(I.CALLE);
  END LOOP;
END VER_DIREC;
/

--Probando el procedimiento
BEGIN
  VER_DIREC(1);
END;
/


--) FUNCIÃ“N

CREATE OR REPLACE FUNCTION EXISTE_DIREC 
   (IDEN NUMBER, PCALLE VARCHAR2,  PCIU VARCHAR2, CP NUMBER)
RETURN VARCHAR2 AS
  IDT NUMBER;
  TABLAANID TABLA_ANIDADA;
  CUENTA NUMBER;
BEGIN
 --COMPROBAR SI EXISTE ID, 
   SELECT COUNT(ID) INTO CUENTA 
       FROM EJEMPLO_TABLA_ANIDADA WHERE ID = IDEN;
   IF CUENTA = 0 THEN
       RETURN 'NO EXISTE EL ID: '||IDEN||', EN LA TABLA'; 
   END IF ;
   IF CUENTA > 1 THEN
      RETURN 'EXISTEN VARIOS REGISTROS CON EL MISMO  ID: '||IDEN ;
   END IF;
   
 --EL ID EXISTE, COMPROBAR SI LA CALLE EXISTE:
  SELECT ID INTO IDT
            FROM EJEMPLO_TABLA_ANIDADA, TABLE(DIREC)
            WHERE ID= IDEN 
            AND UPPER(CALLE)=UPPER(PCALLE) 
            AND UPPER(CIUDAD) = UPPER(PCIU) 
            AND CODIGO_POST= CP;
            
  RETURN ('LA DIRECCIÃ“N : '||PCALLE || '*' ||PCIU
              || '*' || CP  || 'YA EXISTE PARA ESE ID: '||IDEN);   
EXCEPTION
WHEN NO_DATA_FOUND THEN  
  RETURN 'NO EXISTE LA DIRECCION : '||PCALLE || '*' ||PCIU
              || '*' || CP  || ' PARA EL ID: '||IDEN;
END EXISTE_DIREC;
/


BEGIN
  DBMS_OUTPUT.PUT_LINE(EXISTE_DIREC(1, 'C/Madrid 5', 'OROPESA', 45560));
  DBMS_OUTPUT.PUT_LINE(EXISTE_DIREC(1,'C/Los manantiales 5', 'GUADALAJARA', 19004));
  DBMS_OUTPUT.PUT_LINE(EXISTE_DIREC(5,'C/Los manantiales 5', 'GUADALAJARA', 19004));  
END;
/


