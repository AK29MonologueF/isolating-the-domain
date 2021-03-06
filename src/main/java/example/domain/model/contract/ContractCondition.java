package example.domain.model.contract;

import example.domain.model.contract.shift.Shifts;
import example.domain.model.contract.wage.BaseHourlyWage;
import example.domain.model.contract.wage.WageCondition;

import java.time.LocalDate;

/**
 * 労働条件
 */
public class ContractCondition {
    ContractEffectiveDate effectiveDate;
    Shifts shifts;
    WageCondition wageCondition;

    @Deprecated
    public ContractCondition() {
    }

    public ContractCondition(ContractEffectiveDate effectiveDate, WageCondition wageCondition) {
        this.effectiveDate = effectiveDate;
        this.wageCondition = wageCondition;
    }

    public WageCondition wageCondition() {
        return wageCondition;
    }

    public BaseHourlyWage baseHourlyWage() {
        return wageCondition.baseHourlyWage();
    }

    public ContractEffectiveDate effectiveDate() {
        return effectiveDate;
    }

    public boolean availableAt(LocalDate date) {
        return effectiveDate.value().equals(date) || date.isAfter(effectiveDate.value());
    }
}
