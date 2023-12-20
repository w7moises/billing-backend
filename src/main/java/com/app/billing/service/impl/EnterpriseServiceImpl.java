package com.app.billing.service.impl;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import com.app.billing.exception.DataProcessingException;
import com.app.billing.exception.ResourceNotFoundException;
import com.app.billing.models.Enterprise;
import com.app.billing.repository.EnterpriseRepository;
import com.app.billing.service.EnterpriseService;
import com.app.billing.service.MessageService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;

    private final ResourceLoader resourceLoader;

    @Value("${api.url}")
    private String url;

    @Value("${api.token}")
    private String token;

    private final RestTemplate restTemplate;

    private final ModelMapper modelMapper;

    private final MessageService messageService;

    private static final Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository, ResourceLoader resourceLoader, RestTemplate restTemplate, ModelMapper modelMapper, MessageService messageService) {
        this.enterpriseRepository = enterpriseRepository;
        this.resourceLoader = resourceLoader;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
        this.messageService = messageService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnterpriseDto> findAllEnterprises() {
        return enterpriseRepository.findAll().stream()
                .map(enterprise -> modelMapper.map(enterprise, EnterpriseDto.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public EnterpriseDto findEnterpriseById(UUID id) {
        Enterprise optionalEnterprise = enterpriseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(messageService.getMessage("enterprise"), "id", id, messageService));
        return modelMapper.map(optionalEnterprise, EnterpriseDto.class);
    }

    @Override
    public EnterpriseDto createEnterprise(EnterpriseCreateDto enterpriseCreateDto) {
        try {
            Enterprise enterprise = enterpriseRepository.save(modelMapper.map(enterpriseCreateDto, Enterprise.class));
            return modelMapper.map(enterprise, EnterpriseDto.class);
        } catch (Exception e) {
            logger.error("Error while creating enterprise", e);
            throw new DataProcessingException(messageService.getMessage("enterprise.create"));
        }
    }

    @Override
    public EnterpriseDto updateEnterprise(UUID id, EnterpriseCreateDto enterpriseCreateDto) {
        try {
            Enterprise optionalEnterprise = enterpriseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(messageService.getMessage("enterprise"), "id", id, messageService));

            BeanUtils.copyProperties(enterpriseCreateDto, optionalEnterprise, "id", "createdBy", "createdDate");
            return modelMapper.map(enterpriseRepository.save(optionalEnterprise), EnterpriseDto.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while updating enterprise with ID: " + id, e);
            throw new DataProcessingException(messageService.getMessage("enterprise.update") + id);
        }
    }

    @Override
    public EnterpriseDto deleteEnterprise(UUID id) {
        try {
            Enterprise optionalEnterprise = enterpriseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(messageService.getMessage("enterprise"), "id", id, messageService));

            enterpriseRepository.deleteById(optionalEnterprise.getId());
            return modelMapper.map(optionalEnterprise, EnterpriseDto.class);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting enterprise with ID: " + id, e);
            throw new DataProcessingException(messageService.getMessage("enterprise.delete") + id);
        }
    }

    @Override
    public void exportPdf(HttpServletResponse response) throws JRException, IOException {
        List<Enterprise> enterprises = enterpriseRepository.findAll();
        Resource jrxmlResource = resourceLoader.getResource("classpath:report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlResource.getInputStream());
        // Parámetros para el informe (si es necesario)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("enterprisesData", new JRBeanCollectionDataSource(enterprises));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=report.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
    }

    @Override
    public void exportExcel(HttpServletResponse response) throws JRException, IOException {
        List<Enterprise> enterprises = enterpriseRepository.findAll();
        Resource jrxmlResource = resourceLoader.getResource("classpath:report.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlResource.getInputStream());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("enterprisesData", new JRBeanCollectionDataSource(enterprises));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

        response.setContentType("application/vnd.ms-excel");
        response.setHeader("Content-Disposition", "attachment; filename=report.xls");

        JRXlsExporter exporter = new JRXlsExporter();
        SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
        configuration.setDetectCellType(true);
        configuration.setCollapseRowSpan(false);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
        exporter.exportReport();
    }

    @Override
    public Object searchEnterpriseByRuc(String ruc) {
        try {
            return restTemplate.getForObject(url + ruc + token, Object.class);
        } catch (Exception e) {
            logger.error("Error while sarching ruc in api : https://apiperu.dev/api/ruc/");
            throw new DataProcessingException("Error while sarching ruc in api : https://apiperu.dev/api/ruc/");
        }
    }
}
