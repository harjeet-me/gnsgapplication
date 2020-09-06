package org.gnsg.gms.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.gnsg.gms.domain.enumeration.PROGTYPE;

import org.gnsg.gms.domain.enumeration.EventStatus;

/**
 * A ASProgram.
 */
@Entity
@Table(name = "as_program")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "asprogram")
public class ASProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "program")
    private PROGTYPE program;

    @Column(name = "family")
    private String family;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "booking_date")
    private Instant bookingDate;

    @Column(name = "jhi_desc")
    private String desc;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EventStatus status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "prog")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PRoul> pRouls = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PROGTYPE getProgram() {
        return program;
    }

    public ASProgram program(PROGTYPE program) {
        this.program = program;
        return this;
    }

    public void setProgram(PROGTYPE program) {
        this.program = program;
    }

    public String getFamily() {
        return family;
    }

    public ASProgram family(String family) {
        this.family = family;
        return this;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ASProgram phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public ASProgram address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public ASProgram startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public ASProgram endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public ASProgram remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Instant getBookingDate() {
        return bookingDate;
    }

    public ASProgram bookingDate(Instant bookingDate) {
        this.bookingDate = bookingDate;
        return this;
    }

    public void setBookingDate(Instant bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getDesc() {
        return desc;
    }

    public ASProgram desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public EventStatus getStatus() {
        return status;
    }

    public ASProgram status(EventStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public ASProgram createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ASProgram createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public ASProgram lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ASProgram lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<PRoul> getPRouls() {
        return pRouls;
    }

    public ASProgram pRouls(Set<PRoul> pRouls) {
        this.pRouls = pRouls;
        return this;
    }

    public ASProgram addPRoul(PRoul pRoul) {
        this.pRouls.add(pRoul);
        pRoul.setProg(this);
        return this;
    }

    public ASProgram removePRoul(PRoul pRoul) {
        this.pRouls.remove(pRoul);
        pRoul.setProg(null);
        return this;
    }

    public void setPRouls(Set<PRoul> pRouls) {
        this.pRouls = pRouls;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ASProgram)) {
            return false;
        }
        return id != null && id.equals(((ASProgram) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ASProgram{" +
            "id=" + getId() +
            ", program='" + getProgram() + "'" +
            ", family='" + getFamily() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", remark='" + getRemark() + "'" +
            ", bookingDate='" + getBookingDate() + "'" +
            ", desc='" + getDesc() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
