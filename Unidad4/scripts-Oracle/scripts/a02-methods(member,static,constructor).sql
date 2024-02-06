
--Ejemplo(1): tipo con variables de instancia y métodos miembro

--Declaracion del tipo con las variables de instancia y la especificacion de los metodos (funciones o procedimientos): 
--
--CREATE O REPLACE TYPE x AS OBJECT
--(vi_1 tipo1, ...,vi_n tipon,
-- MEMBER PROCEDURE m1 (p1 tipop1,...),
-- MEMBER FUNCTION  m2 (p1 tipop1,...) RETURN tipo
--)

CREATE OR REPLACE TYPE DIRECCION AS OBJECT
(
  CALLE  VARCHAR2(25),
  CIUDAD VARCHAR2(20),
  CODIGO_POST NUMBER(5),
  MEMBER PROCEDURE SET_CALLE(C VARCHAR2),
  MEMBER FUNCTION GET_CALLE RETURN VARCHAR2  
);
/

--Definicion del cuerpo del tipo:
-- CREATE OR REPLACE TYPE BODY x AS
-- 	MEMBER PROCEDURE m1 (p1 tipop1,...) IS
--		BEGIN ... END;
--
-- 	MEMBER FUNCTION  m2 (p1 tipop1,...) RETURN tipo IS
--	BEGIN ... RETURN valor ...END;
-- END;

CREATE OR REPLACE TYPE BODY DIRECCION AS
  --
  MEMBER PROCEDURE SET_CALLE(C VARCHAR2) IS
  BEGIN
    CALLE := C;
  END;
  --
  MEMBER FUNCTION GET_CALLE RETURN VARCHAR2 IS
  BEGIN
    RETURN CALLE;
  END;
END;
/

----Bloque PL/SQL donde se crea un objeto (constructor por defecto) y se invocana metodos (funciones o procedimientos)

DECLARE
  DIR DIRECCION := DIRECCION(NULL,NULL,NULL);
BEGIN
  DIR.SET_CALLE('La Mina, 3');
  DBMS_OUTPUT.PUT_LINE(DIR.GET_CALLE); 
  
  DIR := NEW DIRECCION ('C/Madrid 10','Toledo',45002);
  DBMS_OUTPUT.PUT_LINE(DIR.GET_CALLE); 
END;
/

--Ejemplo(2): tipo con constructor

--Declaracion del tipo con variables de instancia y constructor

CREATE OR REPLACE TYPE RECTANGULO AS OBJECT
(
  BASE   NUMBER,
  ALTURA NUMBER,
  AREA   NUMBER,
  CONSTRUCTOR FUNCTION RECTANGULO(BASE NUMBER, ALTURA NUMBER)
    RETURN SELF AS RESULT
);
/

CREATE OR REPLACE TYPE BODY RECTANGULO AS
-- 
  CONSTRUCTOR FUNCTION RECTANGULO (BASE NUMBER, ALTURA NUMBER) RETURN SELF AS RESULT IS  
  BEGIN
    SELF.BASE := BASE;
    SELF.ALTURA := ALTURA;
    SELF.AREA := BASE * ALTURA;
    RETURN;
  END;
END;
/

----Bloque PL/SQL donde se crea un objeto invocando al constructor

DECLARE

  R1 RECTANGULO := RECTANGULO(NULL, NULL, NULL);
  R2 RECTANGULO := RECTANGULO(10, 20);

BEGIN
  R1 := NEW RECTANGULO(10, 20, 2000);
  DBMS_OUTPUT.PUT_LINE('AREA R1: '||R1.AREA);

  DBMS_OUTPUT.PUT_LINE('AREA R2: '||R2.AREA);
END;
/


--Ejemplo(3): tipo con constructor, miembro (procedimiento) y miembro STATIC
-------------------------------------------------------------------------
CREATE OR REPLACE TYPE RECTANGULO AS OBJECT
(
  BASE   NUMBER,
  ALTURA NUMBER,
  AREA   NUMBER,
  STATIC PROCEDURE PROC1 (ANCHO INTEGER, ALTO INTEGER),
  MEMBER PROCEDURE PROC2 (ANCHO INTEGER, ALTO INTEGER),
  CONSTRUCTOR FUNCTION RECTANGULO (BASE NUMBER, ALTURA NUMBER)
                       RETURN SELF AS RESULT
);
/  


CREATE TABLE TABLAREC (VALOR INTEGER);
/

CREATE OR REPLACE TYPE BODY RECTANGULO AS
-- 
  CONSTRUCTOR FUNCTION RECTANGULO (BASE NUMBER, ALTURA NUMBER) RETURN SELF AS RESULT IS  
  BEGIN
    SELF.BASE := BASE;
    SELF.ALTURA := ALTURA;
    SELF.AREA := BASE * ALTURA;
    RETURN;
  END;
--
  STATIC PROCEDURE PROC1 (ANCHO INTEGER, ALTO INTEGER) IS
  BEGIN
    INSERT INTO TABLAREC VALUES(ANCHO*ALTO);
    --ALTURA := ALTO; --ERROR NO SE PUEDE ACCEDER A LOS ATRIBUTOS DEL TIPO
    DBMS_OUTPUT.PUT_LINE('FILA INSERTADA');
    COMMIT;
  END;
--
  MEMBER PROCEDURE PROC2 (ANCHO INTEGER, ALTO INTEGER) IS
  BEGIN    
    SELF.ALTURA := ALTO; --SE PUEDE ACCEDER A LOS ATRIBUTOS DEL TIPO
    SELF.BASE := ANCHO;
    AREA := ALTURA*BASE;
    INSERT INTO TABLAREC VALUES(AREA);
    DBMS_OUTPUT.PUT_LINE('FILA INSERTADA');
    COMMIT;
  END;
END;
/


--
DECLARE
  R1 RECTANGULO;
  R2 RECTANGULO;
  R3 RECTANGULO := RECTANGULO(NULL, NULL, NULL);
BEGIN
  R1 := NEW RECTANGULO(10, 20, 200);
  DBMS_OUTPUT.PUT_LINE('AREA R1: '||R1.AREA);

  R2 := NEW RECTANGULO(10,20);
  DBMS_OUTPUT.PUT_LINE('AREA R2: '||R2.AREA); 
 
  R3.BASE := 5;
  R3.ALTURA := 15;
  R3.AREA := R3.BASE * R3.ALTURA;
  DBMS_OUTPUT.PUT_LINE('AREA R3: '||R3.AREA);

  --USO DE LOS METODOS DEL TIPO  RECTANGULO
  RECTANGULO.PROC1(10, 20);   --LLAMADA AL METODO STATIC
  --RECTANGULO.PROC2(20, 30); --ERROR, LLAMADA AL METODO MEMBER
  --R1.PROC1(5, 6);          --ERROR, LLAMADA AL METODO STATIC 
  R1.PROC2(5, 10);           --LLAMADA AL METODO MEMBER  
END;
/

SELECT * FROM TABLAREC;


