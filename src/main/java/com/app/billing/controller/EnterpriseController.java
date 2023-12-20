package com.app.billing.controller;

import com.app.billing.dto.EnterpriseDto;
import com.app.billing.dto.create.EnterpriseCreateDto;
import com.app.billing.service.EnterpriseService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/enterprise")
public class EnterpriseController {
    private final EnterpriseService enterpriseService;

    public EnterpriseController(EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping
    public ResponseEntity<List<EnterpriseDto>> getAllEnterprises() {
        List<EnterpriseDto> enterpriseList = enterpriseService.findAllEnterprises();
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

    @GetMapping("/search/ruc/{ruc}")
    public ResponseEntity<Object> getEnterpriseByRuc(@PathVariable String ruc) {
        Object response = enterpriseService.searchEnterpriseByRuc(ruc);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/report/{type}")
    public void getPdf(HttpServletResponse response, @PathVariable String type) throws JRException, IOException {
        if (type.equals("pdf")) {
            enterpriseService.exportPdf(response);
        } else if (type.equals("xls")) {
            enterpriseService.exportExcel(response);
        }
    }

}
