mysql> SHOW INDEX FROM customers;
+-----------+------------+----------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| Table     | Non_unique | Key_name | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment |
+-----------+------------+----------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| customers |          0 | PRIMARY  |            1 | cust_id     | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
+-----------+------------+----------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
1 row in set (0.00 sec)

mysql> EXPLAIN SELECT cust_name FROM customers WHERE cust_zip = '42222';
+----+-------------+-----------+------------+------+---------------+------+---------+------+------+----------+-------------+
| id | select_type | table     | partitions | type | possible_keys | key  | key_len | ref  | rows | filtered | Extra       |
+----+-------------+-----------+------------+------+---------------+------+---------+------+------+----------+-------------+
|  1 | SIMPLE      | customers | NULL       | ALL  | NULL          | NULL | NULL    | NULL |    5 |    20.00 | Using where |
+----+-------------+-----------+------------+------+---------------+------+---------+------+------+----------+-------------+
1 row in set, 1 warning (0.00 sec)


ALTER TABLE customers ADD INDEX idx_zip_name(cust_zip, cust_name);
DROP INDEX idx_zip_name ON customers;


mysql> SHOW INDEX FROM customers;
+-----------+------------+--------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| Table     | Non_unique | Key_name     | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment |
+-----------+------------+--------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| customers |          0 | PRIMARY      |            1 | cust_id     | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
| customers |          1 | idx_zip_name |            1 | cust_zip    | A         |           5 |     NULL | NULL   | YES  | BTREE      |         |               |
| customers |          1 | idx_zip_name |            2 | cust_name   | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
+-----------+------------+--------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
3 rows in set (0.00 sec)


mysql> EXPLAIN SELECT cust_name FROM customers WHERE cust_zip = '42222';
+----+-------------+-----------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+
| id | select_type | table     | partitions | type | possible_keys | key          | key_len | ref   | rows | filtered | Extra       |
+----+-------------+-----------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+
|  1 | SIMPLE      | customers | NULL       | ref  | idx_zip_name  | idx_zip_name | 41      | const |    1 |   100.00 | Using index |
+----+-------------+-----------+------------+------+---------------+--------------+---------+-------+------+----------+-------------+
1 row in set, 1 warning (0.00 sec)

-- 可以看到Extra中没有Using index， 覆盖索引失效
mysql> EXPLAIN SELECT * FROM customers WHERE cust_zip = '42222';
+----+-------------+-----------+------------+------+---------------+--------------+---------+-------+------+----------+-------+
| id | select_type | table     | partitions | type | possible_keys | key          | key_len | ref   | rows | filtered | Extra |
+----+-------------+-----------+------------+------+---------------+--------------+---------+-------+------+----------+-------+
|  1 | SIMPLE      | customers | NULL       | ref  | idx_zip_name  | idx_zip_name | 41      | const |    1 |   100.00 | NULL  |
+----+-------------+-----------+------------+------+---------------+--------------+---------+-------+------+----------+-------+
1 row in set, 1 warning (0.00 sec)

-- 覆盖索引避免了回表现象的产生，从而减少树的搜索次数，显著提升查询性能，所以使用覆盖索引是性能优化的一种手段


-- 那如果反过来搜索
mysql> EXPLAIN SELECT cust_zip FROM customers WHERE cust_name = 'Taylor';
+----+-------------+-----------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
| id | select_type | table     | partitions | type  | possible_keys | key          | key_len | ref  | rows | filtered | Extra                    |
+----+-------------+-----------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
|  1 | SIMPLE      | customers | NULL       | index | NULL          | idx_zip_name | 241     | NULL |    6 |    16.67 | Using where; Using index |
+----+-------------+-----------+------------+-------+---------------+--------------+---------+------+------+----------+--------------------------+
1 row in set, 1 warning (0.00 sec)


-- 并不符合左前缀匹配原则


-- 新增一条数据
INSERT INTO mysql_learn.customers
(cust_id, cust_name, cust_address, cust_city, cust_state, cust_zip, cust_country, cust_contact, cust_email)
VALUES(10006, 'Taylor', '4545 54rd Street', 'Texas', 'IL', '42222', 'USA', 'Taylor', NULL);




-- join using  和join on 的区别 (总结 join using可以用来简化 join on ,用join on 可以替代 join using)
-- join using 后面接 两张表中都存在的字段 (字段名称 一样)
-- join on    后面接 两张表中中需要关联的字段 (字段名称不需要一样 a.id = b.id )
-- SELECT * FROM a JOIN b ON (a.id = b.id)
-- SELECT * FROM a JOIN b USING (id)


-- 建索引之前
mysql> EXPLAIN
    -> SELECT * FROM orders o
    -> INNER JOIN (
    -> SELECT order_num FROM orders ORDER BY order_num DESC LIMIT 0, 10
    -> ) AS page USING(order_num)
    -> ORDER BY order_date DESC;
+----+-------------+------------+------------+--------+---------------+---------+---------+----------------+------+----------+---------------------------------+
| id | select_type | table      | partitions | type   | possible_keys | key     | key_len | ref            | rows | filtered | Extra                           |
+----+-------------+------------+------------+--------+---------------+---------+---------+----------------+------+----------+---------------------------------+
|  1 | PRIMARY     | <derived2> | NULL       | ALL    | NULL          | NULL    | NULL    | NULL           |    5 |   100.00 | Using temporary; Using filesort |
|  1 | PRIMARY     | o          | NULL       | eq_ref | PRIMARY       | PRIMARY | 4       | page.order_num |    1 |   100.00 | NULL                            |
|  2 | DERIVED     | orders     | NULL       | index  | NULL          | PRIMARY | 4       | NULL           |    5 |   100.00 | Using index                     |
+----+-------------+------------+------------+--------+---------------+---------+---------+----------------+------+----------+---------------------------------+
3 rows in set, 1 warning (0.01 sec)


ALTER TABLE orders ADD INDEX idx_date_num(order_date, order_num);
DROP INDEX idx_date_num ON orders;

-- 建索引之后

mysql> SHOW INDEX FROM orders;
+--------+------------+---------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| Table  | Non_unique | Key_name            | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment |
+--------+------------+---------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
| orders |          0 | PRIMARY             |            1 | order_num   | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
| orders |          1 | fk_orders_customers |            1 | cust_id     | A         |           4 |     NULL | NULL   |      | BTREE      |         |               |
| orders |          1 | idx_date_num        |            1 | order_date  | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
| orders |          1 | idx_date_num        |            2 | order_num   | A         |           5 |     NULL | NULL   |      | BTREE      |         |               |
+--------+------------+---------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+
4 rows in set (0.00 sec)


mysql> EXPLAIN
    -> SELECT * FROM orders o
    -> INNER JOIN (
    -> SELECT order_num FROM orders ORDER BY order_num DESC LIMIT 0, 10
    -> ) AS page USING(order_num)
    -> ORDER BY order_date DESC;
+----+-------------+------------+------------+--------+---------------+---------+---------+----------------+------+----------+---------------------------------+
| id | select_type | table      | partitions | type   | possible_keys | key     | key_len | ref            | rows | filtered | Extra                           |
+----+-------------+------------+------------+--------+---------------+---------+---------+----------------+------+----------+---------------------------------+
|  1 | PRIMARY     | <derived2> | NULL       | ALL    | NULL          | NULL    | NULL    | NULL           |    5 |   100.00 | Using temporary; Using filesort |
|  1 | PRIMARY     | o          | NULL       | eq_ref | PRIMARY       | PRIMARY | 4       | page.order_num |    1 |   100.00 | NULL                            |
|  2 | DERIVED     | orders     | NULL       | index  | NULL          | PRIMARY | 4       | NULL           |    5 |   100.00 | Using index                     |
+----+-------------+------------+------------+--------+---------------+---------+---------+----------------+------+----------+---------------------------------+
3 rows in set, 1 warning (0.00 sec)

