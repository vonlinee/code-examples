-- 初始化工作
-- 视频教程：https://www.bilibili.com/video/BV1KW411u7vy?p=29  - 尚硅谷周阳老师
CREATE DATABASE `mysql_learn` /*!40100 DEFAULT CHARACTER SET utf8 */;

-- 常用SQL
-- 1.索引相关 https://blog.csdn.net/lixiangss1993/article/details/86078509

-- 一：创建索引
-- 1.PRIMARY  KEY（主键索引）
ALTER TABLE `table_name` ADD PRIMARY KEY (`column`)
-- 2.UNIQUE(唯一索引)
ALTER TABLE `table_name` ADD UNIQUE (`column`)
-- 3.INDEX(普通索引)
ALTER TABLE `table_name` ADD INDEX index_name (`column`)
-- 4.FULLTEXT(全文索引)
ALTER TABLE `table_name` ADD FULLTEXT (`column`)
-- 5.多列索引
ALTER TABLE `table_name` ADD INDEX index_name (`column1`, `column2`, `column3`)

-- 二：使用CREATE INDEX语句对表增加索引。能够增加普通索引和UNIQUE索引两种。其格式如下：
CREATE INDEX index_name ON table_name (column_list) ;
CREATE UNIQUE INDEX index_name ON table_name (column_list) ;
-- 说明：table_name、index_name和column_list具有与ALTER TABLE语句中相同的含义，索引名不可选。
-- 另外，不能用CREATE INDEX语句创建PRIMARY KEY索引。

-- 删除索引
-- 删除索引可以使用ALTER TABLE或DROP INDEX语句来实现。
-- DROP INDEX可以在ALTER TABLE内部作为一条语句处理，其格式如下：
DROP INDEX index_name ON table_name ;
ALTER TABLE table_name DROP INDEX index_name ;
ALTER TABLE table_name DROP PRIMARY KEY ;
-- 其中，在前面的两条语句中，都删除了table_name中的索引index_name。而在最后一条语句中，只在删除PRIMARY KEY索引中使用，
-- 因为一个表只可能有一个PRIMARY KEY索引，因此不需要指定索引名。如果没有创建PRIMARY KEY索引，但表具有一个或多个UNIQUE索引，
-- 则MySQL将删除第一个UNIQUE索引。如果从表中删除某列，则索引会受影响。对于多列组合的索引，如果删除其中的某列，则该列也会从索引中删除。
-- 如果删除组成索引的所有列，则整个索引将被删除。

-- P29 Explain之Extra字段介绍
DROP TABLE IF EXISTS t1;
CREATE TABLE IF NOT EXISTS `t1` (
   `col1` VARCHAR(10) NOT NULL DEFAULT '',
   `col2` VARCHAR(10) NOT NULL DEFAULT '',
   `col3` VARCHAR(10) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

EXPLAIN SELECT col1 FROM t1 WHERE col1 = 'ac' ORDER BY col3;
-- 添加索引
ALTER TABLE `t1` ADD INDEX IDX_COL1_COL2_COL3 (`col1`, `col2`, `col3`)
-- 再次执行这条SQL
EXPLAIN SELECT col1 FROM t1 WHERE col1 = 'ac' ORDER BY col3;

-- P30笔记：https://www.bilibili.com/video/BV1KW411u7vy?p=30&spm_id_from=pageDriver
-- Explain热身案例
SELECT d1.name, (SELECT id FROM t3) d2
FROM (SELECT id, name FROM t1 WHERE other_column = '') d1
UNION (SELECT name, id FROM t2)


-- P31
-- https://www.bilibili.com/video/BV1KW411u7vy?p=31&spm_id_from=pageDriver
-- 索引单表优化案例
CREATE TABLE IF NOT EXISTS `article`(
`id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`author_id` INT (10) UNSIGNED NOT NULL,
`category_id` INT(10) UNSIGNED NOT NULL , 
`views` INT(10) UNSIGNED NOT NULL , 
`comments` INT(10) UNSIGNED NOT NULL,
`title` VARBINARY(255) NOT NULL,
`content` TEXT NOT NULL
);

INSERT INTO mysql_learn.article
(id, author_id, category_id, views, comments, title, content)
VALUES(null, 1, 1, 1, 1, '1', '1');
INSERT INTO mysql_learn.article
(id, author_id, category_id, views, comments, title, content)
VALUES(null, 2, 2, 2, 2, '1', '2');
INSERT INTO mysql_learn.article
(id, author_id, category_id, views, comments, title, content)
VALUES(null, 1, 1, 3, 3, '1', '3');


SELECT * FROM mysql_learn.article a;
DELETE FROM mysql_learn.article WHERE 1 = 1;

SHOW INDEX FROM mysql_learn.article;

# 查询category_id为1且comments大于1的情况下，满足views最多的article_id。
# 理解：只有一条
SELECT * FROM article a 
WHERE category_id = '1' AND comments > 1 ORDER BY views DESC LIMIT 0, 1;

-- 优化过程：先看执行计划，再想优化办法
EXPLAIN SELECT * FROM article a 
WHERE category_id = '1' AND comments > 1 ORDER BY views DESC LIMIT 0, 1;
-- 范围查询之后的索引会失效


-- 双表优化
CREATE TABLE IF NOT EXISTS `class`(
`id` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`card` INT (10) UNSIGNED NOT NULL
);
CREATE TABLE IF NOT EXISTS `book`(
`bookid` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`card` INT (10) UNSIGNED NOT NULL
);


insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));
insert into class(card) values(floor(1+(rand()*20)));

insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));
insert into book(card) values(floor(1+(rand()*20)));


-- P33
CREATE TABLE IF NOT EXISTS `phone`(
`phoneid` INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
`card` INT (10) UNSIGNED NOT NULL
)ENGINE = INNODB;

insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));
insert into phone(card) values(floor(1+(rand()*20)));

-- P34
CREATE TABLE staffs(
id INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(24)NOT NULL DEFAULT'' COMMENT'姓名',
`age` INT NOT NULL DEFAULT 0 COMMENT'年龄',
`pos` VARCHAR(20) NOT NULL DEFAULT'' COMMENT'职位',
`add_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT'入职时间'
)CHARSET utf8 COMMENT'员工记录表';

insert into staffs(NAME,age,pos,add_time) values('z3',22,'manager',NOW());
insert into staffs(NAME,age,pos,add_time) values('July',23,'dev',NOW());
insert into staffs(NAME,age,pos,add_time) values('2000',23,'dev',NOW());


-- P40
CREATE TABLE tbl_user(
`id` INT(11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR(20) DEFAULT NULL,
`age`INT(11) DEFAULT NULL,
`email` VARCHAR(20) DEFAULT NULL,
PRIMARY KEY(`id`)
)ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

insert into tbl_user(NAME,age,email) values('1aa1',21,'b@163.com');
insert into tbl_user(NAME,age,email) values('2aa2',222,'a@163.com');
insert into tbl_user(NAME,age,email) values('3aa3',265,'c@163.com');
insert into tbl_user(NAME,age,email) values('4aa4',21,'d@163.com');


-- P44
create table test03(
id int primary key not null auto_increment,
c1 char(10),
c2 char(10),
c3 char(10),
c4 char(10),
c5 char(10)
);

insert into test03(c1,c2,c3,c4,c5) values('a1','a2','a3','a4','a5');
insert into test03(c1,c2,c3,c4,c5) values('b1','b2','b3','b4','b5');
insert into test03(c1,c2,c3,c4,c5) values('c1','c2','c3','c4','c5');
insert into test03(c1,c2,c3,c4,c5) values('d1','d2','d3','d4','d5');
insert into test03(c1,c2,c3,c4,c5) values('e1','e2','e3','e4','e5');

-- P48
create table tblA(
age int,
birth timestamp not null
);

insert into tblA(age,birth) values(22,now());
insert into tblA(age,birth) values(23,now());
insert into tblA(age,birth) values(24,now());

-- P50
create table dept(
id int unsigned primary key auto_increment,
deptno mediumint unsigned not null default 0,
dname varchar(20) not null default "",
loc varchar(13) not null default ""
)engine=innodb default charset=GBK;

CREATE TABLE emp(
id int unsigned primary key auto_increment,
empno mediumint unsigned not null default 0,
ename varchar(20) not null default "",
job varchar(9) not null default "",
mgr mediumint unsigned not null default 0,
hiredate date not null,
sal decimal(7,2) not null,
comm decimal(7,2) not null,
deptno mediumint unsigned not null default 0
)ENGINE=INNODB DEFAULT CHARSET=GBK;
-- //函数
delimiter $$
create function ran_string(n int) returns varchar(255)
begin
declare chars_str varchar(100) default 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
declare return_str varchar(255) default '';
declare i int default 0;
while i < n do
set return_str = concat(return_str,substring(chars_str,floor(1+rand()*52),1));
set i=i+1;
end while;
return return_str;
end $$
-- //函数
delimiter $$
create function rand_num() returns int(5)
begin
declare i int default 0;
set i=floor(100+rand()*10);
return i;
end $$
-- //存储过程
delimiter $$ 
create procedure insert_emp(in start int(10),in max_num int(10))
begin
declare i int default 0;
set autocommit = 0;
repeat
set i = i+1;
insert into emp(empno,ename,job,mgr,hiredate,sal,comm,deptno) values((start+i),ran_string(6),'salesman',0001,curdate(),2000,400,rand_num());
until i=max_num
end repeat;
commit;
end $$
-- 存储过程
delimiter $$ 
create procedure insert_dept(in start int(10),in max_num int(10))
begin
declare i int default 0;
set autocommit = 0;
repeat
set i = i+1;
insert into dept(deptno,dname,loc) values((start+i),ran_string(10),ran_string(8));
until i=max_num
end repeat;
commit;
end $$


-- 数据库锁原理
-- https://www.bilibili.com/video/BV1KW411u7vy?p=52&spm_id_from=pageDriver

-- P54
CREATE TABLE mylock (
id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
name varchar(20) DEFAULT ''
) engine myisam;
INSERT INTO mylock(name) VALUES('a');
INSERT INTO mylock(name) VALUES('b');
INSERT INTO mylock(name) VALUES('c');
INSERT INTO mylock(name) VALUES('d');
INSERT INTO mylock(name) VALUES('e');

SELECT * FROM mylock;

-- 手动增加表锁
LOCK table表名字iead(WRITE)，表名字2 READ(WRITE)，其它;



















-- P57
CREATE TABLE test_innodb_lock (
a INT(11),
b VARCHAR(16)
)ENGINE=INNODB;

