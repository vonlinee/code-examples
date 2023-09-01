///<reference path='../../model/route.ts' />
import React from 'react'
import lazyLoad from '@/router/utils/lazyLoad'
import Layouts from '@/layouts/index'

//home页面 路由
const TestRouter: ModelRoute.Route[] = [
  {
    element: <Layouts />,
    meta: {
      title: '测试'
    },
    path: '/test/index',
    sidebar: true,
    isPage: false,
    icon: 'HomeOutlined',
    children: [

    ]
  }
]

export default TestRouter
