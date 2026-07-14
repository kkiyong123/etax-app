package lk.gov.ird.etax.app.controller;

import lk.gov.ird.etax.app.dto.TaxCalculationDTO;
import lk.gov.ird.etax.app.entity.TaxCalculation;
import lk.gov.ird.etax.app.service.TaxCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/calc")
@CrossOrigin(origins = "*")
public class TaxCalculationController {

    @Autowired
    private TaxCalculationService taxCalculationService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "etax-app",
            "version", "1.0.0"
        ));
    }

    @PostMapping("/calculate")
    public ResponseEntity<TaxCalculation> calculate(@RequestBody TaxCalculationDTO dto) {
        return ResponseEntity.ok(taxCalculationService.calculate(dto));
    }

    @GetMapping("/rates")
    public ResponseEntity<Map<String, Object>> getRates() {
        return ResponseEntity.ok(Map.of(
            "PROPERTY_TAX",  Map.of("rate", "1%",    "description", "Basic Real Property Tax"),
            "TRANSFER_TAX",  Map.of("rate", "0.5%",  "description", "Real Estate Transfer Tax"),
            "STAMP_DUTY",    Map.of("rate", "1.5%",  "description", "Documentary Stamp Tax per PHP 200"),
            "LICENSE_TAX",   Map.of("rate", "Fixed", "description", "Business License Tax"),
            "REGISTRATION",  Map.of("rate", "0.5%",  "description", "Registration and License Tax")
        ));
    }
}
