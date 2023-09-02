import { HashRouter, Route, Routes } from "react-router-dom";
import Layout from "@/views/layout";

/**
 * 路由组件
 * @returns
 */
const Router = () => {
  return (
    <HashRouter>
      <Routes>
        <Route path="/" element={<Layout tagsView />} />
      </Routes>
    </HashRouter>
  );
};

export default Router;
