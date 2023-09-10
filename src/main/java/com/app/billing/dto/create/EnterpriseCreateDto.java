package com.app.billing.dto.create;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
public class EnterpriseCreateDto {
    private UUID id;
    @NotBlank(message = "RUC is required")
    @Pattern(regexp = "^\\d{11}$", message = "RUC must be 11 digits")
    private String ruc;
    @NotBlank(message = "Business name is required")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Business name must be alphabetic")
    private String businessName;
    @NotBlank(message = "Address is required")
    private String address;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{9}$", message = "Phone number must be 9 digits")
    private String phoneNumber;
    @NotBlank(message = "Country is required")
    private String country;
    @NotBlank(message = "Geographic location is required")
    private String geographicLocation;
    @NotBlank(message = "Principal currency is required")
    private String principalCurrency;
    @NotBlank(message = "Secondary currency is required")
    private String secondaryCurrency;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date startDate;
    @NotBlank(message = "Exercise is required")
    private String exercise;
}
