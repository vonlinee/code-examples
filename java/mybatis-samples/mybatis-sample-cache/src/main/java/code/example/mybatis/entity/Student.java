package code.example.mybatis.entity;

import java.io.Serializable;

public class Student implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String stuId;
	private String stuNo;
	private String stuSex;
	private String stuName;
	private String nativePlace;// 籍贯
	private String stuDepartNo;// 院系ID
	private String stuClassNo;

	public String getStuId() {
		return stuId;
	}

	public void setStuId(String stuId) {
		this.stuId = stuId;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getStuSex() {
		return stuSex;
	}

	public void setStuSex(String stuSex) {
		this.stuSex = stuSex;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public String getStuDepartNo() {
		return stuDepartNo;
	}

	public void setStuDepartNo(String stuDepartNo) {
		this.stuDepartNo = stuDepartNo;
	}

	public String getStuClassNo() {
		return stuClassNo;
	}

	public void setStuClassNo(String stuClassNo) {
		this.stuClassNo = stuClassNo;
	}

	@Override
	public String toString() {
		return "Student [stuId=" + stuId + ", stuNo=" + stuNo + ", stuSex=" + stuSex + ", stuName=" + stuName
				+ ", nativePlace=" + nativePlace + ", stuDepartNo=" + stuDepartNo + ", stuClassNo=" + stuClassNo + "]";
	}
}
