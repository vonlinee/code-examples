package sample.spring.boot.h2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "t_department") // 控制生成的表名
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DEPARTMENT_ID")
	private Long departmentId;
	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;
	@Column(name = "DEPARTMENT_ADDRESS")
	private String departmentAddress;
	@Column(name = "DEPARTMENT_CODE")
	private String departmentCode;
}
