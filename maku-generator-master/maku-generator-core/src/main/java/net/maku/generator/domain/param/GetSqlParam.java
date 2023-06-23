package net.maku.generator.domain.param;

import lombok.Data;
import net.maku.generator.domain.ParamNode;

import java.util.List;

@Data
public class GetSqlParam {

    /**
     * Mapper Statement
     */
    private String mapperStatement;

    /**
     * 参数
     */
    private List<ParamNode> msParams;

    /**
     * 实际sql还是预编译的sql
     */
    private int real;
}
