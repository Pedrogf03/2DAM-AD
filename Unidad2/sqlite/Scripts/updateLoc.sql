CREATE PROCEDURE updateLoc (num INT, nueva_loc VARCHAR(20))
BEGIN
  -- Declarar una variable para almacenar la antigua localización
  DECLARE antigua_loc VARCHAR(20);
  -- Declarar una variable para almacenar el valor booleano
  DECLARE cambio BOOLEAN;
  -- Obtener la antigua localización del departamento
  SELECT loc INTO antigua_loc FROM departamentos WHERE dept_no = num;
  -- Comparar la antigua localización con la nueva
  IF antigua_loc = nueva_loc THEN
    -- Si son iguales, asignar falso al valor booleano
    SET cambio = FALSE;
  ELSE
    -- Si son diferentes, asignar verdadero al valor booleano
    SET cambio = TRUE;
    -- Actualizar la localización del departamento
    UPDATE departamentos SET loc = nueva_loc WHERE dept_no = num;
  END IF;
  -- Mostrar el resultado
  SELECT antigua_loc AS 'Antigua localización', cambio AS 'Cambio';
END
