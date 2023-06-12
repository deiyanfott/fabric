package com.example.geektrust.bean;

import java.math.BigDecimal;

public class InvoiceBean {
    private final BigDecimal totalWaterConsumption;
    private final BigDecimal totalFees;

    public InvoiceBean(BigDecimal totalWaterConsumption, BigDecimal totalFees) {
        this.totalWaterConsumption = totalWaterConsumption;
        this.totalFees = totalFees;
    }

    public BigDecimal getTotalWaterConsumption() {
        return totalWaterConsumption;
    }

    public BigDecimal getTotalFees() {
        return totalFees;
    }
}
