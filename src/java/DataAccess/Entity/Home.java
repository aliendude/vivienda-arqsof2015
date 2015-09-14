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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "HOME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Home.findAll", query = "SELECT h FROM Home h"),
    @NamedQuery(name = "Home.findByIdhome", query = "SELECT h FROM Home h WHERE h.idhome = :idhome"),
    @NamedQuery(name = "Home.findByCity", query = "SELECT h FROM Home h WHERE h.city = :city"),
    @NamedQuery(name = "Home.findByContracttype", query = "SELECT h FROM Home h WHERE h.contracttype = :contracttype"),
    @NamedQuery(name = "Home.findByHomevalue", query = "SELECT h FROM Home h WHERE h.homevalue = :homevalue")})
public class Home implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDHOME")
    private Long idhome;
    @Size(max = 255)
    @Column(name = "CITY")
    private String city;
    @Size(max = 255)
    @Column(name = "CONTRACTTYPE")
    private String contracttype;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "HOMEADDRESS")
    private String homeaddress;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "HOMETYPE")
    private String hometype;
    @Column(name = "HOMEVALUE")
    private BigInteger homevalue;
    @OneToMany(mappedBy = "idhome")
    private Collection<Benefit> benefitCollection;
    @JoinColumn(name = "IDPERSON", referencedColumnName = "IDPERSON")
    @ManyToOne
    private Person idperson;

    public Home() {
    }

    public Home(Long idhome) {
        this.idhome = idhome;
    }

    public Long getIdhome() {
        return idhome;
    }

    public void setIdhome(Long idhome) {
        this.idhome = idhome;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public String getHometype() {
        return hometype;
    }

    public void setHometype(String hometype) {
        this.hometype = hometype;
    }

    public BigInteger getHomevalue() {
        return homevalue;
    }

    public void setHomevalue(BigInteger homevalue) {
        this.homevalue = homevalue;
    }

    @XmlTransient
    public Collection<Benefit> getBenefitCollection() {
        return benefitCollection;
    }

    public void setBenefitCollection(Collection<Benefit> benefitCollection) {
        this.benefitCollection = benefitCollection;
    }

    public Person getIdperson() {
        return idperson;
    }

    public void setIdperson(Person idperson) {
        this.idperson = idperson;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idhome != null ? idhome.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Home)) {
            return false;
        }
        Home other = (Home) object;
        if ((this.idhome == null && other.idhome != null) || (this.idhome != null && !this.idhome.equals(other.idhome))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.Entity.Home[ idhome=" + idhome + " ]";
    }
    
}
