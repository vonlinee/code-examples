/**
 * 模块路由
 */
declare namespace ModelRoute {
  interface MetaProps {
    keepAlive?: boolean //状态保持
    title: string //页面标题
  }

  /**
   * 路由项
   * @see React.RouteObject
   */
  interface Route {
    path?: string //路径
    element?: React.ReactNode
    children?: Route[] //子路由
    sidebar?: boolean //菜单栏
    meta?: MetaProps
    icon?: string //图标
    /**
     * 标签页的名称
     */
    label?: React.ReactNode
    key?: React.Key | null
    isPage?: boolean //是否页面
    menuName?: string // 菜单名称
    routePath?: string // 路由路径
  }

  /**
   * 适配antd菜单项
   * https://ant.design/components/menu-cn#itemtype
   * @see MenuItemType | SubMenuType | MenuItemGroupType | MenuDividerType | null;
   */
  interface Menu {
    path?: string //路径
    /**
     * 菜单项标题
     */
    label: React.ReactNode
    key: React.Key
    /**
     * 菜单图标
     */
    icon?: React.ReactNode
    children?: Menu[]
  }

  /**
   * 面包屑
   * https://ant.design/components/breadcrumb-cn
   */
  interface Breadcrumb {
    // 显示文本
    label?: React.ReactNode
    // 路由路径
    path?: string
    key?: React.Key
    // 图标
    icon?: string,

    [propname: string]: any
  }

  /**
   * 面包屑的每项
   */
  interface BreadcrumbItem {
    // 展示名称
    label: string,
    // 跳转路由路径
    path: string
  }

  interface TabsList {
    label: React.ReactNode
    title?: React.ReactNode
    path?: string
    close?: boolean
    icon?: string
    key: string
  }

  interface MenuBtn {
    menuId: number //按钮id
    menuName: string //按钮名
    routePath: string //匹配地址
  }
}


