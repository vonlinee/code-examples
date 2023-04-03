package io.devpl.toolkit.sqlparser;

import cn.hutool.core.util.ReUtil;
import com.google.common.collect.Lists;
import io.devpl.toolkit.utils.StringUtils;
import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@ToString
public class ConditionExpression {

    private static final Pattern DYNAMIC_PARAM_PATTERN = Pattern.compile("#\\{(.*?)}");

    private String findPattern;

    private String logicOperator;

    private String leftExpr;

    private String rightExpr;

    private String operator;

    //如果operator是between，会存在middleOperator和endExpr
    private String middleOperator;

    private String endExpr;

    private List<String> paramNames;

    public int parseDynamicParams(String content) {
        if (paramNames == null) {
            paramNames = Lists.newArrayList();
        }
        Matcher m = DYNAMIC_PARAM_PATTERN.matcher(content);
        int index = 0;
        while (m.find()) {
            String group = m.group(index);
            paramNames.add(group.substring(2, group.length() - 1));
            index++;
        }
        return index;
    }

    public String getFindPattern() {
        StringBuilder pattern = new StringBuilder();
        if (!StringUtils.hasLength(logicOperator)) {
            pattern.append("\\s+");
            pattern.append(logicOperator);
            pattern.append("\\s+");
        }
        pattern.append(leftExpr);
        pattern.append("\\s*");
        pattern.append(operator);
        pattern.append("\\s*");
        pattern.append(ReUtil.escape(getRightExpr()));
        if (!StringUtils.hasLength(middleOperator)) {
            pattern.append("\\s+");
            pattern.append(middleOperator);
            pattern.append("\\s+");
            pattern.append(ReUtil.escape(getEndExpr()));
        }
        return pattern.toString();
    }

    public static boolean isDynamicParam(String content) {
        return DYNAMIC_PARAM_PATTERN.matcher(content).find();
    }
}
