package com.app.billing.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnterpriseDto {
    private UUID id;
    private String ruc;
    private String businessName;
    private String address;
    private String email;
    private String phoneNumber;
    private String country;
    private String geographicLocation;
    private String principalCurrency;
    private String secondaryCurrency;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;
    private String exercise;
}
