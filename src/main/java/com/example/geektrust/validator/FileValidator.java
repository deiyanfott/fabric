package com.example.geektrust.validator;

import com.example.geektrust.bean.BillingBean;
import com.example.geektrust.constant.Apartment;
import com.example.geektrust.constant.Command;
import com.example.geektrust.constant.Delimiter;
import com.example.geektrust.util.StringUtils;

import java.math.BigDecimal;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.List;

public class FileValidator {
    private static int additionalGuests;
    private FileValidator() {

    }

    public static Path validateFilePath(String filePath) {
        try {
            return Path.of(filePath);
        } catch (InvalidPathException e) {
            throw new IllegalArgumentException(filePath + " does not exist.");
        }
    }

    public static void validateNumberOfRecords (List<String> records) {
        if (records.size() < 2) {
            throw new IllegalArgumentException("Input entries are incomplete.");
        }
    }

    public static int validateFirstRecord(String firstRecord, BillingBean billingBean) {
        String[] allotWaterParameters = StringUtils.splitString(firstRecord, Delimiter.SPACE);
        validateNumberOfParameters(3, allotWaterParameters.length);
        validateCommand(Command.ALLOT_WATER, allotWaterParameters[0]);
        int numberOfBedrooms = validateApartmentType(allotWaterParameters[1], billingBean);
        validateAndSetRatio(allotWaterParameters[2], billingBean);

        return numberOfBedrooms;
    }

    public static int validateAdditionalGuests(List<String> records, BillingBean billingBean) {
        additionalGuests = 0;

        if (records.size() > 2) {
            records.subList(1, records.size() -1).forEach(guest -> {
                String[] guestParameters = StringUtils.splitString(guest.strip(), Delimiter.SPACE);
                validateNumberOfParameters(2, guestParameters.length);
                validateCommand(Command.ADD_GUESTS, guestParameters[0]);
                int guests = StringUtils.convertStringToInt(guestParameters[1]);
                additionalGuests += guests;
            });
        }

        return additionalGuests;
    }

    public static void validateCommand(String expectedCommand, String actualCommand) {
        if (!expectedCommand.equalsIgnoreCase(actualCommand)) {
            throw new IllegalArgumentException(expectedCommand + " is expected command but actual is " + actualCommand);
        }
    }

    private static void validateNumberOfParameters(int expectedParams, int actualParams) {
        if (expectedParams != actualParams) {
            throw new IllegalArgumentException("Expected " + expectedParams + " parameters but actual is " + actualParams);
        }
    }

    private static int validateApartmentType(String apartmentType, BillingBean billingBean) {
        int numberOfBedrooms = StringUtils.convertStringToInt(apartmentType);
        validateAndSetApartmentTypeValue(numberOfBedrooms, billingBean);
        return numberOfBedrooms;
    }

    private static void validateAndSetApartmentTypeValue(int numberOfBedrooms, BillingBean billingBean) {
        if (!Apartment.BEDROOM_TYPES.contains(numberOfBedrooms)) {
            throw new IllegalArgumentException(numberOfBedrooms + " is not a valid value");
        }
    }

    private static void validateAndSetRatio(String ratio, BillingBean billingBean) {
        String[] ratioParameters = StringUtils.splitString(ratio, Delimiter.COLON);
        validateNumberOfParameters(2, ratioParameters.length);
        billingBean.setCorpWaterRatio(BigDecimal.valueOf(StringUtils.convertStringToInt(ratioParameters[0])));
        billingBean.setBorewellWaterRatio(BigDecimal.valueOf(StringUtils.convertStringToInt(ratioParameters[1])));
    }
}
