package lk.gov.ird.etax.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tax_calculations")
@Data
public class TaxCalculation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tin;

    @Column(name = "tax_type")
    private String taxType;

    @Column(name = "assessed_value")
    private BigDecimal assessedValue;

    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Column(name = "tax_amount")
    private BigDecimal taxAmount;

    @Column(name = "surcharge")
    private BigDecimal surcharge;

    @Column(name = "interest")
    private BigDecimal interest;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;

    @PrePersist
    protected void onCreate() {
        calculatedAt = LocalDateTime.now();
    }
}
