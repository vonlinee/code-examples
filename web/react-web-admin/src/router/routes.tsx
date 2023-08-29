///<reference path='../model/route.ts' />
import Login from '@/views/login'
import { Navigate, useRoutes } from 'react-router-dom'
import lazyLoad from '@/router/utils/lazyLoad'
import React from 'react'
import { store } from '@/store'
// 导入所有router
const metaRouters = import.meta.globEager('./modules/*.tsx')

/**
 * 加载 router/modules 目录下的所有路由配置
 * 处理路由
 */
export const routerArray: ModelRoute.Route[] = []
Object.keys(metaRouters).forEach((item: string) => {
  Object.keys(metaRouters[item]).forEach((key: any) => {
    routerArray.push(...metaRouters[item][key])
  })
})

// 获取token
const token = store.getState().user.token
console.log('token', token)
const storeRoute = store.getState().route.routeList || []
console.log("storeRoute", storeRoute)

/**
 * 基本路由配置
 * @param {string} path 路径
 * @param {string} element 组件的页面
 * @param {string} name 菜单名
 * @param {Route} children 子页面
 * @param {boolean} sidebar 菜单栏
 */
export let rootRouter: ModelRoute.Route[] = [
  {
    path: '/login',
    element: lazyLoad(
      React.lazy(() => import('@/views/login'))
    ),
    meta: {
      title: '登录页'
    }
  },
  {
    path: '/',
    element: <Navigate to='/login' />
  }
]

/**
 * 根据token添加登录路由
 */
if (token) {
  rootRouter = [
    {
      path: '/login',
      element: <Login />,
      meta: {
        title: '登录页'
      }
    },
    {
      path: '/',
      element: <Navigate to='/login' />
    }
  ]
  // console.log('有token,storeRoute', storeRoute)
  // console.log('有token,routerArray', routerArray)

  if (storeRoute.length) {
    rootRouter.push(...storeRoute)
  } else {
    rootRouter.push(...routerArray)
  }
}

/**
 * 错误路由配置
 * @param {string} path 路径
 * @param {string} element 组件的页面
 * @param {string} name 菜单名
 * @param {Route} children 子页面
 * @param {boolean} sidebar 菜单栏
 */
export const errorRouter: ModelRoute.Route[] = [
  {
    path: '/404',
    element: lazyLoad(
      React.lazy(() => import('@/views/404/index'))
    ),
    meta: {
      title: '404页面'
    }
  },
  {
    path: '*',
    element: <Navigate to='/404' />
  }
]

const Router = () => {
  return useRoutes(rootRouter)
}

export default Router
