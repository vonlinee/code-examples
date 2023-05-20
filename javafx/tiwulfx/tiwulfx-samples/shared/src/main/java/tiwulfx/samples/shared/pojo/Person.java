package tiwulfx.samples.shared.pojo;

import com.panemu.tiwulfx.table.annotation.TableViewColumn;
import com.panemu.tiwulfx.table.annotation.TableViewModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "person")
@TableViewModel
@NamedQueries({@NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")})
public class Person implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @TableViewColumn(name = "ID")
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    /**
     * 是否存活
     */
    @Column(name = "alive")
    private Boolean alive;

    /**
     * 生日
     */
    @Column(name = "birth_date")
    @TableViewColumn(name = "生日")
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    /**
     * 出生地
     */
    @Column(name = "birth_place")
    @TableViewColumn(name = "出生地")
    private String birthPlace;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "gender")
    private char gender;
    @Column(name = "name", nullable = false)
    private String name;
    @Version
    @Column(name = "version")
    private Integer version;
    @Column(name = "visit")
    private Integer visit;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "weight")
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", referencedColumnName = "id")
    private Insurance insurance;

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String email, char gender, String name) {
        this.id = id;
        this.email = email;
        this.gender = gender;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAlive() {
        return alive;
    }

    public void setAlive(Boolean alive) {
        this.alive = alive;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getVisit() {
        return visit;
    }

    public void setVisit(Integer visit) {
        this.visit = visit;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    @Override
    public String toString() {
        return "com.panemu.tiwulfx.demo.pojo.Person [id=" + id + "]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
