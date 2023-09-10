package com.app.billing.controller;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import com.app.billing.service.EnterpriseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("enterprise")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;

    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping("/page")
    public ResponseEntity<Page<EnterpriseDto>> getAllEnterprises(Pageable pageable) {
        Page<EnterpriseDto> enterpriseList = enterpriseService.findAllEnterprises(pageable);
        return new ResponseEntity<>(enterpriseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnterpriseDto> getEnterpriseById(@PathVariable UUID id) {
        EnterpriseDto enterprise = enterpriseService.findEnterpriseById(id);
        return new ResponseEntity<>(enterprise, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<EnterpriseDto> createEnterprise(@RequestBody @Valid EnterpriseCreateDto enterpriseDto) {
        EnterpriseDto enterprise = enterpriseService.createEnterprise(enterpriseDto);
        return new ResponseEntity<>(enterprise, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnterpriseDto> updateEnterprise(@PathVariable UUID id, @RequestBody @Valid EnterpriseCreateDto EnterpriseDto) {
        EnterpriseDto enterprise = enterpriseService.updateEnterprise(id, EnterpriseDto);
        return new ResponseEntity<>(enterprise, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnterpriseDto> deleteEnterprise(@PathVariable UUID id) {
        EnterpriseDto enterprise = enterpriseService.deleteEnterprise(id);
        return new ResponseEntity<>(enterprise, HttpStatus.OK);
    }

}
