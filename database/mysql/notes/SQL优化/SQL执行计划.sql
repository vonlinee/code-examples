EXPLAIN 
SELECT * FROM orders o
	LEFT JOIN orderitems o2 ON o.order_num = o2.order_num 
WHERE 1 = 1
	AND o.cust_id = (
		SELECT cust_id FROM customers c WHERE c.cust_name IN (''))
	AND DATE_FORMAT(o.order_date ,'%Y-%m-%d %H:%i:%s') > '2022-03-07 16:25:33'


EXPLAIN 
SELECT cust_id 
FROM orders o
	LEFT JOIN orderitems o2 ON o.order_num = o2.order_num 
ORDER BY o2.order_num 

-- SHOW INDEX FROM orders

EXPLAIN 
SELECT * FROM orders o 
WHERE o.cust_id IN (SELECT cust_id FROM customers c WHERE c.cust_country = 'USA')

EXPLAIN 
SELECT * FROM orders o
	LEFT JOIN orderitems o2 ON o.order_num = o2.order_num 
WHERE o.order_num > 100

EXPLAIN 
SELECT
	a.prod_id, b.prod_name, b.prod_price
FROM (
	SELECT DISTINCT oi.prod_id FROM orderitems oi WHERE oi.item_price > 10
) a
JOIN (SELECT * FROM products) b
ON a.prod_id = b.prod_id

EXPLAIN 
SELECT
	a.prod_id, b.prod_name, b.prod_price
FROM (
	SELECT DISTINCT oi.prod_id FROM orderitems oi WHERE oi.item_price > 10
) a
LEFT JOIN (SELECT * FROM products) b
ON a.prod_id = b.prod_id



















CREATE TABLE t1 (
	`column1` varchar(10),
	`column2` varchar(10),
	`column3` varchar(10)
)

CREATE TABLE t2 (
	`column1` varchar(10),
	`column2` varchar(10),
	`column3` varchar(10)
)

SHOW INDEX FROM t1;
SHOW INDEX FROM t2;

-- 派生表优化
EXPLAIN
SELECT *
FROM (SELECT DISTINCT column1 FROM t1 WHERE column3 = '1') A 
JOIN (SELECT * FROM t2) B
ON A.column1 = B.column1

EXPLAIN
SELECT *
FROM (SELECT DISTINCT column1 FROM t1 WHERE column3 = '1') A 
JOIN (SELECT * FROM t2) B
ON A.column1 = B.column1

EXPLAIN
SELECT *
FROM (SELECT column1 FROM t1 WHERE column3 = '1') A 
JOIN (SELECT * FROM t2) B
ON A.column1 = B.column1

-- 加索引

-- https://www.cnblogs.com/wqbin/p/12127711.html

ALTER TABLE t1 ADD UNIQUE INDEX ()







