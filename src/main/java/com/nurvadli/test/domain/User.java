package com.nurvadli.test.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "test_users")
public class User implements Serializable {

    private static final long serialVersionUID = -2147468513335906679L;
    private static final String DEFAULT_USER_ID = "SPRING_BOOT_TEST";

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id")
    private String id;
    @Column( length = 50)
    private String name;
    private String socialSecurityNumber;
    private LocalDate dateOfBirth;

    @Column( insertable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(insertable = false, updatable = false)
    private OffsetDateTime dateUpdated;

    private String createdBy;
    private String updatedBy;
    private Boolean isDeleted;

    @PrePersist
    private void prePersist() {
        this.dateCreated = OffsetDateTime.now();
        this.createdBy = DEFAULT_USER_ID;
        this.isDeleted = false;
    }

    @PreUpdate
    private void preUpdate() {
        this.dateUpdated = OffsetDateTime.now();
        this.updatedBy = DEFAULT_USER_ID;
    }

}
