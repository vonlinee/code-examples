import { Outlet } from 'react-router-dom'
import matchRoute from '@/utils/match'
import React from 'react'

interface LevelLayoutProps extends React.HTMLAttributes<HTMLElement> {
  pathName: string
  children: any
}

/**
 * 多层级路由容器
 * @returns {HTMLElement}   页面
 * @param props
 */
const LevelLayout = (props: LevelLayoutProps) => {
  const { pathName, children } = props
  return (
    <div>
      {/*匹配二级路由*/}
      {matchRoute(pathName) && children}
      {/*占位符，匹配三级路由*/}
      <Outlet></Outlet>
    </div>
  )
}

export default LevelLayout
