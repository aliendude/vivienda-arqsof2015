/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author trossky
 */
@Entity
@Table(name = "PERSON")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findByIdperson", query = "SELECT p FROM Person p WHERE p.idperson = :idperson"),
    @NamedQuery(name = "Person.findByDocument", query = "SELECT p FROM Person p WHERE p.document = :document"),
    @NamedQuery(name = "Person.findByEMail", query = "SELECT p FROM Person p WHERE p.eMail = :eMail"),
    @NamedQuery(name = "Person.findByLastName", query = "SELECT p FROM Person p WHERE p.lastName = :lastName"),
    @NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name"),
    @NamedQuery(name = "Person.findByPassword", query = "SELECT p FROM Person p WHERE p.password = :password"),
    @NamedQuery(name = "Person.findByPhone", query = "SELECT p FROM Person p WHERE p.phone = :phone"),
    @NamedQuery(name = "Person.findByProfession", query = "SELECT p FROM Person p WHERE p.profession = :profession"),
    @NamedQuery(name = "Person.findByRol", query = "SELECT p FROM Person p WHERE p.rol = :rol"),
    @NamedQuery(name = "Person.findBySalary", query = "SELECT p FROM Person p WHERE p.salary = :salary"),
    @NamedQuery(name = "Person.findByUser", query = "SELECT p FROM Person p WHERE p.user = :user"),
    @NamedQuery(name = "Person.findByCity", query = "SELECT p FROM Person p WHERE p.city = :city")})
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDPERSON")
    private Long idperson;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "DOCUMENT")
    private BigInteger document;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 255)
    @Column(name = "E_MAIL")
    private String eMail;
    @Size(max = 255)
    @Column(name = "LAST_NAME")
    private String lastName;
    @Size(max = 255)
    @Column(name = "NAME")
    private String name;
    @Size(max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PHONE")
    private BigInteger phone;
    @Size(max = 255)
    @Column(name = "PROFESSION")
    private String profession;
    @Size(max = 255)
    @Column(name = "ROL")
    private String rol;
    @Column(name = "SALARY")
    private BigInteger salary;
    @Size(max = 25)
    @Column(name = "USER")
    private String user;
    @Size(max = 50)
    @Column(name = "CITY")
    private String city;
    @OneToMany(mappedBy = "idperson")
    private Collection<Home> homeCollection;

    public Person() {
    }

    public Person(Long idperson) {
        this.idperson = idperson;
    }

    public Long getIdperson() {
        return idperson;
    }

    public void setIdperson(Long idperson) {
        this.idperson = idperson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigInteger getDocument() {
        return document;
    }

    public void setDocument(BigInteger document) {
        this.document = document;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigInteger getPhone() {
        return phone;
    }

    public void setPhone(BigInteger phone) {
        this.phone = phone;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public BigInteger getSalary() {
        return salary;
    }

    public void setSalary(BigInteger salary) {
        this.salary = salary;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @XmlTransient
    public Collection<Home> getHomeCollection() {
        return homeCollection;
    }

    public void setHomeCollection(Collection<Home> homeCollection) {
        this.homeCollection = homeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idperson != null ? idperson.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.idperson == null && other.idperson != null) || (this.idperson != null && !this.idperson.equals(other.idperson))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.Entity.Person[ idperson=" + idperson + " ]";
    }
    
}
