package sample.mybatis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    DROP TABLE IF EXISTS `students`;
    CREATE TABLE `students` (
        `id` bigint(20) NOT NULL AUTO_INCREMENT,
        `name` varchar(50) NOT NULL,
        `gender` tinyint(1) NOT NULL,
        `grade` int(11) NOT NULL,
        `score` int(11) NOT NULL,
        PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=60007 DEFAULT CHARSET=utf8;
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private String name;
    private Boolean gender;
    private Integer grade;
    private Integer score;
}
