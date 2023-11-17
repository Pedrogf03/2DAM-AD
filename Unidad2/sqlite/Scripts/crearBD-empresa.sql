CREATE TABLE  departamentos (
                                               dept_no	INTEGER		CONSTRAINT dep_dep_pk PRIMARY KEY ASC AUTOINCREMENT, 
                                               dnombre	VARCHAR(15)	CONSTRAINT dep_dno_ck UNIQUE, 
                                               loc	VARCHAR(15)
                                              );

CREATE TABLE empleados (
                                           emp_no	INTEGER		CONSTRAINT emp_emp_pk PRIMARY KEY ASC AUTOINCREMENT,
                                           apellido	VARCHAR(10)	CONSTRAINT emp_ape_nn NOT NULL,
                                           oficio	VARCHAR(10)		CONSTRAINT emp_ofi_nn NOT NULL,
                                           dir		INTEGER,
                                           fecha_alt	DATE,
                                           salario      REAL,
                                           comision	REAL,
                                           dept_no	INTEGER		CONSTRAINT emp_dep_fk REFERENCES departamentos(dept_no) 
                                                                                                        						 ON DELETE NO ACTION
						                                                                                                         ON UPDATE CASCADE
                                          );


