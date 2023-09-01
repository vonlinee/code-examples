import defaultSettings from "@/defaultSettings";
const { showSettings, sidebarLogo, fixedHeader, tagsView } = defaultSettings;

const initState = {
  showSettings: showSettings,
  sidebarLogo: sidebarLogo,
  fixedHeader: fixedHeader,
  tagsView: tagsView,
};
export default function settings(state = initState, action) {
  return null
}
