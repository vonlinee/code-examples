package org.example.springboot.jta;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.XAConnection;
import javax.sql.XADataSource;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class JTA {

	public XADataSource getXaDataSource() {
		return null;
	}
	
	public static void main(String[] args) throws SQLException {
		JTA jta = new JTA();
		jta.name();
	}

	public void name() throws SQLException {
		XADataSource xaDS = getXaDataSource();
		XAConnection xaCon;
		XAResource xaRes;
		Xid xid;
		Connection con;
		Statement stmt;
		int ret;
		xaCon = xaDS.getXAConnection("root", "123456");
		xaRes = xaCon.getXAResource();
		con = xaCon.getConnection();
		stmt = con.createStatement();
		xid = new MyXid(100, new byte[] { 0x01 }, new byte[] { 0x02 });
		try {
			xaRes.start(xid, XAResource.TMNOFLAGS);
			stmt.executeUpdate("insert into test_table values (100)");
			xaRes.end(xid, XAResource.TMSUCCESS);
			ret = xaRes.prepare(xid);
			if (ret == XAResource.XA_OK) {
				xaRes.commit(xid, false);
			}
		} catch (XAException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			con.close();
			xaCon.close();
		}
	}
}
