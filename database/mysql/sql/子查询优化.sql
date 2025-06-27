

https://dev.mysql.com/doc/refman/5.7/en/semijoins.html

MySQL查询优化器有不同的策略可用于评估子查询

SELECT class.class_num, class.class_name
FROM class INNER JOIN roster
WHERE class.class_num = roster.class_num;

INNER JOIN可以去重

同样的SELECT DISTINCT也可以用于去重

SELECT class_num, class_name
FROM class
WHERE class_num IN (SELECT class_num FROM roster);

查询使用semijoin,只返回roster表中匹配class中的一行，不用多余的去重操作


MySQL中，子查询要想以semijoin的方式进行处理，需要满足一定的条件

也就是说mysql优化器只会针对semijoin进行优化

1.必须是IN子查询，同时必须是在WHERE或者ON后面，或者在AND表达式后面

mysql> EXPLAIN SELECT * FROM orders o WHERE o.order_num IN (SELECT order_num FROM orderitems oi);
+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+----------------------------------------------------+
| id | select_type | table | partitions | type  | possible_keys | key     | key_len | ref  | rows | filtered | Extra                                              |
+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+----------------------------------------------------+
|  1 | SIMPLE      | oi    | NULL       | index | PRIMARY       | PRIMARY | 8       | NULL |   11 |    45.45 | Using index; LooseScan                             |
|  1 | SIMPLE      | o     | NULL       | ALL   | PRIMARY       | NULL    | NULL    | NULL |    5 |    20.00 | Using where; Using join buffer (Block Nested Loop) |
+----+-------------+-------+------------+-------+---------------+---------+---------+------+------+----------+----------------------------------------------------+
2 rows in set (0.06 sec)


FirstMatch优化，这也是在处理半连接子查询时可能会用到的一种优化策略

mysql> EXPLAIN 
SELECT * FROM orders o 
LEFT JOIN orderitems o2 ON o.order_num IN (SELECT order_num FROM orderitems);
+----+-------------+------------+------------+-------+---------------+---------------------+---------+-------------------------+------+----------+-----------------------------------------+
| id | select_type | table      | partitions | type  | possible_keys | key                 | key_len | ref                     | rows | filtered | Extra                                   |
+----+-------------+------------+------------+-------+---------------+---------------------+---------+-------------------------+------+----------+-----------------------------------------+
|  1 | SIMPLE      | o          | NULL       | ALL   | NULL          | NULL                | NULL    | NULL                    |    5 |   100.00 | NULL                                    |
|  1 | SIMPLE      | orderitems | NULL       | ref   | PRIMARY       | PRIMARY             | 4       | mysql_learn.o.order_num |    2 |   100.00 | Using where; Using index; FirstMatch(o) |
|  1 | SIMPLE      | o2         | NULL       | index | NULL          | idx_prod_quan_price | 38      | NULL                    |   11 |   100.00 | Using index                             |
+----+-------------+------------+------------+-------+---------------+---------------------+---------+-------------------------+------+----------+-----------------------------------------+
3 rows in set (0.03 sec)









