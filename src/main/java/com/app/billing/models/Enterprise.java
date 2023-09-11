package com.app.billing.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "enterprises")
@Audited
@AuditTable(value = "aud_enterprises")
public class Enterprise extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false, unique = true)
    private String ruc;
    @Column(name = "business_name", nullable = false, unique = true)
    private String businessName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;
    @Column(nullable = false)
    private String country;
    @Column(name = "geographic_location", nullable = false)
    private String geographicLocation;
    @Column(name = "principal_currency", nullable = false)
    private String principalCurrency;
    @Column(name = "secondary_currency", nullable = false)
    private String secondaryCurrency;
    @Column(name = "start_date", nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;
    @Column(nullable = false)
    private String exercise;
}
