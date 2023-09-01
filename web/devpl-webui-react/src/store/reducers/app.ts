import * as types from "../action-types";

const initState = {
  sidebarCollapsed: false,
  settingPanelVisible: false,
};

/**
 * reducer 又接收两个参数，state 和 action，根据不同的 action 返回一个新的 state
 * @param state
 * @param action Actions 是具有 type 字段的普通 JavaScript 对象
 * @returns
 */
export default function app(state = initState, action: { type: string }) {
  switch (action.type) {
    case types.APP_TOGGLE_SIDEBAR:
      return {
        ...state,
        sidebarCollapsed: !state.sidebarCollapsed,
      };
    case types.APP_TOGGLE_SETTINGPANEL:
      return {
        ...state,
        settingPanelVisible: !state.settingPanelVisible,
      };
    default:
      return state;
  }
}
