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


-- 单表优化
EXPLAIN SELECT id, author_id FROM artic1e WHERE category_id = l AND comments > l ORDER BY views DESC LIMIT 1;

mysql> EXPLAIN SELECT id, author_id FROM article WHERE category_id = 1 AND comments > 1 ORDER BY views DESC LIMIT 1;
+----+-------------+---------+------------+------+---------------+------+---------+------+------+----------+-----------------------------+
| id | select_type | table   | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra                       |
+----+-------------+---------+------------+------+---------------+------+---------+------+------+----------+-----------------------------+
|  1 | SIMPLE      | article | NULL       | ALL  | NULL          | NULL | NULL    | NULL |    6 |    16.67 | Using where; Using filesort |
+----+-------------+---------+------------+------+---------------+------+---------+------+------+----------+-----------------------------+
1 row in set, 1 warning (0.00 sec)

有ALL和Using filesort，数据量大了就慢了，需要优化，但现在只有一个主键索引，该怎么加索引？

-- 一般是需要被查询的条件对应的字段上建索引
CREATE INDEX idx_article_ccv ON article(category_id, comments, views);
-- 这里符合最左前缀匹配法则
mysql> show index from article;
+---------+------------+-----------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| Table   | Non_unique | Key_name        | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment |
+---------+------------+-----------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| article |          0 | PRIMARY         |            1 | id          | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
| article |          1 | idx_article_ccv |            1 | category_id | A         |           2 |     NULL | NULL   |      | BTREE      |         |               |
| article |          1 | idx_article_ccv |            2 | comments    | A         |           3 |     NULL | NULL   |      | BTREE      |         |               |
| article |          1 | idx_article_ccv |            3 | views       | A         |           3 |     NULL | NULL   |      | BTREE      |         |               |
+---------+------------+-----------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
4 rows in set (0.00 sec)

-- 再次执行的结果
-- 索引被用到，但是还是有Using filesort
mysql> EXPLAIN SELECT id, author_id FROM article WHERE category_id = 1 AND comments > 1 ORDER BY views DESC LIMIT 1;
+----+-------------+---------+------------+-------+-----------------+-----------------+---------+------+------+----------+---------------------------------------+
| id | select_type | table   | partitions | type  | possible_keys   | key             | key_len | ref  | rows | filtered | Extra                                 |
+----+-------------+---------+------------+-------+-----------------+-----------------+---------+------+------+----------+---------------------------------------+
|  1 | SIMPLE      | article | NULL       | range | idx_article_ccv | idx_article_ccv | 8       | NULL |    2 |   100.00 | Using index condition; Using filesort |
+----+-------------+---------+------------+-------+-----------------+-----------------+---------+------+------+----------+---------------------------------------+
1 row in set, 1 warning (0.00 sec)

mysql> EXPLAIN SELECT id, author_id FROM article WHERE category_id = 1 AND comments = 1 ORDER BY views DESC LIMIT 1;
+----+-------------+---------+------------+------+-----------------+-----------------+---------+-------------+------+----------+-------------+
| id | select_type | table   | partitions | type | possible_keys   | key             | key_len | ref         | rows | filtered | Extra       |
+----+-------------+---------+------------+------+-----------------+-----------------+---------+-------------+------+----------+-------------+
|  1 | SIMPLE      | article | NULL       | ref  | idx_article_ccv | idx_article_ccv | 8       | const,const |    2 |   100.00 | Using where |
+----+-------------+---------+------------+------+-----------------+-----------------+---------+-------------+------+----------+-------------+
1 row in set, 1 warning (0.00 sec)

-- 范围以后的索引会导致失效，中间有个范围导致后面的索引用不上，说明这个索引不合适
DROP INDEX idx_article_ccv ON artic1e

EXPLAIN SELECT id, author_id FROM article WHERE category_id IN (1) AND comments > 1 ORDER BY views DESC LIMIT 1;


#1.2第2次EXPLAIN
EXPLAIN SELECT id,author_id FROM `article` WHERE category_id = 1 AND comments > 1 ORDER BY views DESC LIMIT 1;
EXPLAIN SELECT id,author_id FROM `article` WHERE category_id = 1 AND comments =3 ORDER BY views DESC LIMIT 1;
# 结论:
# type变成了range,这是可以忍受的。但是extra里使用Using filesort仍是无法接受的。#但是我们已经建立了索引,为啥没用呢?
# 这是因为按照BTree索引的工作原理，# 先排序category_id,
# 如果遇到相同的category_id则再排序comments,如果遇到相同的comments则再排序views。#当comments字段在联合索引里处于中间位置时，
# 因comments >1条件是一个范围值(所谓range),
# MySQL无法利用索引再对后面的views部分进行检索,即range类型查询字段后面的索引无效。

CREATE INDEX idx_artic1e_cv ON article(category_id, views) ;

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
-- https://www.bilibili.com/video/BV1KW411u7vy?p=32&spm_id_from=pageDriver

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

-- 以下数据无实际意义，只是为了外键关联条件相等
mysql> select * from class;
+----+------+
| id | card |
+----+------+
|  1 |    2 |
|  2 |   19 |
|  3 |    9 |
|  4 |    8 |
|  5 |   11 |
|  6 |   12 |
|  7 |    7 |
|  8 |   18 |
|  9 |    8 |
| 10 |    5 |
| 11 |   19 |
| 12 |    2 |
| 13 |   10 |
| 14 |    4 |
| 15 |   12 |
| 16 |    6 |
| 17 |   12 |
| 18 |    5 |
| 19 |    6 |
| 20 |   14 |
+----+------+
20 rows in set (0.00 sec)

mysql> select * from book;
+--------+------+
| bookid | card |
+--------+------+
|      1 |   10 |
|      2 |   11 |
|      3 |    2 |
|      4 |   19 |
|      5 |    9 |
|      6 |    7 |
|      7 |    8 |
|      8 |   20 |
|      9 |   13 |
|     10 |    5 |
|     11 |    5 |
|     12 |   12 |
|     13 |    3 |
|     14 |   17 |
|     15 |   15 |
|     16 |    7 |
|     17 |    7 |
|     18 |   13 |
|     19 |    3 |
|     20 |   15 |
+--------+------+
20 rows in set (0.00 sec)


EXPLAIN select * from book inner join class on book.card = c1ass.card;
-- 索引应该加在哪里，是book.card还是c1ass.card

mysql> EXPLAIN select * from book inner join class on book.card = class.card;
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------------------------------------------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra                                              |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------------------------------------------+
|  1 | SIMPLE      | book  | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   20 |   100.00 | NULL                                               |
|  1 | SIMPLE      | class | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   20 |    10.00 | Using where; Using join buffer (Block Nested Loop) |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------------------------------------------+
2 rows in set, 1 warning (0.01 sec)

-- 一定有一个驱动表，小表驱动大表
ALTER TABLE `book` ADD INDEX Y(`card`);
mysql> EXPLAIN select * from book inner join class on book.card = class.card;
+----+-------------+-------+------------+------+---------------+------+---------+------------------------+------+----------+-------------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref                    | rows | filtered | Extra       |
+----+-------------+-------+------------+------+---------------+------+---------+------------------------+------+----------+-------------+
|  1 | SIMPLE      | class | NULL       | ALL  | NULL          | NULL | NULL    | NULL                   |   20 |   100.00 | NULL        |
|  1 | SIMPLE      | book  | NULL       | ref  | Y             | Y    | 4       | mysql_learn.class.card |    1 |   100.00 | Using index |
+----+-------------+-------+------------+------+---------------+------+---------+------------------------+------+----------+-------------+
2 rows in set, 1 warning (0.00 sec)
-- 删除该索引
DROP INDEX Y ON book;

-- 因为左连接，左表的每个字段都要,因此给右边索引,好让右边去四配，而左边不动

ALTER TABLE `class` ADD INDEX A(`card`);

mysql> EXPLAIN select * from book inner join class on book.card = class.card;
+----+-------------+-------+------------+------+---------------+------+---------+-----------------------+------+----------+-------------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref                   | rows | filtered | Extra       |
+----+-------------+-------+------------+------+---------------+------+---------+-----------------------+------+----------+-------------+
|  1 | SIMPLE      | book  | NULL       | ALL  | NULL          | NULL | NULL    | NULL                  |   20 |   100.00 | NULL        |
|  1 | SIMPLE      | class | NULL       | ref  | A             | A    | 4       | mysql_learn.book.card |    1 |   100.00 | Using index |
+----+-------------+-------+------------+------+---------------+------+---------+-----------------------+------+----------+-------------+
2 rows in set, 1 warning (0.00 sec)
DROP INDEX A ON class;
-- 注意表的加载顺序变了
-- 可以看到第二行的type变为了ref,rows也变成了优化比较明显。
-- 这是由左连接特性决定的。LEFTJOIN条件用于确定如何从右表搜索行,左边一定都有

-- 主表不管加不加索引都要全部查一遍


-- 可以对调表的顺序，但是要注意数据是否正确的问题
-- 假如DBA建好了索引（可能出于通用性的考虑，只在左边字段建了索引，这时不能轻易在右边字段增加索引）
-- 右连接就在左边建索引


-- 三表优化
-- https://www.bilibili.com/video/BV1KW411u7vy?p=33&spm_id_from=pageDriver
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


ALTER TABLE `phone` ADD INDEX z(`card`);



mysql> SELECT * FROM class INNER JOIN book ON class.card = book.card LEFT JOIN phone ON book.card = phone.card;
+----+------+--------+------+---------+------+
| id | card | bookid | card | phoneid | card |
+----+------+--------+------+---------+------+
|  3 |    9 |      5 |    9 |       3 |    9 |
|  1 |    2 |      3 |    2 |       5 |    2 |
| 12 |    2 |      3 |    2 |       5 |    2 |
|  1 |    2 |      3 |    2 |       9 |    2 |
| 12 |    2 |      3 |    2 |       9 |    2 |
| 13 |   10 |      1 |   10 |      14 |   10 |
|  6 |   12 |     12 |   12 |      19 |   12 |
| 15 |   12 |     12 |   12 |      19 |   12 |
| 17 |   12 |     12 |   12 |      19 |   12 |
| 13 |   10 |      1 |   10 |      20 |   10 |
|  5 |   11 |      2 |   11 |    NULL | NULL |
|  2 |   19 |      4 |   19 |    NULL | NULL |
| 11 |   19 |      4 |   19 |    NULL | NULL |
|  7 |    7 |      6 |    7 |    NULL | NULL |
|  4 |    8 |      7 |    8 |    NULL | NULL |
|  9 |    8 |      7 |    8 |    NULL | NULL |
| 10 |    5 |     10 |    5 |    NULL | NULL |
| 18 |    5 |     10 |    5 |    NULL | NULL |
| 10 |    5 |     11 |    5 |    NULL | NULL |
| 18 |    5 |     11 |    5 |    NULL | NULL |
|  7 |    7 |     16 |    7 |    NULL | NULL |
|  7 |    7 |     17 |    7 |    NULL | NULL |
+----+------+--------+------+---------+------+
22 rows in set (0.00 sec)

mysql> EXPLAIN SELECT * FROM class INNER JOIN book ON class.card = book.card LEFT JOIN phone ON book.card = phone.card;
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------------------------------------------+
| id | select_type | table | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra                                              |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------------------------------------------+
|  1 | SIMPLE      | class | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   20 |   100.00 | NULL                                               |
|  1 | SIMPLE      | book  | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   20 |    10.00 | Using where; Using join buffer (Block Nested Loop) |
|  1 | SIMPLE      | phone | NULL       | ALL  | NULL          | NULL | NULL    | NULL |   20 |   100.00 | Using where; Using join buffer (Block Nested Loop) |
+----+-------------+-------+------------+------+---------------+------+---------+------+------+----------+----------------------------------------------------+
3 rows in set, 1 warning (0.00 sec)



尽可能减少Join语句中的NestedLoop的循环总次数; 永远用小结果集驱动大的结果集

优先优化NestedLoop的内层循环;

当无法保证被驱动表的Join条件字段被索引且内存资源充足的前提下，不要太吝惜JoinBuffer的设置;
这个可以在配置文件中调整buffer的大小

ALTER TABLE`phone`ADD INDEXz ( 'card');
ALTER TABLE`book`ADD INDEX Y ( `card');#上一个case建过一个同样的
EXPLAIN SELECT* FROM class LEFTJOIN book ON class.card=book.card LEFTJOIN phone ON book.card = phone.card;
#后2行的 type 都是ref且总 rows优化很好,效果不错。因此索引最好设置在需要经常查询的字段w。
========EE=
【结论】
Join语句的优化
`




-- P34  索引失效
-- https://www.bilibili.com/video/BV1KW411u7vy?p=34&spm_id_from=pageDriver

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

ALTER TABLE staffs ADD INDEX idx_staffs_nameAgePos(name，age，pos);


-- 1.全值匹配： 如果索引了多列，要遵守最左前缀法则，指的是查询从索引的最左前列开始且不跳过索引中的列

-- 比如建了3个索引，查询只有一个索引条件
EXPLAIN SELECT * FROM staffs WHERE name = 'July';

EXPLAIN SELECT * FROM staffs WHERE age = 23 AND pos = 'dev ';
-- 第一个条件没用到，因为没有符合条件的数据

-- 2.最佳左前缀法则



-- 3.不在索引列上做任何操作（计算、函数、(自动或者手动)类型转换，会导致索引失效而转向全表扫描
-- https://www.bilibili.com/video/BV1KW411u7vy?p=35&spm_id_from=pageDriver
EXPLAIN SELECT * FROM staffs WHERE name = 'July' ;
EXPLAIN SELECT * from staffs WHERE left(name, 4) LIKE 'July' ;

-- 4.存储引擎不能使用索引中范围条件右边的列
-- https://www.bilibili.com/video/BV1KW411u7vy?p=36&spm_id_from=pageDriver
-- 范围之后全是小

-- 5.尽量使用覆盖索引(只访问索引的查询(索引列和查询列一致))，减少SELECT *



-- 6. mysql在使用不等于(!=或者<>)的时候无法使用索引会导致全表扫描
-- https://www.bilibili.com/video/BV1KW411u7vy?p=38&spm_id_from=pageDriver
-- 这个也属于范围查询
explain select t from staff WHERE name <> 'july';

-- 7. is null ,is not null也无法使用索引
-- https://www.bilibili.com/video/BV1KW411u7vy?p=39&spm_id_from=pageDriver


-- 8. like以通配符开头('%abc...' )mysql索引失效会变成全表扫描的操作
-- https://www.bilibili.com/video/BV1KW411u7vy?p=40&spm_id_from=pageDriver

-- 全表扫描
EXPLAIN SELECT * FROM staffs WHERE name LIKE '%July%';
-- 全扫描
EXPLAIN SELECT * FROM staffs WHERE name LIKE 'July%';
-- 全索引扫描
EXPLAIN SELECT * FROM staffs WHERE name LIKE '%July';

-- 解决like '%字符串%'时索引不被使用的方法?
-- 使用覆盖索引来解决
-- https://www.bilibili.com/video/BV1KW411u7vy?p=40&spm_id_from=pageDriver
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


-- 只查有索引的列
SELECT id FROM tal_user WHERE name LIKE '%aa%';


-- 9.字符串不加单引号索引失效
-- https://www.bilibili.com/video/BV1KW411u7vy?p=41&spm_id_from=pageDriver

-- 10.少用or,用它来连接时会索引失效




-- 优化sql的前提是保证业务逻辑正确





-- 回表


SELECT @@version;
DROP TABLE mysql_optimization.test ;
DROP TABLE mysql_optimization.student ;
CREATE TABLE `student` (
  `ID` int(11) AUTO_INCREMENT NOT NULL,
  `AGE` int(11) NOT NULL,
  `NAME` varchar(255) NOT NULL DEFAULT '',
  `ADDRESS` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `idx_age_name` (`AGE`, `NAME`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SELECT * FROM student 

INSERT INTO student VALUES (NULL, 18, 'A', '');
INSERT INTO student VALUES (NULL, 17, 'B', '');
INSERT INTO student VALUES (NULL, 18, 'C', '');
INSERT INTO student VALUES (NULL, 18, 'D', '');
INSERT INTO student VALUES (NULL, 18, 'E', '');
INSERT INTO student VALUES (NULL, 18, 'F', '');




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





-- 辅助索引
-- https://zhuanlan.zhihu.com/p/339666157

-- 只从辅助索引要数据。那么普通索引(单字段)和联合索引，以及唯一索引都能实现覆盖索引的作用。

-- 一个index就是一个B-Tree或B plus tree
-- https://www.cnblogs.com/happyflyingpig/p/7662881.html







-- P57
CREATE TABLE test_innodb_lock (
a INT(11),
b VARCHAR(16)
)ENGINE=INNODB;

























DROP DATABASE IF EXISTS db_mysql;
CREATE DATABASE IF NOT EXISTS db_mysql;
USE db_mysql;
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;
-- 表结构
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course`  (
  `cid` int(3) NOT NULL,
  `cname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tid` int(3) NULL DEFAULT NULL,
  PRIMARY KEY (`cid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher`  (
  `tid` int(3) NOT NULL,
  `tname` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `tcid` int(3) NULL DEFAULT NULL,
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

DROP TABLE IF EXISTS `teacher_card`;
CREATE TABLE `teacher_card`  (
  `tcid` int(3) NOT NULL,
  `tcdesc` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`tcid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
-- 数据
INSERT INTO course VALUES(1,'语文',1), (2,'数学',1),(3,'英语',2),(4,'物理',3);
INSERT INTO teacher VALUES(1,'刘老师',1), (2,'张老师',2), (3,'李老师',3);
INSERT INTO teacher_card VALUES(1,'张老师信息'), (2,'刘老师信息'), (3,'李老师信息');


SELECT tc.tcdesc FROM teacher_card tc 
WHERE tc.tcid = (SELECT t.tcid FROM teacher t WHERE t.tid = (SELECT c.tid FROM course c WHERE c.cname = '语文'))

EXPLAIN 
SELECT * FROM course c , teacher t WHERE c.cid = t.tid ;

EXPLAIN SELECT t.tcid FROM teacher t JOIN teacher_card tc ON t.tcid = tc.tcid;

SELECT * FROM teacher t ;
SELECT * FROM teacher_card tc ;

DELETE FROM teacher WHERE tid IN (4, 5);

INSERT INTO mysql_learn.teacher (tid, tname, tcid) VALUES(4, '张老师1', 2);
INSERT INTO mysql_learn.teacher (tid, tname, tcid) VALUES(5, '张老师1', 2);






-- MySQL 8.0版本 - 宋红康
-- https://www.bilibili.com/video/BV1iq4y1u7vj/?spm_id_from=333.788.b_765f64657363.1










