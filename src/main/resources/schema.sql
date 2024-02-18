------------------------------------------------------------
--        Script Postgres
------------------------------------------------------------
DROP TABLE IF EXISTS "BOOKS";
DROP TABLE IF EXISTS "AUTHORS";

------------------------------------------------------------
-- Table: AUTHOR
------------------------------------------------------------
CREATE TABLE public.AUTHORS(
	id     SERIAL NOT NULL ,
	name   VARCHAR (255) NOT NULL ,
	age    INT  NOT NULL  ,
	CONSTRAINT AUTHORS_PK PRIMARY KEY (id)
);


------------------------------------------------------------
-- Table: BOOKS
------------------------------------------------------------
CREATE TABLE public.BOOKS(
	isbn    VARCHAR (255) NOT NULL ,
	title   VARCHAR (255) NOT NULL ,
	id      INT  NOT NULL  ,
	CONSTRAINT BOOKS_PK PRIMARY KEY (isbn)

	,CONSTRAINT BOOKS_AUTHORS_FK FOREIGN KEY (id) REFERENCES public.AUTHORS(id)
);