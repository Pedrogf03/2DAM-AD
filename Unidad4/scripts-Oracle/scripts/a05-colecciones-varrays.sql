--En las BDROO (Bases de Datos Relacionales Orientadas a Objetos) se puede almacenar una colección de elementos en una única columna de una tabla. En el caso de Oracle esto es posible mediante:

--* VARRAY: El tipo VARRAY es similar al tipo array de Java, ya que permite almacenar un conjunto, del tamaño indicado,de elementos, todos del mismo tipo, y cada elemento tiene un índice asociado

--* Tablas anidadas: Permiten almacenar en una columna de una tabla, otra tabla
----------------------------------


--1) DEFINICIÓN DE UN TIPO definido por el usuario como VARRAY. Uso de ese tipo en la DECLARACIÓN DE UNA COLUMNA en una tabla. 


--Definición de un tipo definido por el usuario como VARRAY

CREATE TYPE TELEFONO AS VARRAY(3) OF VARCHAR2(9); 

/
DESC TELEFONO;

--En la vista USER_VARRAYS se obtiene información de las tablas que tienen columnas VARRAY



--Uso de ese tipo en la declaración de una columna en una tabla

CREATE TABLE AGENDA 
(
  NOMBRE VARCHAR2(15),
  TELEF TELEFONO
);

DESC AGENDA;




--2) MANIPULACIÓN DE TUPLAS (insert, update,delete) en una tabla con columnas VARRAY


--Inserción de tuplas

SELECT * FROM AGENDA;


INSERT INTO AGENDA VALUES 
('MANUEL', TELEFONO ('656008876', '927986655', '639883300')); 


INSERT INTO AGENDA (NOMBRE, TELEF) VALUES 
       ('MARTA', TELEFONO ('649500800'));

COMMIT;


SELECT * FROM AGENDA ;



--Modificación de tuplas


  UPDATE AGENDA 
  SET TELEF =  TELEFONO ('656008876', 
                         '927986655')
  WHERE NOMBRE = 'MANUEL';



--3) RECORRER los elementos de un array

--   (el método COUNT aplicado a una columna de tipo VARRAY proporciona el tamaño del array)


DECLARE
  CURSOR C1 IS SELECT * FROM AGENDA;
  CAD VARCHAR2(50);
BEGIN
  FOR I IN C1 LOOP
    DBMS_OUTPUT.PUT_LINE(I.NOMBRE ||
        ', Número de Telefonos: '||I.TELEF.COUNT);
    CAD:='*';
    FOR J IN 1 .. I.TELEF.COUNT LOOP   
      CAD:=CAD ||I.TELEF(J)||'*';
    END LOOP;
    DBMS_OUTPUT.PUT_LINE(CAD);
  END LOOP;
END;
/



--4) DEFINICIÓN DE UN PROCEDIMIENTO CON PARÁMETROS DE TIPO VARRAY para insertar datos en una tablaPARA

-- El procedimiento recibe el nombre y el array de teléfonos



CREATE OR REPLACE PROCEDURE INSERTAR_AGENDA 
  ( N VARCHAR2, T TELEFONO) AS
BEGIN
  INSERT INTO AGENDA VALUES (N, T);
END INSERTAR_AGENDA;
/


--USO DEL PROCEDIMIENTO

BEGIN
  INSERTAR_AGENDA('LUIS', TELEFONO('949009977'));
  INSERTAR_AGENDA('MIGUEL', TELEFONO('949004020','678905400'));
  COMMIT;
END;
/

SELECT * FROM AGENDA;



--5) EJEMPLO que visualiza los teléfonos de un nombre.
--   (métodos asociados a un VARRAY: COUNT, FIRST, LAST, LIMIT, EXTEND, TRIM, DELETE, ...)

DECLARE 
  TEL TELEFONO := TELEFONO(NULL, NULL, NULL); 
BEGIN
  SELECT TELEF INTO  TEL FROM AGENDA WHERE NOMBRE = 'MARTA';

  --Visualizar Datos
  DBMS_OUTPUT.PUT_LINE('Nº DE TELÉFONOS ACTUALES:   ' || TEL.COUNT);
  DBMS_OUTPUT.PUT_LINE('ÍNDICE DEL PRIMER ELEMENTO: ' || TEL.FIRST);
  DBMS_OUTPUT.PUT_LINE('ÍNDICE DEL ÚLTIMO ELEMENTO: ' || TEL.LAST);
  DBMS_OUTPUT.PUT_LINE('MÁXIMO Nº DE TLFS PERMITIDO:' || TEL.LIMIT);

  --Añade un número de teléfono a MARTA   
  TEL.EXTEND; 
  TEL(TEL.COUNT):= '123000000';     
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MARTA';   
    
  --Elimina un teléfono   
  SELECT TELEF INTO TEL FROM AGENDA WHERE NOMBRE = 'MANUEL';     
  TEL.TRIM;   --Elimina el último elemento del array    
  TEL.DELETE; --Elimina todos los elementos
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MANUEL';    
END;
/

------------------------------------------------------------
--ACTIVIDADES

--Definir una función almacenada que reciba un nombre de la agenda y devuelva el primer teléfono que tenga. Realiza un bloque PL/SQL que haga uso de la función

--Modificar el tipo T_ALUMNO para que incluya un VARRAY de hasta 5 teléfonos y otro array de 10 elementos que contenga las faltas acumuladas en cada uno de los 10 meses del curso escolar (de Septiembre a Junio).

--Redefinir el constructor adecuadamente

--Insertar dos alumnos; del primer se conocen dos números de teléfono y las faltas de los meses de septiembre a Diciembre. Del segundo no se tienen datos sobre teléfonos ni faltas

--Modificar el segundo alumno indicando 3 números de teléfono y 10 faltas en septiembre

--Mostrar los datos que se tienen de todos los alumnos, incluyendo teléfonos y faltas






