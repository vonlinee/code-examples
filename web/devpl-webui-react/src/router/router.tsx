import Index from '@/views/pages/index'
import Demo from '@/views/pages/demo'
import { HeartOutlined } from '@ant-design/icons'

export interface RouteItem {
    path: string,
    element?: React.ReactNode,
    icon: React.ReactNode,
    label: string,
    children?: RouteItem[]
}

const routes: RouteItem[] = [
  {
    path: '/',
    element: <Index />,
    icon: <HeartOutlined />,
    label: '首页',
  },
  {
    path: '/demo',
    element: <Demo />,
    icon: <HeartOutlined />,
    label: '朋友',
  },
  {
    path: '/about',
    label: '关于',
    icon: <HeartOutlined />,
    children: [
      {
        path: 'music',
        element: <Demo />,
        label: '音乐',
        icon: <HeartOutlined />,
      },
      {
        path: 'movie',
        element: <Demo />,
        label: '电影',
        icon: <HeartOutlined />,
      },
    ],
  },
  {
    path: '/my',
    label: '我的',
    icon: <HeartOutlined />,
    children: [
      {
        path: 'money',
        label: '钱包',
        icon: <HeartOutlined />,
        children: [
          {
            path: 'yue',
            element: <Demo />,
            label: '余额',
            icon: <HeartOutlined />,
          },
        ],
      },
      {
        path: 'message',
        element: <Demo />,
        label: '信息',
        icon: <HeartOutlined />,
      },
    ],
  },
]
 
export default routes