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
        label: '代码生成',
        path: '/devpl/index',
        element: lazyLoad(
          React.lazy(
            () => import('@/views/devpl/index')
          )
        ),
        meta: {
          title: '代码生成'
        },
        sidebar: true,
        isPage: true
      },
    ]
  }
]

export default devplRouter
