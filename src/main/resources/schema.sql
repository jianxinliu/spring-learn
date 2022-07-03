CREATE TABLE student (
	"class" int4 NULL,
	"name" varchar(10) NULL,
	sno varchar(10) NOT NULL,
	sex varchar(8) NULL,
	age int4 NULL,
	height int4 NULL,
	weight int4 NULL,
	iq int4 NULL,
	city varchar(10) NULL,
	country varchar(10) NULL,
	ucl int4 NULL,
	lcl int4 NULL,
	lsl int4 NULL,
	usl int4 NULL,
	CONSTRAINT student_pk2 PRIMARY KEY (sno)
);