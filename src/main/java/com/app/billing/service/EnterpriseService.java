package com.app.billing.service;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface EnterpriseService {
    List<EnterpriseDto> findAllEnterprises();

    EnterpriseDto findEnterpriseById(UUID id);

    EnterpriseDto createEnterprise(EnterpriseCreateDto enterpriseCreateDto);

    EnterpriseDto updateEnterprise(UUID id, EnterpriseCreateDto enterpriseCreateDto);

    EnterpriseDto deleteEnterprise(UUID id);

    void exportPdf(HttpServletResponse response) throws JRException, IOException;

    void exportExcel(HttpServletResponse response) throws JRException, IOException;

    Object searchEnterpriseByRuc(String ruc);
}
