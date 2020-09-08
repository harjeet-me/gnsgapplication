package org.gnsg.gms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A PRoul.
 */
@Entity
@Table(name = "p_roul")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "proul")
public class PRoul implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Column(name = "path_name")
    private String pathName;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "total_roul")
    private Double totalRoul;

    @Column(name = "total_amt")
    private Double totalAmt;

    @Column(name = "bhog_date")
    private LocalDate bhogDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToOne
    @JoinColumn(unique = true)
    private Sevadar pathi;

    @ManyToOne
    @JsonIgnoreProperties(value = "pRouls", allowSetters = true)
    private ASProgram prog;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPathName() {
        return pathName;
    }

    public PRoul pathName(String pathName) {
        this.pathName = pathName;
        return this;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getDesc() {
        return desc;
    }

    public PRoul desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getTotalRoul() {
        return totalRoul;
    }

    public PRoul totalRoul(Double totalRoul) {
        this.totalRoul = totalRoul;
        return this;
    }

    public void setTotalRoul(Double totalRoul) {
        this.totalRoul = totalRoul;
    }

    public Double getTotalAmt() {
        return totalAmt;
    }

    public PRoul totalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
        return this;
    }

    public void setTotalAmt(Double totalAmt) {
        this.totalAmt = totalAmt;
    }

    public LocalDate getBhogDate() {
        return bhogDate;
    }

    public PRoul bhogDate(LocalDate bhogDate) {
        this.bhogDate = bhogDate;
        return this;
    }

    public void setBhogDate(LocalDate bhogDate) {
        this.bhogDate = bhogDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public PRoul createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public PRoul createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public PRoul lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public PRoul lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Sevadar getPathi() {
        return pathi;
    }

    public PRoul pathi(Sevadar sevadar) {
        this.pathi = sevadar;
        return this;
    }

    public void setPathi(Sevadar sevadar) {
        this.pathi = sevadar;
    }

    public ASProgram getProg() {
        return prog;
    }

    public PRoul prog(ASProgram aSProgram) {
        this.prog = aSProgram;
        return this;
    }

    public void setProg(ASProgram aSProgram) {
        this.prog = aSProgram;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PRoul)) {
            return false;
        }
        return id != null && id.equals(((PRoul) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PRoul{" +
            "id=" + getId() +
            ", pathName='" + getPathName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", totalRoul=" + getTotalRoul() +
            ", totalAmt=" + getTotalAmt() +
            ", bhogDate='" + getBhogDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
