const menusListData = [
    {
        "id": 1,
        "title": "首页",
        "key": "/home",
        "pagepermisson": 1,
        "grade": 1,
        "children": []
    },
    {
        "id": 2,
        "title": "用户管理",
        "key": "/user-manage",
        "pagepermisson": 1,
        "grade": 1,
        "children": [
            {
                "id": 3,
                "title": "添加用户",
                "rightId": 2,
                "key": "/user-manage/add",
                "grade": 2
            }
        ]
    },
    {
        "id": 7,
        "title": "权限管理",
        "key": "/right-manage",
        "pagepermisson": 1,
        "grade": 1,
        "children": [
            {
                "id": 8,
                "title": "角色列表",
                "rightId": 7,
                "key": "/right-manage/role/list",
                "pagepermisson": 1,
                "grade": 2
            }
        ]
    },
    {
        "id": 14,
        "title": "新闻管理",
        "key": "/news-manage",
        "pagepermisson": 1,
        "grade": 1,
        "children": [
            {
                "id": 15,
                "title": "新闻列表",
                "rightId": 14,
                "key": "/news-manage/list",
                "grade": 2
            }
        ]
    },
    {
        "id": 21,
        "title": "审核管理",
        "key": "/audit-manage",
        "pagepermisson": 1,
        "grade": 1,
        "children": [
            {
                "id": 22,
                "title": "审核新闻",
                "rightId": 21,
                "key": "/audit-manage/audit",
                "pagepermisson": 1,
                "grade": 2
            }
        ]
    },
    {
        "id": 24,
        "title": "发布管理",
        "key": "/publish-manage",
        "pagepermisson": 1,
        "grade": 1,
        "children": [
            {
                "id": 25,
                "title": "待发布",
                "rightId": 24,
                "key": "/publish-manage/unpublished",
                "pagepermisson": 1,
                "grade": 2
            }
        ]
    }
]


export menusListData