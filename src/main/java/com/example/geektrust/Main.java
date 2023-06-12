package com.example.geektrust;

import com.example.geektrust.bean.BillingBean;
import com.example.geektrust.service.BillingService;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expects 1 argument but actual is " + args.length);
        }

        var billingService = new BillingService();
        var billingBean = new BillingBean();
        billingService.validateRecords(args[0], billingBean);
        billingService.computeAndPrintOutBill(billingBean);
    }
}
