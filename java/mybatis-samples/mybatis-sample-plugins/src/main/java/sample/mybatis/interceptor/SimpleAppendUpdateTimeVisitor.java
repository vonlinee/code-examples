package sample.mybatis.interceptor;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLObject;
import com.alibaba.druid.sql.ast.SQLOrderBy;
import com.alibaba.druid.sql.ast.expr.SQLBinaryOpExpr;
import com.alibaba.druid.sql.ast.expr.SQLInListExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectGroupByClause;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectOrderByItem;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.dialect.mysql.visitor.MySqlASTVisitorAdapter;
import com.alibaba.druid.util.StringUtils;

/**
 * 
 */
public class SimpleAppendUpdateTimeVisitor extends MySqlASTVisitorAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(MyBatisSqlRebuildPlugin.class);
	
	private static final ThreadLocal<Boolean> REWRITE_STATUS_CACHE = new ThreadLocal<>();

	private static final String UPDATE_TIME_COLUMN = "update_time";

	@Override
	public void preVisit(SQLObject astNode) {
		
	}
	

	@Override
	public void postVisit(SQLObject astNode) {
		
	}

	@Override
	public boolean visit(MySqlSelectQueryBlock x) {
		List<SQLSelectItem> selectList = x.getSelectList();
		
		SQLSelectGroupByClause groupBy = x.getGroupBy();
		
		SQLExpr where = x.getWhere();
		List<SQLObject> children = where.getChildren();
		
		for (int i = 0; i < children.size(); i++) {
			if (children.get(i) instanceof SQLInListExpr) {
				SQLInListExpr inListExpr = (SQLInListExpr) children.get(i);
				List<SQLExpr> targetList = inListExpr.getTargetList();
				
			}
		}
		
		SQLOrderBy orderBy = x.getOrderBy();
		List<SQLSelectOrderByItem> items = orderBy.getItems();
		
		
		return super.visit(x);
	}

	@Override
	public boolean visit(MySqlInsertStatement x) {
		boolean hasUpdateTimeCol = false;
		// duplicate key update得到的都是SQLBinaryOpExpr
		List<SQLExpr> duplicateKeyUpdate = x.getDuplicateKeyUpdate();
		if (!duplicateKeyUpdate.isEmpty()) {
			for (SQLExpr sqlExpr : duplicateKeyUpdate) {
				if (sqlExpr instanceof SQLBinaryOpExpr
						&& ((SQLBinaryOpExpr) sqlExpr).conditionContainsColumn(UPDATE_TIME_COLUMN)) {
					hasUpdateTimeCol = true;
					break;
				}
			}
			if (!hasUpdateTimeCol) {
				// append update time column
				String tableAlias = x.getTableSource().getAlias();
				StringBuilder setUpdateTimeBuilder = new StringBuilder();
				if (!StringUtils.isEmpty(tableAlias)) {
					setUpdateTimeBuilder.append(tableAlias).append('.');
				}
				setUpdateTimeBuilder.append(UPDATE_TIME_COLUMN).append(" = now()");
				SQLExpr sqlExpr = SQLUtils.toMySqlExpr(setUpdateTimeBuilder.toString());
				duplicateKeyUpdate.add(sqlExpr);
				// 重写状态记录
				REWRITE_STATUS_CACHE.set(Boolean.TRUE);
			}
		}
		return super.visit(x);
	}

	/**
	 * 返回重写状态并重置重写状态
	 *
	 * @return 重写状态，{@code true}表示已重写，{@code false}表示未重写
	 */
	public boolean getAndResetRewriteStatus() {
		boolean rewriteStatus = Optional.ofNullable(REWRITE_STATUS_CACHE.get()).orElse(Boolean.FALSE);
		// reset rewrite status
		REWRITE_STATUS_CACHE.remove();
		return rewriteStatus;
	}
}
