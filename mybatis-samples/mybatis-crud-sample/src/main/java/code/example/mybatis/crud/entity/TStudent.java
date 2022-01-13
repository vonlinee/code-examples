package code.example.mybatis.crud.entity;


/**
 * @author Von
 * @date 2022-01-13 13:38:35
 * @email 
 */
public class TStudent {

    private String stuId;
    
    private String stuNo;
    
    private String stuSex;
    
    private String stuName;
    
    // 籍贯
    private String nativePlace;
    
    // 院系ID
    private String stuDepartNo;
    
    private String stuClassNo;
    
    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId){
        this.stuId = stuId;
    }

    public String getStuNo() {
        return stuNo;
    }

    public void setStuNo(String stuNo){
        this.stuNo = stuNo;
    }

    public String getStuSex() {
        return stuSex;
    }

    public void setStuSex(String stuSex){
        this.stuSex = stuSex;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName){
        this.stuName = stuName;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace){
        this.nativePlace = nativePlace;
    }

    public String getStuDepartNo() {
        return stuDepartNo;
    }

    public void setStuDepartNo(String stuDepartNo){
        this.stuDepartNo = stuDepartNo;
    }

    public String getStuClassNo() {
        return stuClassNo;
    }

    public void setStuClassNo(String stuClassNo){
        this.stuClassNo = stuClassNo;
    }

}
