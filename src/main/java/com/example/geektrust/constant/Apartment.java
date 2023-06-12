package com.example.geektrust.constant;

import java.util.ArrayList;
import java.util.List;

public class Apartment {
    public static final int TWO_BEDROOMS = 2;
    public static final int THREE_BEDROOMS = 3;
    public static final int TWO_BEDROOM_GUESTS = 3;
    public static final int THREE_BEDROOM_GUESTS = 5;

    public static final List<Integer> BEDROOM_TYPES = new ArrayList<>();
    static {
        BEDROOM_TYPES.addAll(List.of(TWO_BEDROOMS, THREE_BEDROOMS));
    }

    private Apartment () {

    }
}
