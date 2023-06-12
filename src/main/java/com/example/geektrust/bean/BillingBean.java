package com.example.geektrust.bean;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

public class BillingBean {
    private BigDecimal corpWaterRatio = BigDecimal.ONE;
    private BigDecimal borewellWaterRatio = BigDecimal.ONE;
    private BigDecimal initialWaterAllotment = BigDecimal.ZERO;
    private BigDecimal additionalWaterAllotment = BigDecimal.ZERO;
    private BigDecimal corpWaterRate = BigDecimal.ONE;
    private BigDecimal borewellWaterRate = BigDecimal.valueOf(1.5);
    private Map<BigDecimal, BigDecimal> tankWaterRate = new TreeMap<>();

    public BillingBean() {
        tankWaterRate.put(BigDecimal.valueOf(2), BigDecimal.valueOf(500));
        tankWaterRate.put(BigDecimal.valueOf(3), BigDecimal.valueOf(1500));
        tankWaterRate.put(BigDecimal.valueOf(5), BigDecimal.valueOf(3000));
    }

    public BigDecimal getCorpWaterRatio() {
        return corpWaterRatio;
    }

    public void setCorpWaterRatio(BigDecimal corpWaterRatio) {
        this.corpWaterRatio = corpWaterRatio;
    }

    public BigDecimal getBorewellWaterRatio() {
        return borewellWaterRatio;
    }

    public void setBorewellWaterRatio(BigDecimal borewellWaterRatio) {
        this.borewellWaterRatio = borewellWaterRatio;
    }

    public BigDecimal getInitialWaterAllotment() {
        return initialWaterAllotment;
    }

    public void setInitialWaterAllotment(BigDecimal initialWaterAllotment) {
        this.initialWaterAllotment = initialWaterAllotment;
    }

    public BigDecimal getAdditionalWaterAllotment() {
        return additionalWaterAllotment;
    }

    public void setAdditionalWaterAllotment(BigDecimal additionalWaterAllotment) {
        this.additionalWaterAllotment = additionalWaterAllotment;
    }

    public BigDecimal getCorpWaterRate() {
        return corpWaterRate;
    }

    public BigDecimal getBorewellWaterRate() {
        return borewellWaterRate;
    }

    public Map<BigDecimal, BigDecimal> getTankWaterRate() {
        return tankWaterRate;
    }
}
