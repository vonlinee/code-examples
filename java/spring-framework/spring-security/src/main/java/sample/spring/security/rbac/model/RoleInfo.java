package sample.spring.security.rbac.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoleInfo {

	private String roleId;
	private String roleCode;
	private String roleName;
	private String description;
	private String createBy;
	private LocalDateTime createdTime;
	private String updateBy;
	private LocalDateTime updateTime;
}
