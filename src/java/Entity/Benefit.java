/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mac
 */
@Entity
@Table(name = "BENEFIT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Benefit.findAll", query = "SELECT b FROM Benefit b"),
    @NamedQuery(name = "Benefit.findByIdbenefit", query = "SELECT b FROM Benefit b WHERE b.idbenefit = :idbenefit"),
    @NamedQuery(name = "Benefit.findByBenefittype", query = "SELECT b FROM Benefit b WHERE b.benefittype = :benefittype"),
    @NamedQuery(name = "Benefit.findByBenefitvalue", query = "SELECT b FROM Benefit b WHERE b.benefitvalue = :benefitvalue")})
public class Benefit implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IDBENEFIT")
    private Long idbenefit;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "BENEFITDESCRIPTION")
    private String benefitdescription;
    @Size(max = 255)
    @Column(name = "BENEFITTYPE")
    private String benefittype;
    @Column(name = "BENEFITVALUE")
    private BigInteger benefitvalue;
    @JoinColumn(name = "IDHOME", referencedColumnName = "IDHOME")
    @ManyToOne
    private Home idhome;

    public Benefit() {
    }

    public Benefit(Long idbenefit) {
        this.idbenefit = idbenefit;
    }

    public Long getIdbenefit() {
        return idbenefit;
    }

    public void setIdbenefit(Long idbenefit) {
        this.idbenefit = idbenefit;
    }

    public String getBenefitdescription() {
        return benefitdescription;
    }

    public void setBenefitdescription(String benefitdescription) {
        this.benefitdescription = benefitdescription;
    }

    public String getBenefittype() {
        return benefittype;
    }

    public void setBenefittype(String benefittype) {
        this.benefittype = benefittype;
    }

    public BigInteger getBenefitvalue() {
        return benefitvalue;
    }

    public void setBenefitvalue(BigInteger benefitvalue) {
        this.benefitvalue = benefitvalue;
    }

    public Home getIdhome() {
        return idhome;
    }

    public void setIdhome(Home idhome) {
        this.idhome = idhome;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idbenefit != null ? idbenefit.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Benefit)) {
            return false;
        }
        Benefit other = (Benefit) object;
        if ((this.idbenefit == null && other.idbenefit != null) || (this.idbenefit != null && !this.idbenefit.equals(other.idbenefit))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Benefit[ idbenefit=" + idbenefit + " ]";
    }
    
}
