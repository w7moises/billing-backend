package com.app.billing.service;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import com.app.billing.models.Enterprise;
import com.app.billing.repository.EnterpriseRepository;
import com.app.billing.service.impl.EnterpriseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EnterpriseServiceTest {
    private static final UUID enterpriseId = UUID.randomUUID();

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

    public static Enterprise enterprise = new Enterprise();

    public static EnterpriseDto enterpriseDto;

    public static EnterpriseCreateDto enterpriseCreateDto;

    @Mock
    private EnterpriseRepository enterpriseRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private EnterpriseServiceImpl enterpriseServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        enterprise = new Enterprise(
                enterpriseId,
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
                enterpriseId,
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
        enterpriseDto = new EnterpriseDto(
                enterpriseId,
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

    @DisplayName("Test for the creation of the enterprise")
    @Test
    public void createEnterpriseTest() {
        //given
        given(enterpriseRepository.save(enterprise)).willReturn(enterprise);

        //when
        when(enterpriseServiceImpl.createEnterprise(enterpriseCreateDto)).thenReturn(enterpriseDto);
        EnterpriseDto enterpriseDto = enterpriseServiceImpl.createEnterprise(enterpriseCreateDto);

        //then
        assertThat(enterpriseDto).isNotNull();
    }

    @DisplayName("Test to list all enterprises")
    @Test
    public void getAllEnterpriseTest() {
        //given
        given(enterpriseRepository.findAll()).willReturn(List.of(enterprise));

        //when
        final List<EnterpriseDto> enterpriseDtoList = enterpriseServiceImpl.findAllEnterprises();

        //then
        assertThat(enterpriseDtoList).isNotNull();
        assertThat(enterpriseDtoList.size()).isGreaterThan(0);
    }

    @DisplayName("Test to search enterprise by id")
    @Test
    public void findEnterpriseById() {
        //given
        given(enterpriseRepository.findById(enterpriseId)).willReturn(Optional.of(enterprise));

        //when
        when(enterpriseServiceImpl.findEnterpriseById(enterpriseId)).thenReturn(enterpriseDto);
        EnterpriseDto enterpriseDto = enterpriseServiceImpl.findEnterpriseById(enterpriseId);

        //then
        assertThat(enterpriseDto).isNotNull();
    }

    @DisplayName("Test for the update of the enterprise")
    @Test
    void updateEnterprise() {
        //given
        given(enterpriseRepository.findById(enterpriseId)).willReturn(Optional.of(enterprise));
        given(enterpriseRepository.save(enterprise)).willReturn(enterprise);
        enterpriseDto.setAddress("address");
        enterpriseDto.setCountry("country");

        //when
        when(enterpriseServiceImpl.updateEnterprise(enterpriseId, enterpriseCreateDto)).thenReturn(enterpriseDto);
        EnterpriseDto enterpriseDto = enterpriseServiceImpl.updateEnterprise(enterpriseId, enterpriseCreateDto);

        //then
        assertThat(enterpriseDto).isNotNull();
        assertThat(enterpriseDto.getAddress()).isEqualTo("address");
        assertThat(enterpriseDto.getCountry()).isEqualTo("country");
    }

    @DisplayName("Test for the delete of the enterprise")
    @Test
    void deleteEnterprise() {
        //given
        given(enterpriseRepository.findById(enterpriseId)).willReturn(Optional.of(enterprise));
        willDoNothing().given(enterpriseRepository).deleteById(enterpriseId);

        //when
        when(enterpriseServiceImpl.findEnterpriseById(enterpriseId)).thenReturn(enterpriseDto);
        enterpriseServiceImpl.deleteEnterprise(enterpriseId);

        //then
        verify(enterpriseRepository, times(1)).deleteById(enterpriseId);
    }
}
