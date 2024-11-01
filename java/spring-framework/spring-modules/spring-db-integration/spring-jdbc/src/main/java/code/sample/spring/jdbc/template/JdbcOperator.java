package code.sample.spring.jdbc.template;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcOperator implements JdbcOperations {

	@Override
	public <T> T execute(ConnectionCallback<T> action) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T execute(StatementCallback<T> action) throws DataAccessException {
		return null;
	}

	@Override
	public void execute(String sql) throws DataAccessException {

	}

	@Override
	public <T> T query(String sql, ResultSetExtractor<T> rse) throws DataAccessException {
		return null;
	}

	@Override
	public void query(String sql, RowCallbackHandler rch) throws DataAccessException {

	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType) throws DataAccessException {

		return null;
	}

	@Override
	public Map<String, Object> queryForMap(String sql) throws DataAccessException {
		return null;
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType) throws DataAccessException {
		return null;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql) throws DataAccessException {
		return null;
	}

	@Override
	public SqlRowSet queryForRowSet(String sql) throws DataAccessException {
		return null;
	}

	@Override
	public int update(String sql) throws DataAccessException {
		return 0;
	}

	@Override
	public int[] batchUpdate(String... sql) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T execute(PreparedStatementCreator psc, PreparedStatementCallback<T> action) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T execute(String sql, PreparedStatementCallback<T> action) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T query(PreparedStatementCreator psc, ResultSetExtractor<T> rse) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T query(String sql, PreparedStatementSetter pss, ResultSetExtractor<T> rse) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T query(String sql, Object[] args, int[] argTypes, ResultSetExtractor<T> rse)
			throws DataAccessException {

		return null;
	}

	@Override
	public <T> T query(String sql, Object[] args, ResultSetExtractor<T> rse) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T query(String sql, ResultSetExtractor<T> rse, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public void query(PreparedStatementCreator psc, RowCallbackHandler rch) throws DataAccessException {

	}

	@Override
	public void query(String sql, PreparedStatementSetter pss, RowCallbackHandler rch) throws DataAccessException {

	}

	@Override
	public void query(String sql, Object[] args, int[] argTypes, RowCallbackHandler rch) throws DataAccessException {

	}

	@Override
	public void query(String sql, Object[] args, RowCallbackHandler rch) throws DataAccessException {

	}

	@Override
	public void query(String sql, RowCallbackHandler rch, Object... args) throws DataAccessException {

	}

	@Override
	public <T> List<T> query(PreparedStatementCreator psc, RowMapper<T> rowMapper) throws DataAccessException {

		return null;
	}

	@Override
	public <T> List<T> query(String sql, PreparedStatementSetter pss, RowMapper<T> rowMapper)
			throws DataAccessException {

		return null;
	}

	@Override
	public <T> List<T> query(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws DataAccessException {

		return null;
	}

	@Override
	public <T> List<T> query(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {

		return null;
	}

	@Override
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, RowMapper<T> rowMapper)
			throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, RowMapper<T> rowMapper) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, int[] argTypes, Class<T> requiredType)
			throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, Object[] args, Class<T> requiredType) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object[] args, int[] argTypes) throws DataAccessException {

		return null;
	}

	@Override
	public Map<String, Object> queryForMap(String sql, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] args, int[] argTypes, Class<T> elementType)
			throws DataAccessException {

		return null;
	}

	@Override
	public <T> List<T> queryForList(String sql, Object[] args, Class<T> elementType) throws DataAccessException {

		return null;
	}

	@Override
	public <T> List<T> queryForList(String sql, Class<T> elementType, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object[] args, int[] argTypes)
			throws DataAccessException {

		return null;
	}

	@Override
	public List<Map<String, Object>> queryForList(String sql, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public SqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes) throws DataAccessException {

		return null;
	}

	@Override
	public SqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException {

		return null;
	}

	@Override
	public int update(PreparedStatementCreator psc) throws DataAccessException {

		return 0;
	}

	@Override
	public int update(PreparedStatementCreator psc, KeyHolder generatedKeyHolder) throws DataAccessException {

		return 0;
	}

	@Override
	public int update(String sql, PreparedStatementSetter pss) throws DataAccessException {

		return 0;
	}

	@Override
	public int update(String sql, Object[] args, int[] argTypes) throws DataAccessException {

		return 0;
	}

	@Override
	public int update(String sql, Object... args) throws DataAccessException {

		return 0;
	}

	@Override
	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) throws DataAccessException {

		return null;
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs) throws DataAccessException {

		return null;
	}

	@Override
	public int[] batchUpdate(String sql, List<Object[]> batchArgs, int[] argTypes) throws DataAccessException {

		return null;
	}

	@Override
	public <T> int[][] batchUpdate(String sql, Collection<T> batchArgs, int batchSize,
			ParameterizedPreparedStatementSetter<T> pss) throws DataAccessException {

		return null;
	}

	@Override
	public <T> T execute(CallableStatementCreator csc, CallableStatementCallback<T> action) throws DataAccessException {
		return null;
	}

	@Override
	public <T> T execute(String callString, CallableStatementCallback<T> action) throws DataAccessException {
		return null;
	}

	@Override
	public Map<String, Object> call(CallableStatementCreator csc, List<SqlParameter> declaredParameters)
			throws DataAccessException {
		return null;
	}
}
