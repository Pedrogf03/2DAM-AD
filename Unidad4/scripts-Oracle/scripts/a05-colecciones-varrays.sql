--En las BDROO (Bases de Datos Relacionales Orientadas a Objetos) se puede almacenar una coleccion de elementos en una unica columna de una tabla. En el caso de Oracle esto es posible mediante:

--* VARRAY: El tipo VARRAY es similar al tipo array de Java, ya que permite almacenar un conjunto, del tama√±o indicado,de elementos, todos del mismo tipo, y cada elemento tiene un indice asociado

--* Tablas anidadas: Permiten almacenar en una columna de una tabla, otra tabla
----------------------------------


--1) DEFINICION DE UN TIPO definido por el usuario como VARRAY. Uso de ese tipo en la DECLARACION DE UNA COLUMNA en una tabla. 


--Definicion de un tipo definido por el usuario como VARRAY

CREATE TYPE TELEFONO AS VARRAY(3) OF VARCHAR2(9); 

/
DESC TELEFONO;

--En la vista USER_VARRAYS se obtiene informacion de las tablas que tienen columnas VARRAY



--Uso de ese tipo en la declaracion de una columna en una tabla

CREATE TABLE AGENDA 
(
  NOMBRE VARCHAR2(15),
  TELEF TELEFONO
);

DESC AGENDA;




--2) MANIPULACION DE TUPLAS (insert, update,delete) en una tabla con columnas VARRAY


--Insercion de tuplas

SELECT * FROM AGENDA;


INSERT INTO AGENDA VALUES 
('MANUEL', TELEFONO ('656008876', '927986655', '639883300')); 


INSERT INTO AGENDA (NOMBRE, TELEF) VALUES 
       ('MARTA', TELEFONO ('649500800'));

COMMIT;


SELECT * FROM AGENDA ;



--Modificacion de tuplas


  UPDATE AGENDA 
  SET TELEF =  TELEFONO ('656008876', 
                         '927986655')
  WHERE NOMBRE = 'MANUEL';



--3) RECORRER los elementos de un array

--   (el metodo COUNT aplicado a una columna de tipo VARRAY proporciona el tamaÒo del array)


DECLARE
  CURSOR C1 IS SELECT * FROM AGENDA;
  CAD VARCHAR2(50);
BEGIN
  FOR I IN C1 LOOP
    DBMS_OUTPUT.PUT_LINE(I.NOMBRE ||
        ', Numero de Telefonos: '||I.TELEF.COUNT);
    CAD:='*';
    FOR J IN 1 .. I.TELEF.COUNT LOOP   
      CAD:=CAD ||I.TELEF(J)||'*';
    END LOOP;
    DBMS_OUTPUT.PUT_LINE(CAD);
  END LOOP;
END;
/



--4) DEFINICION DE UN PROCEDIMIENTO CON PARAMETROS DE TIPO VARRAY para insertar datos en una tabla

-- El procedimiento recibe el nombre y el array de telefonos



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



--5) EJEMPLO que visualiza los telefonos de un nombre.
--   (metodos asociados a un VARRAY: COUNT, FIRST, LAST, LIMIT, EXTEND, TRIM, DELETE, ...)

DECLARE 
  TEL TELEFONO := TELEFONO(NULL, NULL, NULL); 
BEGIN
  SELECT TELEF INTO  TEL FROM AGENDA WHERE NOMBRE = 'MARTA';

  --Visualizar Datos
  DBMS_OUTPUT.PUT_LINE('N∫ DE TELEFONOS ACTUALES:    ' || TEL.COUNT);
  DBMS_OUTPUT.PUT_LINE('INDICE DEL PRIMER ELEMENTO:  ' || TEL.FIRST);
  DBMS_OUTPUT.PUT_LINE('INDICE DEL ULTIMO ELEMENTO:  ' || TEL.LAST);
  DBMS_OUTPUT.PUT_LINE('MAXIMO N∫ DE TLFS PERMITIDO: ' || TEL.LIMIT);

  --AÒade un numero de telefono a MARTA   
  TEL.EXTEND; 
  TEL(TEL.COUNT):= '123000000';     
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MARTA';   
    
  --Elimina un telefono   
  SELECT TELEF INTO TEL FROM AGENDA WHERE NOMBRE = 'MANUEL';     
  TEL.TRIM;   --Elimina el ultimo elemento del array    
  TEL.DELETE; --Elimina todos los elementos
  UPDATE AGENDA A SET A.TELEF = TEL WHERE NOMBRE = 'MANUEL';    
END;
/

------------------------------------------------------------
--ACTIVIDADES

--Definir una funcion almacenada que reciba un nombre de la agenda y devuelva el primer tel√©fono que tenga. Realiza un bloque PL/SQL que haga uso de la funcion

--Modificar el tipo T_ALUMNO para que incluya un VARRAY de hasta 5 tel√©efonos y otro array de 10 elementos que contenga las faltas acumuladas en cada uno de los 10 meses del curso escolar (de Septiembre a Junio).

--Redefinir el constructor adecuadamente

--Insertar dos alumnos; del primer se conocen dos n√∫meros de telefono y las faltas de los meses de septiembre a Diciembre. Del segundo no se tienen datos sobre telefonos ni faltas

--Modificar el segundo alumno indicando 3 numeros de telefono y 10 faltas en septiembre

--Mostrar los datos que se tienen de todos los alumnos, incluyendo telefonos y faltas






