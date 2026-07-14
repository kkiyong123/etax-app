package lk.gov.ird.etax.app.service;

import lk.gov.ird.etax.app.dto.TaxCalculationDTO;
import lk.gov.ird.etax.app.entity.TaxCalculation;
import lk.gov.ird.etax.app.repository.TaxCalculationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TaxCalculationService {

    @Autowired
    private TaxCalculationRepository taxCalculationRepository;

    public TaxCalculation calculate(TaxCalculationDTO dto) {
        TaxCalculation calc = new TaxCalculation();
        calc.setTin(dto.getTin());
        calc.setTaxType(dto.getTaxType());
        calc.setAssessedValue(dto.getAssessedValue());

        BigDecimal rate = getRate(dto.getTaxType());
        BigDecimal taxAmount = computeTax(dto, rate);
        BigDecimal surcharge = dto.isOverdue()
            ? taxAmount.multiply(new BigDecimal("0.25"))
            : BigDecimal.ZERO;
        BigDecimal interest = dto.isOverdue()
            ? taxAmount.multiply(new BigDecimal("0.02"))
            : BigDecimal.ZERO;
        BigDecimal total = taxAmount.add(surcharge).add(interest);

        calc.setTaxRate(rate);
        calc.setTaxAmount(taxAmount.setScale(2, RoundingMode.HALF_UP));
        calc.setSurcharge(surcharge.setScale(2, RoundingMode.HALF_UP));
        calc.setInterest(interest.setScale(2, RoundingMode.HALF_UP));
        calc.setTotalAmount(total.setScale(2, RoundingMode.HALF_UP));

        return taxCalculationRepository.save(calc);
    }

    private BigDecimal getRate(String taxType) {
        switch (taxType) {
            case "PROPERTY_TAX":  return new BigDecimal("0.01");
            case "TRANSFER_TAX":  return new BigDecimal("0.005");
            case "STAMP_DUTY":    return new BigDecimal("0.015");
            case "REGISTRATION":  return new BigDecimal("0.005");
            default:              return new BigDecimal("0.01");
        }
    }

    private BigDecimal computeTax(TaxCalculationDTO dto, BigDecimal rate) {
        if ("LICENSE_TAX".equals(dto.getTaxType())) {
            return new BigDecimal("1000");
        }
        return dto.getAssessedValue() != null
            ? dto.getAssessedValue().multiply(rate)
            : BigDecimal.ZERO;
    }
}
