package code.example.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class MutiTypeHandler<T> implements TypeHandler<T> {

	@Override
	public void setParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
		
	}

	@Override
	public T getResult(ResultSet rs, String columnName) throws SQLException {
		return null;
	}

	@Override
	public T getResult(ResultSet rs, int columnIndex) throws SQLException {
		return null;
	}

	@Override
	public T getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
}
