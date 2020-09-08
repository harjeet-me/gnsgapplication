package org.gnsg.gms.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "sevadar")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "sevadar")
public class Sevadar implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "seva_start_date")
    private Instant sevaStartDate;

    @Column(name = "seva_end_date")
    private Instant sevaEndDate;

    @Column(name = "is_valid")
    private Boolean isValid;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @OneToMany(mappedBy = "sevadar")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Program> programs = new HashSet<>();

    @OneToMany(mappedBy = "pathi")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PathReport> pathReports = new HashSet<>();

    @OneToMany(mappedBy = "pathi")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<PRoul> pRouls = new HashSet<>();

    @ManyToMany(mappedBy = "granthis")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<ASProgram> pathProgs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Sevadar name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public Sevadar email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Sevadar phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public Sevadar address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Instant getSevaStartDate() {
        return sevaStartDate;
    }

    public Sevadar sevaStartDate(Instant sevaStartDate) {
        this.sevaStartDate = sevaStartDate;
        return this;
    }

    public void setSevaStartDate(Instant sevaStartDate) {
        this.sevaStartDate = sevaStartDate;
    }

    public Instant getSevaEndDate() {
        return sevaEndDate;
    }

    public Sevadar sevaEndDate(Instant sevaEndDate) {
        this.sevaEndDate = sevaEndDate;
        return this;
    }

    public void setSevaEndDate(Instant sevaEndDate) {
        this.sevaEndDate = sevaEndDate;
    }

    public Boolean isIsValid() {
        return isValid;
    }

    public Sevadar isValid(Boolean isValid) {
        this.isValid = isValid;
        return this;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Sevadar createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Sevadar createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Sevadar lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Sevadar lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Set<Program> getPrograms() {
        return programs;
    }

    public Sevadar programs(Set<Program> programs) {
        this.programs = programs;
        return this;
    }

    public Sevadar addProgram(Program program) {
        this.programs.add(program);
        program.setSevadar(this);
        return this;
    }

    public Sevadar removeProgram(Program program) {
        this.programs.remove(program);
        program.setSevadar(null);
        return this;
    }

    public void setPrograms(Set<Program> programs) {
        this.programs = programs;
    }

    public Set<PathReport> getPathReports() {
        return pathReports;
    }

    public Sevadar pathReports(Set<PathReport> pathReports) {
        this.pathReports = pathReports;
        return this;
    }

    public Sevadar addPathReport(PathReport pathReport) {
        this.pathReports.add(pathReport);
        pathReport.setPathi(this);
        return this;
    }

    public Sevadar removePathReport(PathReport pathReport) {
        this.pathReports.remove(pathReport);
        pathReport.setPathi(null);
        return this;
    }

    public void setPathReports(Set<PathReport> pathReports) {
        this.pathReports = pathReports;
    }

    public Set<PRoul> getPRouls() {
        return pRouls;
    }

    public Sevadar pRouls(Set<PRoul> pRouls) {
        this.pRouls = pRouls;
        return this;
    }

    public Sevadar addPRoul(PRoul pRoul) {
        this.pRouls.add(pRoul);
        pRoul.setPathi(this);
        return this;
    }

    public Sevadar removePRoul(PRoul pRoul) {
        this.pRouls.remove(pRoul);
        pRoul.setPathi(null);
        return this;
    }

    public void setPRouls(Set<PRoul> pRouls) {
        this.pRouls = pRouls;
    }

    public Set<ASProgram> getPathProgs() {
        return pathProgs;
    }

    public Sevadar pathProgs(Set<ASProgram> aSPrograms) {
        this.pathProgs = aSPrograms;
        return this;
    }

    public Sevadar addPathProg(ASProgram aSProgram) {
        this.pathProgs.add(aSProgram);
        aSProgram.getGranthis().add(this);
        return this;
    }

    public Sevadar removePathProg(ASProgram aSProgram) {
        this.pathProgs.remove(aSProgram);
        aSProgram.getGranthis().remove(this);
        return this;
    }

    public void setPathProgs(Set<ASProgram> aSPrograms) {
        this.pathProgs = aSPrograms;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Sevadar)) {
            return false;
        }
        return id != null && id.equals(((Sevadar) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Sevadar{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", address='" + getAddress() + "'" +
            ", sevaStartDate='" + getSevaStartDate() + "'" +
            ", sevaEndDate='" + getSevaEndDate() + "'" +
            ", isValid='" + isIsValid() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
