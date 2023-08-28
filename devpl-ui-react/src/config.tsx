export const menuList = [
    {
        value: '/home',
        label: '首页',
    },
    {
        value: '/form',
        label: '表单系列',
        children: [
            {
                value: '/form/loopForm',
                label: '循环表单及校验'
            }
        ]
    },
    {
        value: '/components',
        label: '封装组件',
        children: [
            {
                value: '/components/inherit',
                label: '二次封装组件功能透传'
            },
            {
                value: '/components/table',
                label: 'table组件',
            }
        ]
    },
    {
        value: '/skill',
        label: '奇技淫巧',
    }
]
