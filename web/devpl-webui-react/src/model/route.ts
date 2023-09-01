

/**
 * 适配antd菜单项
 * https://ant.design/components/menu-cn#itemtype
 * @see MenuItemType | SubMenuType | MenuItemGroupType | MenuDividerType | null;
 */
interface MenuItem  {
  label: React.ReactNode,
  key?: React.Key | null,
  icon?: React.ReactNode,
  children?: MenuItem[],
  theme?: 'light' | 'dark',
}