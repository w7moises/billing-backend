package com.app.billing.service;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EnterpriseService {
    Page<EnterpriseDto> findAllEnterprises(Pageable pageable);

    EnterpriseDto findEnterpriseById(UUID id);

    EnterpriseDto createEnterprise(EnterpriseCreateDto enterpriseCreateDto);

    EnterpriseDto updateEnterprise(UUID id, EnterpriseCreateDto enterpriseCreateDto);

    EnterpriseDto deleteEnterprise(UUID id);

    Object searchEnterpriseByRuc(String ruc);
}
