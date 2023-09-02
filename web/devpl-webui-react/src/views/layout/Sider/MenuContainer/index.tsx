import { Menu, MenuProps } from "antd";
import { useLocation } from "react-router-dom";
import "./index.less";
import React from "react";
import MenuItem from "antd/es/menu/MenuItem";
import SubMenu from "antd/es/menu/SubMenu";

interface MenuContainerProps extends React.HTMLAttributes<HTMLElement> {
  collapsed: boolean;
  isSmall: boolean;
  openKeys: string[];
  mode: "vertical" | "inline" | "horizontal";
}

interface SideMenuItem {
  title: string,
  path: string,
  icon: React.Key,
  children?: SideMenuItem[]
}

const menuList: SideMenuItem[] = [
  {
    title: "首页",
    path: "/dashboard",
    icon: "home",
  },
  {
    title: "作者博客",
    path: "/doc",
    icon: "file",
  }
];

console.log(menuList)

type MenuItem = Required<MenuProps>['items'][number];

const MenuContainer = (props: MenuContainerProps) => {
  // 得到当前请求的路由路径
  const path: string = useLocation().pathname;

  const {collapsed, isSmall} = props;

  console.log(collapsed, isSmall);
  

  return (
    <div className="sidebar-menu-container">
        <Menu mode="inline" theme="dark" selectedKeys={[path]}>
          <MenuItem title="系统管理"></MenuItem>
        </Menu>
        <Menu>
          <SubMenu title="菜单管理"></SubMenu>
        </Menu>
    </div>
  );
};

export default MenuContainer;
