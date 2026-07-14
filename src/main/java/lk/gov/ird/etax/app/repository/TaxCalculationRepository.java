package lk.gov.ird.etax.app.repository;

import lk.gov.ird.etax.app.entity.TaxCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaxCalculationRepository extends JpaRepository<TaxCalculation, Long> {
    List<TaxCalculation> findByTin(String tin);
    List<TaxCalculation> findByTaxType(String taxType);
}
