package code.example.mybatis.crud.entity;

import java.math.BigDecimal;

public class Score {

    private String scoreId;
    
    private String scoreLevel;
    
    private String courseNo;
    
    private String courseName;
    
    private BigDecimal scoreValue;
    
    private String teacherNo;
    
    public String getScoreId() {
        return scoreId;
    }

    public void setScoreId(String scoreId){
        this.scoreId = scoreId;
    }

    public String getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(String scoreLevel){
        this.scoreLevel = scoreLevel;
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

    public BigDecimal getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(BigDecimal scoreValue){
        this.scoreValue = scoreValue;
    }

    public String getTeacherNo() {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo){
        this.teacherNo = teacherNo;
    }

}
