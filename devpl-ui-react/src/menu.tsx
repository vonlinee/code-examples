import React from "react";
import { menuList } from './config'
import { MenuProps } from "antd";
import { ItemType } from "antd/es/menu/hooks/useItems";
import Menu from "antd/es/menu/menu";

type MenuItem = Required<MenuProps>['items'][number];// menuItem类型
// antd方法，用于返回menuItem需要的字段
function getItem(label: React.ReactNode, key: React.Key, icon?: React.ReactNode, children?: MenuItem[], type?: 'group'): MenuItem {
    return {
        key,
        icon,
        children,
        label,
        type,
    } as MenuItem;
}
interface item {
    key: string,
    icon: string,
    children: item[],
    label: string,
    type: string,
}
function getMenuList() {
    let tempMenuList: ItemType[] = [];
    let openKeys: string[] = [];
    const getList = (list: any, newList: MenuItem[]) => {
        for (let i = 0; i < list.length; i++) {
            const { value, label, icon } = list[i] || {};
            const it = getItem(label, value || label);
            newList.push(it)
            routeMap[value] = label
            if (tempBo) {
                const tempItem = newList[i] as item
                tempItem.children = [];
                getList(list[i].children || [], tempItem.children);
            }
        }
    }
    getList(menuList, tempMenuList)
    return { tempMenuList }
}

const MyMenu: React.FC<{}> = (props) => {
    const { tempMenuList } = getMenuList()
    return (
        <Menu
            style={{ width: 256, height: '100%' }}
            mode="inline"
            items={tempMenuList}
        ></Menu>
    )
}

export default MyMenu
