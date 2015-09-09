/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccess.Entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author mac
 */
@Embeddable
public class BenefitPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDHOME")
    private long idhome;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDBENEFIT")
    private long idbenefit;

    public BenefitPK() {
    }

    public BenefitPK(long idhome, long idbenefit) {
        this.idhome = idhome;
        this.idbenefit = idbenefit;
    }

    public long getIdhome() {
        return idhome;
    }

    public void setIdhome(long idhome) {
        this.idhome = idhome;
    }

    public long getIdbenefit() {
        return idbenefit;
    }

    public void setIdbenefit(long idbenefit) {
        this.idbenefit = idbenefit;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idhome;
        hash += (int) idbenefit;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BenefitPK)) {
            return false;
        }
        BenefitPK other = (BenefitPK) object;
        if (this.idhome != other.idhome) {
            return false;
        }
        if (this.idbenefit != other.idbenefit) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DataAccess.Entity.BenefitPK[ idhome=" + idhome + ", idbenefit=" + idbenefit + " ]";
    }
    
}
