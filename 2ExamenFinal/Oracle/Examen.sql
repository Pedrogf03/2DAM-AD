

-- COLECCI�N DE DIRECCIONES DE UN SEGUIDOR
CREATE TYPE CORREO AS VARRAY(5) OF VARCHAR(100);
/

-- TIPO SEGUIDOR
CREATE OR REPLACE TYPE SEGUIDOR AS OBJECT
(
  ID NUMBER,
  NICK VARCHAR2(45),
  CORREOS CORREO,
  PAIS VARCHAR2(45),
  -- Getters y Setters
  MEMBER PROCEDURE SET_NICK(N VARCHAR2),
  MEMBER FUNCTION GET_NICK RETURN VARCHAR2,
  MEMBER PROCEDURE SET_CORREOS(C CORREO),
  MEMBER FUNCTION GET_CORREOS RETURN CORREO,
  MEMBER PROCEDURE SET_PAIS(P VARCHAR2),
  MEMBER FUNCTION GET_PAIS RETURN VARCHAR2,
  -- Constructor
  CONSTRUCTOR FUNCTION SEGUIDOR(NICK VARCHAR2, CORREOS CORREO, PAIS VARCHAR2) RETURN SELF AS RESULT
);
/


CREATE OR REPLACE TYPE BODY SEGUIDOR AS
--
  MEMBER PROCEDURE SET_NICK(N VARCHAR2) IS
  BEGIN
    NICK := N;
  END;
  --
  MEMBER FUNCTION GET_NICK RETURN VARCHAR2 IS
  BEGIN
    RETURN NICK;
  END;
--
  MEMBER PROCEDURE SET_CORREOS(C CORREO) IS
  BEGIN
    CORREOS := C;
  END;
  --
  MEMBER FUNCTION GET_CORREOS RETURN CORREO IS
  BEGIN
    RETURN CORREOS;
  END;
--
  MEMBER PROCEDURE SET_PAIS(P VARCHAR2) IS
  BEGIN
    PAIS := P;
  END;
  --
  MEMBER FUNCTION GET_PAIS RETURN VARCHAR2 IS
  BEGIN
    RETURN PAIS;
  END;
-- 
  CONSTRUCTOR FUNCTION SEGUIDOR(NICK VARCHAR2, CORREOS CORREO, PAIS VARCHAR2) RETURN SELF AS RESULT IS
  BEGIN
    SELF.NICK := NICK;
    SELF.CORREOS := CORREOS;
    SELF.PAIS := PAIS;
    RETURN;
  END;
--
END;
/

-- TIPO LIDER
CREATE OR REPLACE TYPE LIDER AS OBJECT
(
  ID NUMBER,
  NOMBRE VARCHAR2(45),
  FECHA_ALTA DATE,
  SEGUIDORES NUMBER,
  -- Getters y Setters
  MEMBER PROCEDURE SET_NOMBRE(N VARCHAR2),
  MEMBER FUNCTION GET_NOMBRE RETURN VARCHAR2,
  MEMBER PROCEDURE SET_FECHA_ALTA(F DATE),
  MEMBER FUNCTION GET_FECHA_ALTA RETURN DATE,
  MEMBER PROCEDURE SET_SEGUIDORES(S NUMBER),
  MEMBER FUNCTION GET_SEGUIDORES RETURN NUMBER,
  -- Constructor
  CONSTRUCTOR FUNCTION LIDER(NOMBRE VARCHAR2, FECHA_ALTA DATE, SEGUIDORES NUMBER) RETURN SELF AS RESULT,
  -- Ordenar y Comparar por seguidores
  MAP MEMBER FUNCTION POR_SEGUIDORES RETURN INT
);
/

CREATE OR REPLACE TYPE BODY LIDER AS
--
  MEMBER PROCEDURE SET_NOMBRE(N VARCHAR2) IS
  BEGIN
    NOMBRE := N;
  END;
  --
  MEMBER FUNCTION GET_NOMBRE RETURN VARCHAR2 IS
  BEGIN
    RETURN NOMBRE;
  END;
--
  MEMBER PROCEDURE SET_FECHA_ALTA(F DATE) IS
  BEGIN
    FECHA_ALTA := F;
  END;
  --
  MEMBER FUNCTION GET_FECHA_ALTA RETURN DATE IS
  BEGIN
    RETURN FECHA_ALTA;
  END;
--
  MEMBER PROCEDURE SET_SEGUIDORES(S NUMBER) IS
  BEGIN
    SEGUIDORES := S;
  END;
  --
  MEMBER FUNCTION GET_SEGUIDORES RETURN NUMBER IS
  BEGIN
    RETURN SEGUIDORES;
  END;
-- 
  CONSTRUCTOR FUNCTION LIDER(NOMBRE VARCHAR2, FECHA_ALTA DATE, SEGUIDORES NUMBER) RETURN SELF AS RESULT IS
  BEGIN
    SELF.NOMBRE := NOMBRE;
    SELF.FECHA_ALTA := FECHA_ALTA;
    SELF.SEGUIDORES := SEGUIDORES;
    RETURN;
  END;
--
  MAP MEMBER FUNCTION POR_SEGUIDORES RETURN INT IS
  BEGIN
    RETURN SEGUIDORES;
  END;
--
END;
/

-- TABLA ANIDADA DE SEGUIDORES
CREATE TYPE SEGUIDORESLIDER AS TABLE OF SEGUIDOR;
/

-- TABLA LIDERES
CREATE TABLE LIDERES (
  LIDER LIDER,
  SEGS SEGUIDORESLIDER
) NESTED TABLE SEGS STORE AS SEGS;
/

DECLARE
  L1 LIDER := LIDER('Lider1', '04-04-2024', 0);
  L2 LIDER := LIDER('Lider2', '04-04-2024', 0);
  
  S1 SEGUIDOR := SEGUIDOR('Seguidor1', CORREO('example@gmail.com', null, null), 'ESPA�A');
  S2 SEGUIDOR := SEGUIDOR('Seguidor2', CORREO('example2@gmail.com', 'example5@gmail.com', null), 'ESPA�A');
  S3 SEGUIDOR := SEGUIDOR('Seguidor3', CORREO('example3@gmail.com', 'example6@gmail.com', 'example7@gmail.com'), 'ESPA�A');
  S4 SEGUIDOR := SEGUIDOR('Seguidor4', CORREO('example4@gmail.com', null, null), 'ESPA�A');
BEGIN  

    INSERT INTO LIDERES VALUES (L1, SEGUIDORESLIDER(S1, S2));
    L1.SET_SEGUIDORES(L1.GET_SEGUIDORES + 2);
    
    INSERT INTO LIDERES VALUES (L2, SEGUIDORESLIDER(S3, S4));
    L2.SET_SEGUIDORES(L2.GET_SEGUIDORES + 2);

END;
/










