import { Menu, MenuProps } from "antd";
import { useLocation } from "react-router-dom";
import { Scrollbars } from "react-custom-scrollbars";
import "./index.less";

type SideMenuItem = Required<MenuProps>['items'][number];

/**
 * 用来根据配置信息筛选可以显示的菜单项
 * @param item
 * @returns
 */
const filterMenuItem = (item: MenuItem) => {
  return item != null;
};

interface MenuContainerProps extends React.HTMLAttributes<HTMLElement> {
  collapsed: boolean;
  isSmall: boolean;
  openKeys: string[];
  mode: "vertical" | "inline" | "horizontal";
}

const MenuContainer = (props: MenuContainerProps) => {
  // 得到当前请求的路由路径
  const path: string = useLocation().pathname;

  const {collapsed, isSmall} = props;

  console.log(collapsed, isSmall);

  const menuTreeNode: SideMenuItem[] = [];

  return (
    <div className="sidebar-menu-container">
      <Scrollbars autoHide autoHideTimeout={1000} autoHideDuration={200}>
        <Menu mode="inline" theme="dark" selectedKeys={[path]} items={menuTreeNode}>
        </Menu>
      </Scrollbars>
    </div>
  );
};

export default MenuContainer;
