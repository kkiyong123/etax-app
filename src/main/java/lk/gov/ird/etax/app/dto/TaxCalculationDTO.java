package lk.gov.ird.etax.app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TaxCalculationDTO {
    private String tin;
    private String taxType;
    private BigDecimal assessedValue;
    private String taxPeriod;
    private boolean isOverdue;
}
