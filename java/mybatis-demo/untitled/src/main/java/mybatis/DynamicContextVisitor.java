package mybatis;

import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.session.Configuration;

import java.util.Map;

public class DynamicContextVisitor extends DynamicContext {

    public DynamicContextVisitor(Configuration configuration, Object parameterObject) {
        super(configuration, parameterObject);
    }

    @Override
    public Map<String, Object> getBindings() {
        return super.getBindings();
    }

    @Override
    public void bind(String name, Object value) {
        super.bind(name, value);
    }

    @Override
    public void appendSql(String sql) {
        super.appendSql(sql);
    }

    @Override
    public String getSql() {
        return super.getSql();
    }

    @Override
    public int getUniqueNumber() {
        return super.getUniqueNumber();
    }
}
