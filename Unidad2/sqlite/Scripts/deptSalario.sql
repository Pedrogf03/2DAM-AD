CREATE PROCEDURE deptSalario (num INT)
BEGIN
  -- Declarar una variable para almacenar la suma de los salarios
  DECLARE suma_salarios DECIMAL(10,2);
  -- Obtener la suma de los salarios de los empleados del departamento
  SELECT SUM(salario) INTO suma_salarios FROM empleados WHERE dept_no = num;
  -- Mostrar el resultado
  SELECT suma_salarios AS 'Suma de salarios';
END