/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
 * @author mac
 */
@Entity
@Table(name = "HOME")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Home.findAll", query = "SELECT h FROM Home h"),
    @NamedQuery(name = "Home.findByIdhome", query = "SELECT h FROM Home h WHERE h.idhome = :idhome"),
    @NamedQuery(name = "Home.findByContracttype", query = "SELECT h FROM Home h WHERE h.contracttype = :contracttype"),
    @NamedQuery(name = "Home.findByCity", query = "SELECT h FROM Home h WHERE h.city = :city"),
    @NamedQuery(name = "Home.findByHomevalue", query = "SELECT h FROM Home h WHERE h.homevalue = :homevalue")})
public class Home implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDHOME")
    private Long idhome;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "HOMETYPE")
    private String hometype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CONTRACTTYPE")
    private String contracttype;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CITY")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "HOMEADDRESS")
    private String homeaddress;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "HOMEVALUE")
    private BigDecimal homevalue;
    @JoinColumn(name = "IDPERSON", referencedColumnName = "IDPERSON")
    @ManyToOne
    private Person idperson;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "home")
    private List<Benefit> benefitList;

    public Home() {
    }

    public Home(Long idhome) {
        this.idhome = idhome;
    }

    public Home(Long idhome, String hometype, String contracttype, String city, String homeaddress, BigDecimal homevalue) {
        this.idhome = idhome;
        this.hometype = hometype;
        this.contracttype = contracttype;
        this.city = city;
        this.homeaddress = homeaddress;
        this.homevalue = homevalue;
    }

    public Long getIdhome() {
        return idhome;
    }

    public void setIdhome(Long idhome) {
        this.idhome = idhome;
    }

    public String getHometype() {
        return hometype;
    }

    public void setHometype(String hometype) {
        this.hometype = hometype;
    }

    public String getContracttype() {
        return contracttype;
    }

    public void setContracttype(String contracttype) {
        this.contracttype = contracttype;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress;
    }

    public BigDecimal getHomevalue() {
        return homevalue;
    }

    public void setHomevalue(BigDecimal homevalue) {
        this.homevalue = homevalue;
    }

    public Person getIdperson() {
        return idperson;
    }

    public void setIdperson(Person idperson) {
        this.idperson = idperson;
    }

    @XmlTransient
    public List<Benefit> getBenefitList() {
        return benefitList;
    }

    public void setBenefitList(List<Benefit> benefitList) {
        this.benefitList = benefitList;
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
