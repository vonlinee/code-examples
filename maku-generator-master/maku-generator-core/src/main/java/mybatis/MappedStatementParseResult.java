package mybatis;

import lombok.AllArgsConstructor;
import lombok.Data;
import mybatis.tree.TreeNode;
import org.apache.ibatis.mapping.MappedStatement;

@Data
@AllArgsConstructor
public class MappedStatementParseResult {

    private TreeNode<String> param;

    private MappedStatement mappedStatement;
}
