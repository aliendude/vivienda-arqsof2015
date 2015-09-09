/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Benefit.findByIdhome", query = "SELECT b FROM Benefit b WHERE b.benefitPK.idhome = :idhome"),
    @NamedQuery(name = "Benefit.findByIdbenefit", query = "SELECT b FROM Benefit b WHERE b.benefitPK.idbenefit = :idbenefit"),
    @NamedQuery(name = "Benefit.findByBenefittype", query = "SELECT b FROM Benefit b WHERE b.benefittype = :benefittype"),
    @NamedQuery(name = "Benefit.findByBenefitvalue", query = "SELECT b FROM Benefit b WHERE b.benefitvalue = :benefitvalue")})
public class Benefit implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected BenefitPK benefitPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "BENEFITTYPE")
    private String benefittype;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "BENEFITVALUE")
    private BigDecimal benefitvalue;
    @Lob
    @Size(max = 65535)
    @Column(name = "BENEFITDESCRIPTION")
    private String benefitdescription;
    @JoinColumn(name = "IDHOME", referencedColumnName = "IDHOME", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Home home;

    public Benefit() {
    }

    public Benefit(BenefitPK benefitPK) {
        this.benefitPK = benefitPK;
    }

    public Benefit(BenefitPK benefitPK, String benefittype, BigDecimal benefitvalue) {
        this.benefitPK = benefitPK;
        this.benefittype = benefittype;
        this.benefitvalue = benefitvalue;
    }

    public Benefit(long idhome, long idbenefit) {
        this.benefitPK = new BenefitPK(idhome, idbenefit);
    }

    public BenefitPK getBenefitPK() {
        return benefitPK;
    }

    public void setBenefitPK(BenefitPK benefitPK) {
        this.benefitPK = benefitPK;
    }

    public String getBenefittype() {
        return benefittype;
    }

    public void setBenefittype(String benefittype) {
        this.benefittype = benefittype;
    }

    public BigDecimal getBenefitvalue() {
        return benefitvalue;
    }

    public void setBenefitvalue(BigDecimal benefitvalue) {
        this.benefitvalue = benefitvalue;
    }

    public String getBenefitdescription() {
        return benefitdescription;
    }

    public void setBenefitdescription(String benefitdescription) {
        this.benefitdescription = benefitdescription;
    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (benefitPK != null ? benefitPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Benefit)) {
            return false;
        }
        Benefit other = (Benefit) object;
        if ((this.benefitPK == null && other.benefitPK != null) || (this.benefitPK != null && !this.benefitPK.equals(other.benefitPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.Entity.Benefit[ benefitPK=" + benefitPK + " ]";
    }
    
}
