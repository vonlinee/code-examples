//动态路由数据,模拟后端接口
import React from 'react'

const trees: ModelRoute.Route[] = [
  {
    menuName: '主页',
    routePath: '/home',
    children: [
      {
        menuName: '首页',
        routePath: '/home/index',
        children: [
          {
            menuName: '首页2',
            routePath: '/home/index/details'
          }
        ]
      }
    ]
  },
  {
    menuName: '系统管理',
    routePath: '/system',
    children: [
      {
        menuName: '部门管理',
        routePath: '/system/department'
      },
      {
        menuName: '菜单管理',
        routePath: '/system/menu'
      },
      {
        menuName: '角色管理',
        routePath: '/system/role'
      },
      {
        menuName: '用户管理',
        routePath: '/system/user'
      }
    ]
  },
  {
    menuName: '开发工具',
    routePath: '/devpl',
    children: [
      {
        menuName: '代码生成',
        routePath: '/devpl/codegen',
        children: []
      },
      {
        menuName: '模板管理',
        routePath: '/devpl/codegen/template',
        children: []
      }
    ]
  },
  {
    menuName: '测试',
    routePath: '/test',
    children: [

    ]
  }
]

export default trees
