/**
 * 头部容器左侧
 * @constructor
 */
///<reference path="../../../../../model/route.ts"/>
import { Breadcrumb } from 'antd'
import React, { createElement, useState, useEffect } from 'react'
import { useNavigate, useLocation } from 'react-router-dom'
import './index.less'
import { MenuFoldOutlined, MenuUnfoldOutlined } from '@ant-design/icons'

interface HeaderContainerLeftProps extends React.HTMLAttributes<HTMLElement> {
  toggle: Function
  breadcrumbData: ModelRoute.Breadcrumb
  collapsed: boolean
  breadCrumb: boolean
  isSmall: boolean
  navigationMode: number
}

const HeaderContainerLeft = (props: HeaderContainerLeftProps) => {
  const { toggle, breadcrumbData, collapsed, breadCrumb, isSmall, navigationMode } = props
  // 获取当前路径
  const { pathname } = useLocation()
  const navigate = useNavigate()
  const [layoutLeftClass, setLayoutLeftClass] = useState<string>(navigationMode === 1 ? 'layout_left' : 'layout_left layout_left_top')
  const breadcrumb = () => {
    if (breadcrumbData.getRoutes(pathname)) {
      return (
        <Breadcrumb className='breadcrumb'>
          {
            breadcrumbData.getRoutes(pathname).map((ele: ModelRoute.Route, idx: number) => {
              if (idx === 0 || breadcrumbData.getRoutes(pathname).length - 1 === idx) {
                return (
                  <Breadcrumb.Item key={ele.path}>{ele.label}</Breadcrumb.Item>
                )
              } else {
                return (
                  <Breadcrumb.Item key={ele.path}>
                    <a onClick={() => {
                      if (ele.path) {
                        navigate(ele.path)
                      }
                    }}>{ele.label}</a>
                  </Breadcrumb.Item>
                )
              }
            })
          }
        </Breadcrumb>
      )
    }
    }
      useEffect(() => {
        switch (navigationMode) {
          case 1:
            setLayoutLeftClass('layout_left')
            break
          case 2:
            setLayoutLeftClass('layout_left layout_left_top')
            break
        }
      }, [navigationMode])
      return (
        <div className={layoutLeftClass}>
          {isSmall && createElement(
            collapsed ? MenuUnfoldOutlined : MenuFoldOutlined,
            {
              className: 'trigger',
              onClick: () => {
                toggle()
              }
            }
          )}
          {
            !isSmall && breadCrumb &&
            breadcrumb()
          }
        </div>
      )
    }

    export default HeaderContainerLeft
