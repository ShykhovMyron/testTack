package io.polybius.phonevalidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobilePhoneNumberValidator {
    private final static int phoneNumberLength = 11;

    public ValidationResultDto validate(List<String> phoneNumbers) {
        ValidationResultDto result = new ValidationResultDto();
        result.invalidPhones = new ArrayList<>();
        result.validPhonesByCountry = new HashMap<>();

        for (String phoneNumber : phoneNumbers) {
            String validPhoneNumber = getValidPhoneNumber(phoneNumber);
            String country;
            if (validPhoneNumber.length() == phoneNumberLength
                    && !(country = getNumberCountry(validPhoneNumber)).isEmpty()) {
                result.validPhonesByCountry.putIfAbsent(country, new ArrayList<>());
                result.validPhonesByCountry.get(country).add(phoneNumber);
            } else {
                result.invalidPhones.add(phoneNumber);
            }
        }

        return result;
    }

    private String getNumberCountry(String validNumber) {
        String country = "";
        if (checkNumber(validNumber, "370", "6")) {
            country = "LT";
        }
        if (checkNumber(validNumber, "371", "2")) {
            country = "LV";
        }
        if (checkNumber(validNumber, "372", "5")) {
            country = "EE";
        }
        if (checkNumber(validNumber, "32", "456", "47", "48", "49")) {
            country = "BE";
        }
        return country;
    }

    private boolean checkNumber(String number, String callingCode, String... initialDigits) {
        if (number.startsWith(callingCode)) {
            int beginPhoneNumber = callingCode.length();

            for (String initialDigit : initialDigits) {
                if (number.startsWith(initialDigit, beginPhoneNumber)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getValidPhoneNumber(String number) {
        number = number.replaceAll("^[+]", "").replaceAll("[- ]", "");
        if (number.contains("(") && number.contains(")")) {
            number = number.replace(")", "").replace("(", "");
        }
        return number;
    }
}
