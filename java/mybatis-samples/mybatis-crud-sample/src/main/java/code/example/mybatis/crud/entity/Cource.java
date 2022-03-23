package code.example.mybatis.crud.entity;

import java.math.BigDecimal;

public class Cource {

    private String courseId;
    
    private String courseNo;
    
    private String courseName;
    
    // 课程时长
    private BigDecimal courseTimeLong;
    
    private String teacherNo;
    
    // 课程所开设院系编号
    private String departNo;
    
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId){
        this.courseId = courseId;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo){
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName){
        this.courseName = courseName;
    }

    public BigDecimal getCourseTimeLong() {
        return courseTimeLong;
    }

    public void setCourseTimeLong(BigDecimal courseTimeLong){
        this.courseTimeLong = courseTimeLong;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo){
        this.teacherNo = teacherNo;
    }

    public String getDepartNo() {
        return departNo;
    }

    public void setDepartNo(String departNo){
        this.departNo = departNo;
    }

}
