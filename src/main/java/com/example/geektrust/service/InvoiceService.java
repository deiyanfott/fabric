package com.example.geektrust.service;

import com.example.geektrust.bean.BillingBean;
import com.example.geektrust.bean.InvoiceBean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Map;

public class InvoiceService {
    private static final int DAYS_IN_A_MONTH = 30;
    private static final int WATER_ALLOTMENT_PER_GUEST = 10;

    public InvoiceBean computeInvoice(BillingBean billingBean) {
        BigDecimal totalShare = computeTotals(billingBean.getCorpWaterRatio(), billingBean.getBorewellWaterRatio());
        BigDecimal corpWaterFee = computeInitialCosts(billingBean.getInitialWaterAllotment(),
                billingBean.getCorpWaterRatio(), billingBean.getCorpWaterRate(), totalShare);
        BigDecimal borewellWaterFee = computeInitialCosts(billingBean.getInitialWaterAllotment(),
                billingBean.getBorewellWaterRatio(), billingBean.getBorewellWaterRate(), totalShare);
        BigDecimal tankWaterFee = computeAdditionalCosts(billingBean.getAdditionalWaterAllotment(),
                billingBean.getTankWaterRate());
        BigDecimal totalFees = computeTotals(corpWaterFee, borewellWaterFee, tankWaterFee);
        BigDecimal totalWaterConsumption = computeTotals(billingBean.getInitialWaterAllotment(),
                billingBean.getAdditionalWaterAllotment());

        return new InvoiceBean(totalWaterConsumption, totalFees);
    }

    public BigDecimal computeWaterAllotment(int guests) {
        return BigDecimal.valueOf(guests * DAYS_IN_A_MONTH * WATER_ALLOTMENT_PER_GUEST);
    }

    private BigDecimal computeTotals(BigDecimal... values) {
        return Arrays.stream(values).reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(0, RoundingMode.HALF_EVEN);
    }

    private BigDecimal computeInitialCosts(BigDecimal initialWaterAllotment,
                                                  BigDecimal waterRatio, BigDecimal waterRate, BigDecimal totalShare) {
        return initialWaterAllotment.multiply(waterRatio)
                .multiply(waterRate).divide(totalShare, RoundingMode.HALF_EVEN);
    }

    private BigDecimal computeAdditionalCosts(BigDecimal additionalWaterAllotment,
                                                     Map<BigDecimal, BigDecimal> tankWaterRate) {
        var tankWaterFee = BigDecimal.ZERO;
        var addedWaterAllotment = additionalWaterAllotment;

        for (Map.Entry<BigDecimal, BigDecimal> entrySet : tankWaterRate.entrySet()) {
            var rate = entrySet.getKey();
            var maxAllotment = entrySet.getValue();

            if (addedWaterAllotment.compareTo(maxAllotment) > 0) {
                addedWaterAllotment = addedWaterAllotment.subtract(maxAllotment);
                tankWaterFee = tankWaterFee.add(maxAllotment.multiply(rate));
            } else {
                tankWaterFee = tankWaterFee.add(addedWaterAllotment.multiply(rate));
                break;
            }
        }

        return tankWaterFee;
    }
}
