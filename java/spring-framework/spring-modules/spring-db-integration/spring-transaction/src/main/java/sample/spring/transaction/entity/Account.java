package sample.spring.transaction.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
CREATE TABLE `t_account` (
  `id` int(3) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `money` float DEFAULT '0',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
INSERT INTO db_mysql.t_account (id, name, money) VALUES(1, 'zs', 10000.0);
INSERT INTO db_mysql.t_account (id, name, money) VALUES(2, 'ls', 2000.0);
 */
@Entity
@Getter
@Setter
@Table(name = "t_account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private Integer name;

	@Column(name = "money")
	private BigDecimal money;
}
