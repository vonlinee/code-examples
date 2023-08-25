///<reference path='../../model/route.ts' />
import React from 'react'
import lazyLoad from '@/router/utils/lazyLoad'
import Layouts from '@/layouts/index'

//home页面 路由
const devplRouter: ModelRoute.Route[] = [
  {
    element: <Layouts />,
    meta: {
      title: '开发工具'
    },
    path: '/devpl',
    sidebar: true,
    isPage: false,
    icon: 'HomeOutlined',
    children: [
      {
        path: '/devpl/department',
        element: lazyLoad(
          React.lazy(
            () => import('@/views/system/department/index')
          )
        ),
        meta: {
          title: '部门管理'
        },
        sidebar: true,
        isPage: true
      },
    ]
  }
]

export default devplRouter
