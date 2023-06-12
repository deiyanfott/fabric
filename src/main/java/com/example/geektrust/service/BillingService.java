package com.example.geektrust.service;

import com.example.geektrust.bean.BillingBean;
import com.example.geektrust.bean.InvoiceBean;
import com.example.geektrust.constant.Apartment;
import com.example.geektrust.constant.Command;
import com.example.geektrust.validator.FileValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BillingService {
    public void validateRecords(String filePath, BillingBean billingBean) {
        Path path = FileValidator.validateFilePath(filePath);
        List<String> records = getRecordEntries(path);
        readAndValidateRecords(records, billingBean);
    }

    public void computeAndPrintOutBill(BillingBean billingBean) {
        InvoiceService invoiceService = new InvoiceService();
        InvoiceBean invoiceBean = invoiceService.computeInvoice(billingBean);
        System.out.println(invoiceBean.getTotalWaterConsumption() + " " + invoiceBean.getTotalFees());
    }

    private List<String> getRecordEntries(Path path) {
        try (Stream<String> entries = Files.lines(path)) {
            return entries.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(path + " does not exist.");
        }
    }

    private void readAndValidateRecords(List<String> records, BillingBean billingBean) {
        FileValidator.validateNumberOfRecords(records);
        int numberOfBedrooms = FileValidator.validateFirstRecord(records.get(0).strip(), billingBean);
        int additionalGuests = FileValidator.validateAdditionalGuests(records, billingBean);
        FileValidator.validateCommand(Command.BILL, records.get(records.size() - 1).strip());
        setBillingAttributes(billingBean, numberOfBedrooms, additionalGuests);
    }

    private void setBillingAttributes(BillingBean billingBean, int numberOfBedrooms, int additionalGuests) {
        InvoiceService invoiceService = new InvoiceService();
        int initialGuests = determineInitialGuests(numberOfBedrooms);
        billingBean.setInitialWaterAllotment(invoiceService.computeWaterAllotment(initialGuests));
        billingBean.setAdditionalWaterAllotment(invoiceService.computeWaterAllotment(additionalGuests));
    }

    private int determineInitialGuests(int numberOfBedrooms) {
        return numberOfBedrooms == Apartment.TWO_BEDROOMS
                        ? Apartment.TWO_BEDROOM_GUESTS : Apartment.THREE_BEDROOM_GUESTS;
    }
}
