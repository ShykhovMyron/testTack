package io.polybius.phonevalidator;

import java.util.List;

public enum PhoneNumbers {
    LITHUANIA("370", "LT", List.of("6"), List.of(8)),
    LATVIA("371", "LV", List.of("2"), List.of(8)),
    ESTONIA("372", "EE", List.of("5"), List.of(7, 8)),
    BELGIUM("32", "BE", List.of("456", "47", "48", "49"), List.of(9));

    private final String countryCode;

    private final String country;

    private final List<String> startNumbers;

    private final List<Integer> numberLengths;

    PhoneNumbers(
            String countryCode, String country, List<String> startNumbers, List<Integer> numberLengths
    ) {
        this.countryCode = countryCode;
        this.country = country;
        this.startNumbers = startNumbers;
        this.numberLengths = numberLengths;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountry() {
        return country;
    }

    public List<String> getStartNumbers() {
        return startNumbers;
    }
    public List<Integer> getNumberLengths() {
        return numberLengths;
    }
}
