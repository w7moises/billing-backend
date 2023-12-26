package com.app.billing.controller;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import com.app.billing.service.EnterpriseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class EnterpriseControllerTest {

    private static final UUID id = UUID.fromString("cdfb9d3a-b4ed-40ff-99ce-8b80888fc7ac");

    private static final String ruc = "20600000001";

    private static final String businessName = "hipeCorp";

    private static final String address = "puquina 123";

    private static final String email = "email@gmail.com";

    private static final String phoneNumber = "992748665";

    private static final String country = "Peru";

    private static final String geographicLocation = "San Miguel - Lima";

    private static final String principalCurrency = "Soles";

    private static final String secondaryCurrency = "Dolares";

    private static final Date startDate = new Date();

    private static final String exercise = "2019";

    @MockBean
    private EnterpriseService enterpriseService;

    private EnterpriseDto enterpriseDto;

    private EnterpriseCreateDto enterpriseCreateDto;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() {
        enterpriseDto = new EnterpriseDto(
                id,
                ruc,
                businessName,
                address,
                email,
                phoneNumber,
                country,
                geographicLocation,
                principalCurrency,
                secondaryCurrency,
                startDate,
                exercise
        );
        enterpriseCreateDto = new EnterpriseCreateDto(
                id,
                ruc,
                businessName,
                address,
                email,
                phoneNumber,
                country,
                geographicLocation,
                principalCurrency,
                secondaryCurrency,
                startDate,
                exercise
        );
    }

    @DisplayName("Test for the creation of Enterprise")
    @Test
    void createEnterprise() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String expectedStartDate = dateFormat.format(startDate);

        given(enterpriseService.createEnterprise(ArgumentMatchers.any(EnterpriseCreateDto.class))).willReturn(enterpriseDto);

        this.mockMvc.perform(post("/enterprises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enterpriseCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.ruc", is(ruc)))
                .andExpect(jsonPath("$.businessName", is(businessName)))
                .andExpect(jsonPath("$.address", is(address)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.phoneNumber", is(phoneNumber)))
                .andExpect(jsonPath("$.country", is(country)))
                .andExpect(jsonPath("$.geographicLocation", is(geographicLocation)))
                .andExpect(jsonPath("$.principalCurrency", is(principalCurrency)))
                .andExpect(jsonPath("$.secondaryCurrency", is(secondaryCurrency)))
                .andExpect(jsonPath("$.startDate", is(expectedStartDate)))
                .andExpect(jsonPath("$.exercise", is(exercise)));
    }

    @DisplayName("Test for fetch all Enterprises")
    @Test
    void shouldFetchAllEnterprises() throws Exception {
        given(enterpriseService.findAllEnterprises()).willReturn(List.of(enterpriseDto));

        this.mockMvc.perform(get("/enterprises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)));
    }

    @DisplayName("Test for fetch a Enterprise by Id")
    @Test
    void shouldFetchOneEnterpriseById() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String expectedStartDate = dateFormat.format(startDate);

        given(enterpriseService.findEnterpriseById(id)).willReturn(enterpriseDto);

        this.mockMvc.perform(get("/enterprises/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.ruc", is(ruc)))
                .andExpect(jsonPath("$.businessName", is(businessName)))
                .andExpect(jsonPath("$.address", is(address)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.phoneNumber", is(phoneNumber)))
                .andExpect(jsonPath("$.country", is(country)))
                .andExpect(jsonPath("$.geographicLocation", is(geographicLocation)))
                .andExpect(jsonPath("$.principalCurrency", is(principalCurrency)))
                .andExpect(jsonPath("$.secondaryCurrency", is(secondaryCurrency)))
                .andExpect(jsonPath("$.startDate", is(expectedStartDate)))
                .andExpect(jsonPath("$.exercise", is(exercise)));
    }

    @DisplayName("Test for the update of the Enterprise")
    @Test
    void shouldUpdateEnterprise() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String expectedStartDate = dateFormat.format(startDate);

        given(enterpriseService.findEnterpriseById(id)).willReturn(enterpriseDto);
        given(enterpriseService.updateEnterprise(ArgumentMatchers.eq(id), ArgumentMatchers.any(EnterpriseCreateDto.class)))
                .willReturn(enterpriseDto);

        this.mockMvc.perform(put("/enterprises/{id}", enterpriseDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enterpriseCreateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id.toString())))
                .andExpect(jsonPath("$.ruc", is(ruc)))
                .andExpect(jsonPath("$.businessName", is(businessName)))
                .andExpect(jsonPath("$.address", is(address)))
                .andExpect(jsonPath("$.email", is(email)))
                .andExpect(jsonPath("$.phoneNumber", is(phoneNumber)))
                .andExpect(jsonPath("$.country", is(country)))
                .andExpect(jsonPath("$.geographicLocation", is(geographicLocation)))
                .andExpect(jsonPath("$.principalCurrency", is(principalCurrency)))
                .andExpect(jsonPath("$.secondaryCurrency", is(secondaryCurrency)))
                .andExpect(jsonPath("$.startDate", is(expectedStartDate)))
                .andExpect(jsonPath("$.exercise", is(exercise)));
    }

    @DisplayName("Test for the delete of the Enterprise")
    @Test
    void shouldDeleteEnterprise() throws Exception {
        given(enterpriseService.findEnterpriseById(id)).willReturn(enterpriseDto);

        this.mockMvc.perform(delete("/enterprises/{id}", id))
                .andExpect(status().isOk());
    }
}
