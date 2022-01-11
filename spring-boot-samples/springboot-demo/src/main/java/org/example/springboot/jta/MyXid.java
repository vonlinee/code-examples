package org.example.springboot.jta;

import javax.transaction.xa.Xid;

public class MyXid implements Xid {

	protected int formatId;
	protected byte[] gtrid;
	protected byte[] bqual;

	public MyXid() {
		System.out.println("this => " + this);
	}

	public MyXid(int formatId, byte gtrid[], byte bqual[]) {
		this.formatId = formatId;
		this.gtrid = gtrid;
		this.bqual = bqual;
	}

	@Override
	public byte[] getBranchQualifier() {
		return bqual;
	}

	@Override
	public int getFormatId() {
		return formatId;
	}

	@Override
	public byte[] getGlobalTransactionId() {
		return gtrid;
	}
}
