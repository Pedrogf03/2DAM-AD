CREATE PROCEDURE deleteEmpDpto (num INT)
BEGIN
  DELETE FROM empleados WHERE dept_no = num;
  DELETE FROM departamentos WHERE dept_no = num;
END
