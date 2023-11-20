/*----MySQL----*/

------------------------------Collections Mappings------------------------------

create table EMPLOYEE (
    id INT NOT NULL auto_increment,
    first_name VARCHAR(20) default NULL,
    last_name VARCHAR(20) default NULL,
    salary INT default NULL,
    PRIMARY KEY (id) 
);

--one-to-many relationship between EMPLOYEE and CERTIFICATE: Set, SortedSet, Bag (Collection)
create table CERTIFICATE ( 
    id INT NOT NULL auto_increment, 
    certificate_name VARCHAR(30) default NULL, 
    employee_id INT default NULL, /*one: the (unique) certificate's owner */
    PRIMARY KEY (id)
--  , FOREIGN KEY (employee_id) REFERENCES EMPLOYEE(id)ON DELETE SET NULL ON UPDATE CASCADE
);

--one-to-many relationship between EMPLOYEE and CERTIFICATE: List
create table CERTIFICATELIST ( 
    id INT NOT NULL auto_increment, 
    certificate_name VARCHAR(30) default NULL, 
    employee_id INT default NULL, /*one: the (unique) certificate's owner */
    idx INT default NULL,         /*Index of each certificate on its employee's list*/
    PRIMARY KEY (id)
 -- , FOREIGN KEY (employee_id) REFERENCES EMPLOYEE(id)ON DELETE SET NULL ON UPDATE CASCADE
);

--one-to-many relationship between EMPLOYEE and CERTIFICATE: Map, SortedMap
create table CERTIFICATEMAP ( 
    id INT NOT NULL auto_increment,
    certificate_type VARCHAR(40) default NULL,  /*certificate type*/
    certificate_name VARCHAR(30) default NULL,
    employee_id INT default NULL,               /*one: the (unique) certificate's owner */
    PRIMARY KEY (id)
 );


------------------------------Associations Mappings-----------------------------

--many-to-one unidirectional, and ,one-to-one unidirectional on foreign key 
--relationship between EMPLOYEE and ADDRESS
create table ADDRESS (
    id INT NOT NULL auto_increment,
    street_name VARCHAR(40) default NULL,
    city_name VARCHAR(40) default NULL,
    state_name VARCHAR(40) default NULL,
    zipcode VARCHAR(10) default NULL,
    PRIMARY KEY (id)
);

--one-to-one unidirectional on primary key (ADDRESS is a 'weak'/'subtype entity' in Entity-Relationship model)
--relationship between ADDRESS and EMPLOYEE
--The column "idEmployee" is not autoincrement because it must be assigned
--a id value from "Employee" table
--Note: MySQL doesn't implement FOREIGN KEY, so when an Employee is deleted
--its address is not deleted in cascade (as it should), as a consecuence,
--the database remains in a inconsistent state, because exists an address
--that references an employee that doesn't exists
create table ADDRESS_ONE_TO_ONE_PK (
    idEmployee INT NOT NULL,   --it's not autoincrement
    street_name VARCHAR(40) default NULL,
    city_name VARCHAR(40) default NULL,
    state_name VARCHAR(40) default NULL,
    zipcode VARCHAR(10) default NULL,
    PRIMARY KEY (idEmployee)
-- ,FOREIGN KEY (idEmployee) REFERENCES EMPLOYEE(id) ON DELETE CASCADE ON UPDATE CASCADE
);

--many-to-one unidirectional relationship between EMPLOYEE and ADRESS
--many-to-one unidirectional with join table relationship between EMPLOYEE and ADRESS
create table EMPLOYEE_MANY_TO_ONE (
    id INT NOT NULL auto_increment,
    first_name VARCHAR(20) default NULL,
    last_name VARCHAR(20) default NULL,
    salary INT default NULL,
    address INT NOT NULL,              /*Employee's address*/
    PRIMARY KEY (id)
 -- , FOREIGN KEY (address) REFERENCES ADDRESS(id) ON DELETE SET NULL ON UPDATE CASCADE
);

-- one-to-one unidirectional on foreign key relationship between EMPLOYEE and ADRESS
create table EMPLOYEE_ONE_TO_ONE (
    id INT NOT NULL auto_increment,
    first_name VARCHAR(20) default NULL,
    last_name VARCHAR(20) default NULL,
    salary INT default NULL,
    address INT NOT NULL     UNIQUE,              /*UNIQUE Employee's address*/
    PRIMARY KEY (id)
 -- , FOREIGN KEY (address) REFERENCES ADDRESS(id) ON DELETE SET NULL ON UPDATE CASCADE
);

-------------UNIDIRECTIONAL WITH JOIN TABLE

-- many-to-many unidirectional join table
-- one-to-many unidirecctional join table
-- many-to-one unidirectional  join table
create table CERTIFICATE_WITH_JOIN_TABLE (
    id INT NOT NULL auto_increment,
    certificate_name VARCHAR(30) default NULL,
    PRIMARY KEY (id)
);

-- many-to-many unidirectional join table
create table EMP_CERT_MANY_TO_MANY (
    employee_id INT NOT NULL,
    certificate_id INT NOT NULL,
    PRIMARY KEY (employee_id,certificate_id)
   -- , FOREIGN KEY(employee_id) REFERENCES EMPLOYEE(id) 
   --                            ON DELETE RESTRICT ON UPDATE CASCADE,
   -- , FOREIGN KEY(certificate_id) REFERENCES CERTIFICATE_MANY_TO_MANY(id)
   --                            ON DELETE RESTRICT ON UPDATE CASCADE
 );

-- one-to-many unidirectional join table
create table EMP_CERT_ONE_TO_MANY (
    employee_id INT NOT NULL,
    certificate_id INT NOT NULL,
    PRIMARY KEY (certificate_id)
   -- , FOREIGN KEY(employee_id) REFERENCES EMPLOYEE(id) 
   --                            ON DELETE RESTRICT ON UPDATE CASCADE,
   -- , FOREIGN KEY(certificate_id) REFERENCES CERTIFICATE_MANY_TO_MANY(id)
   --                            ON DELETE RESTRICT ON UPDATE CASCADE
 );

-- many-to-one unidirectional join table
create table EMP_ADDR_MANY_TO_ONE (
    employee_id INT NOT NULL,
    address_id INT NOT NULL,
    PRIMARY KEY (employee_id)
   -- , FOREIGN KEY(employee_id) REFERENCES EMPLOYEE(id) 
   --                            ON DELETE RESTRICT ON UPDATE CASCADE,
   -- , FOREIGN KEY(address_id) REFERENCES ADDRESS(id)
   --                            ON DELETE RESTRICT ON UPDATE CASCADE
 );