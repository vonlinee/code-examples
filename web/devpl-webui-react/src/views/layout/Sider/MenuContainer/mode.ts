/**
 * 路由属性
 */
export interface RouteMetaProps {
  keepAlive?: boolean; //状态保持
  title: string; //页面标题
}

/**
 * 路由项
 * @see React.RouteObject
 */
export interface RouteItem {
  path?: string; //路径
  element?: React.ReactNode;
  children?: RouteItem[]; //子路由
  meta?: RouteMetaProps;
  icon?: string; //图标
}

/**
 * 适配antd菜单项
 * type MenuItem = Required<MenuProps>["items"][number];
 * https://ant.design/components/menu-cn#itemtype
 * @see MenuItemType | SubMenuType | MenuItemGroupType | MenuDividerType | null;
 */
export interface MenuItem {
  /**
   * 路径
   */
  path?: string;
  /**
   * 菜单项标题
   */
  label: React.ReactNode;
  /**
   * 菜单唯一Key
   */
  key: React.Key;
  /**
   * 菜单图标
   */
  icon?: React.ReactNode;
  /**
   * 子菜单项
   */
  children?: MenuItem[];
  /**
   * 类型
   */
  type?: 'group'
}
