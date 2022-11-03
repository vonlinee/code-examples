package sample.spring.security.rbac.model;

import lombok.Data;

/**
 * 菜单信息表
 */
@Data
public class MenuInfo {
    private int menuId;
    private String menuName;
    private String moduleName;
}
