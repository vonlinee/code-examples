package mybatis;

import java.util.List;

/**
 * https://juejin.cn/post/7120742283393105927
 * 树形结构的抽象数据模型
 * @param <T> 子节点
 */
public interface TreeNode<T extends TreeNode<T>> {

    /**
     * 获取子节点
     * @return 子节点列表
     */
    List<T> getChildren();

    /**
     * 转为子类型
     * @return 子类型
     */
    boolean isTypeOf(Class<? extends T> type);
}
