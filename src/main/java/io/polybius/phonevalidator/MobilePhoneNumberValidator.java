package io.polybius.phonevalidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MobilePhoneNumberValidator {
    public ValidationResultDto validate(List<String> phoneNumbers) {
        ValidationResultDto result = new ValidationResultDto();
        result.invalidPhones = new ArrayList<>();
        result.validPhonesByCountry = new HashMap<>();
        for (String phoneNumber : phoneNumbers) {
            boolean isValid;
            String country = null;
            phoneNumber = phoneNumber.replaceAll("[+() -]", "");
            if (phoneNumber.startsWith("370")) {
                country = "LT";
                isValid = phoneNumber.charAt(3) == '6' && phoneNumber.substring(3).length() == 8;
            } else if (phoneNumber.startsWith("371")) {
                country = "LV";
                isValid = phoneNumber.charAt(3) == '2' && phoneNumber.substring(3).length() == 8;
            } else if (phoneNumber.startsWith("372")) {
                country = "EE";
                isValid = phoneNumber.charAt(3) == '5' && phoneNumber.substring(3).length() == 7;
            } else {
                isValid = false;
            }

            if (isValid) {
                if (!result.validPhonesByCountry.containsKey(country)) {
                    result.validPhonesByCountry.put(country, new ArrayList<>());
                }
                result.validPhonesByCountry.get(country).add(phoneNumbers.get(0));
            } else {
                result.invalidPhones.add(phoneNumber);
            }
        }

        return result;
    }
}
