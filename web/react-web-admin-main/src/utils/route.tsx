///<reference path="../model/route.ts"/>
import { routerArray } from '@/router/routes'
import * as Icons from '@ant-design/icons'
import { BreadcrumbProps } from 'antd'
import React from 'react'
// 动态渲染 Icon 图标
const customIcons: { [key: string]: any } = Icons
const addIcon = (name: string) => {
  return React.createElement(customIcons[name])
}

/**
 * 获取菜单列表并处理成 antd menu 需要的格式
 * @see https://ant.design/components/menu-cn
 * 生成菜单栏数据
 * 将前端配置的路由和后端配置的路由进行结合
 * @param asyncRouterMap 后端返回的路由数据
 * @param localRoutetMap 本地的动态路由数据，前端配置的路由
 * @returns 菜单路由数据
 */
const filterAsyncSidebar = (asyncRouterMap: ModelRoute.Route[], localRoutetMap: ModelRoute.Route[]): ModelRoute.Menu[] => {
  let routes: ModelRoute.Menu[] = []

  localRoutetMap.forEach((localItem: ModelRoute.Route) => {
    asyncRouterMap.forEach((asyncItem: ModelRoute.Route) => {
      if (localItem.path === asyncItem.routePath) {
        if (localItem.sidebar) {

          let childrenMenus: ModelRoute.Menu[] = []
          if (asyncItem.children && asyncItem.children.length && localItem.children) {
            childrenMenus = filterAsyncSidebar(asyncItem.children, localItem.children)
          }

          /**
           * TODO
           */
          const route: ModelRoute.Menu = {
            path: localItem.path,
            label: asyncItem.menuName,
            key: asyncItem.routePath ? asyncItem.routePath : 'unknown',
            icon: localItem.icon ? addIcon(localItem.icon) : addIcon('AppstoreOutlined'),
            children: childrenMenus
          }
          if (route.children && !route.children.length) {
            delete route.children
          }
          routes.push(route)
        }
      }
    })
  })
  return routes
}

/**
 * 生成路由数据
 * @param asyncRouterMap 后端返回的路由数据
 * @param localRoutetMap 本地的动态路由数据
 * @returns
 */
const filterAsyncRoute = (asyncRouterMap: ModelRoute.Route[], localRoutetMap: ModelRoute.Route[]) => {
  let routes: ModelRoute.Route[] = []
  localRoutetMap.map((localItem) => {
    asyncRouterMap.map((asyncItem) => {
      if (localItem.path === asyncItem.routePath) {
        const route: ModelRoute.Route = {
          routePath: '',
          element: localItem.element,
          path: localItem.path,
          label: asyncItem.menuName,
          key: asyncItem.routePath,
          meta: localItem.meta,
          children: asyncItem.children && asyncItem.children.length ? filterAsyncRoute(asyncItem.children, localItem.children ? localItem.children : []) : [],
          isPage: localItem.isPage || false
        }
        if (route.children && !route.children.length) {
          delete route.children
        }
        routes.push(route)
      }
    })
  })
  return routes
}

/**
 * 生成面包屑数据
 * 递归
 * @see https://ant.design/components/breadcrumb-cn
 * @param localRoutetMap
 * @param route
 */
const filterAsyncBreadcrumb = (localRoutetMap: ModelRoute.Route[], route: ModelRoute.Route[]): ModelRoute.Breadcrumb => {
  let routes: ModelRoute.Breadcrumb = {}

  localRoutetMap.map((ele: ModelRoute.Route) => {
    if (ele.path && ele.meta) {
      // @ts-ignore
      routes[ele.path] = [
        ...route,
        {
          label: ele.meta.title,
          path: ele.path
        }
      ]
    }

    if (ele.children && ele.children.length) {
      routes = {
        ...routes,
        ...filterAsyncBreadcrumb(ele.children, routes[ele.path]
        )
      }
    }
  })
  return routes
}

export {
  filterAsyncSidebar,
  filterAsyncRoute,
  filterAsyncBreadcrumb
}
