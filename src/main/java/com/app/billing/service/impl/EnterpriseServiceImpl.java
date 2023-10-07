package com.app.billing.service.impl;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import com.app.billing.exception.DataProcessingException;
import com.app.billing.exception.ResourceNotFoundException;
import com.app.billing.models.Enterprise;
import com.app.billing.repository.EnterpriseRepository;
import com.app.billing.service.EnterpriseService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class EnterpriseServiceImpl implements EnterpriseService {
    private final EnterpriseRepository enterpriseRepository;

    @Value("${api.url}")
    private String url;

    @Value("${api.token}")
    private String token;

    private final RestTemplate restTemplate;

    private final ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

    public EnterpriseServiceImpl(EnterpriseRepository enterpriseRepository, RestTemplate restTemplate, ModelMapper modelMapper) {
        this.enterpriseRepository = enterpriseRepository;
        this.restTemplate = restTemplate;
        this.modelMapper = modelMapper;
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
                .orElseThrow(() -> new ResourceNotFoundException("Enterprise", "id", id));
        return modelMapper.map(optionalEnterprise, EnterpriseDto.class);
    }

    @Override
    public EnterpriseDto createEnterprise(EnterpriseCreateDto enterpriseCreateDto) {
        try {
            Enterprise enterprise = enterpriseRepository.save(modelMapper.map(enterpriseCreateDto, Enterprise.class));
            return modelMapper.map(enterprise, EnterpriseDto.class);
        } catch (Exception e) {
            // Log the error and throw a custom exception
            logger.error("Error while creating enterprise", e);
            throw new DataProcessingException("Error creating the enterprise.");
        }
    }

    @Override
    public EnterpriseDto updateEnterprise(UUID id, EnterpriseCreateDto enterpriseCreateDto) {
        try {
            Enterprise optionalEnterprise = enterpriseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Enterprise", "id", id));

            BeanUtils.copyProperties(enterpriseCreateDto, optionalEnterprise, "id", "createdBy", "createdDate");
            return modelMapper.map(enterpriseRepository.save(optionalEnterprise), EnterpriseDto.class);
        } catch (Exception e) {
            // Log the error and throw a custom exception
            logger.error("Error while updating enterprise with ID: " + id, e);
            throw new DataProcessingException("Error updating the enterprise with ID: " + id);
        }
    }

    @Override
    public EnterpriseDto deleteEnterprise(UUID id) {
        try {
            Enterprise optionalEnterprise = enterpriseRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Enterprise", "id", id));

            enterpriseRepository.deleteById(optionalEnterprise.getId());
            return modelMapper.map(optionalEnterprise, EnterpriseDto.class);
        } catch (Exception e) {
            // Log the error and throw a custom exception
            logger.error("Error while deleting enterprise with ID: " + id, e);
            throw new DataProcessingException("Error deleting the enterprise with ID: " + id);
        }
    }

    @Override
    public Object searchEnterpriseByRuc(String ruc) {
        try {
            return restTemplate.getForObject(url + ruc + token, Object.class);
        } catch (Exception e) {
            // Log the error and throw a custom exception
            logger.error("Error while sarching ruc in api : https://apiperu.dev/api/ruc/");
            throw new DataProcessingException("Error while sarching ruc in api : https://apiperu.dev/api/ruc/");
        }
    }
}
