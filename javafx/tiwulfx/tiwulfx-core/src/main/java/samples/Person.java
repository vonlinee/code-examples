/*
 * License GNU LGPL
 * Copyright (C) 2012 Amrullah <amrullah@panemu.com>.
 */
package samples;

import tiwulfx.samples.shared.pojo.Insurance;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author Amrullah <amrullah@panemu.com>
 */
@Entity
@Table(name = "person")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p")})
public class Person implements Serializable , Cloneable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "alive")
    private Boolean alive;
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;
    @Column(name = "birth_place")
    private String birthPlace;
    @Column(name = "email", nullable=false, unique = true)
    private String email;
    @Column(name = "gender")
    private char gender;
    @Column(name = "name", nullable=false)
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
        return "com.panemu.tiwulfx.demo.pojo.Person[ id=" + id + " ]";
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
