package code.example.mybatis.typehandler;

import com.mysql.jdbc.Blob;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BlobTypeHandler implements TypeHandler<Blob> {

    @Override
    public void setParameter(PreparedStatement ps, int i, Blob parameter, JdbcType jdbcType) throws SQLException {

    }

    @Override
    public Blob getResult(ResultSet rs, String columnName) throws SQLException {
        return null;
    }

    @Override
    public Blob getResult(ResultSet rs, int columnIndex) throws SQLException {
        return null;
    }

    @Override
    public Blob getResult(CallableStatement cs, int columnIndex) throws SQLException {
        return null;
    }
}
