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
        path: '/devpl/codegen',
        element: lazyLoad(
          React.lazy(
            () => import('@/views/devpl/codegen/index')
          )
        ),
        meta: {
          title: '代码生成'
        },
        sidebar: true,
        isPage: true
      },
      {
        label: '模板管理',
        path: '/devpl/codegen/template',
        element: lazyLoad(
          React.lazy(
            () => import('@/views/devpl/codegen/template/index')
          )
        ),
        meta: {
          title: '模板管理'
        },
        sidebar: true,
        isPage: true
      },
    ]
  }
]

export default devplRouter
