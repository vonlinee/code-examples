import { Layout, Menu } from 'antd'
import type { MenuProps } from 'antd';
// 引入路由规则文件
import routes from '@/router/router.js'
import { Link, useRoutes } from 'react-router-dom'
import { RouteItem } from './mode';

function createMenuForRoutes(routes: RouteItem[]) {

}

export default function App() {
  const element = useRoutes(routes)
  // Menu组件通过指定items属性，进行菜单渲染
  const items: MenuProps['items'] = []
  // 对路由规则数组进行遍历，并对其进行改造，改造成与Menu的items属性相同的结构
  routes.forEach((item) => {
    items.push({
      label: <Link to={item.path}>{item.label}</Link>,
      key: item.path,
      icon: item.icon,
      children:
        item.children && item.children.map((child) => {
          return {
            label: <Link to={item.path + '/' + child.path}>{child.label}</Link>,
            key: item.path + '/' + child.path,
            icon: child.icon,
            children:
              child.children &&
              child.children.map((sun) => {
                return {
                  label: (
                    <Link to={item.path + '/' + child.path + '/' + sun.path}>
                      {sun.label}
                    </Link>
                  ),
                  key: item.path + '/' + child.path + '/' + sun.path,
                  icon: sun.icon,
                }
              }),
          }
        }),
    })
  })

  const onClick: MenuProps['onClick'] = (e) => {
    console.log('click ', e);
  };

  return (
    <div className='sidebar-menu-container'>
      <Menu theme="dark" mode="inline" items={items} onClick={onClick}></Menu>
      <div>
      <div>{element}</div>
      </div>
    </div>
  )
}