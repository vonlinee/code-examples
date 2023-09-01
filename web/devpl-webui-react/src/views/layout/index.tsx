import Content from "./Content";
import Header from "./Header";
import RightPanel from "./RightPanel";
import Sider from "./Sider";
import TagsView from "./TagsView";
import { Layout } from "antd";

/**
 * 基础布局
 * @param props 是否展示标签视图 
 * @returns 
 */
const BasicLayout = (props: {tagsView: boolean}) => {
  const { tagsView } = props;
  return (
    <Layout style={{ minHeight: "100vh" }}>
      <Sider />
      <Layout>
        <Header />
        {tagsView ? <TagsView /> : null}
        <Content />
        <RightPanel />
      </Layout>
    </Layout>
  );
};
export default BasicLayout
