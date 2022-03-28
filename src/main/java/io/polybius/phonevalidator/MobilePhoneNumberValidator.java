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
            String validPhoneNumber = removeAllAllowedCharacters(phoneNumber);
            String country;
            if (!(country = getPhoneNumberCountry(validPhoneNumber)).isEmpty()) {
                result.validPhonesByCountry.putIfAbsent(country, new ArrayList<>());
                result.validPhonesByCountry.get(country).add(phoneNumber);
            } else {
                result.invalidPhones.add(phoneNumber);
            }
        }

        return result;
    }

    private String removeAllAllowedCharacters(String number) {
        number = number.replaceAll("^[+]", "").replaceAll("[- ]", "");

        int indexBraceBegin = number.indexOf("(");
        int indexBraceEnd = number.indexOf(")");
        while (indexBraceEnd > indexBraceBegin && indexBraceBegin != -1) {
            StringBuilder builder = new StringBuilder(number);
            number = builder.deleteCharAt(indexBraceBegin).deleteCharAt(indexBraceEnd-1).toString();
            indexBraceBegin = number.indexOf("(");
            indexBraceEnd = number.indexOf(")");
        }

        return number;
    }

    private String getPhoneNumberCountry(String phoneNumber) {
        for (PhoneNumbers phoneNumberInfo : PhoneNumbers.values()) {
            if (isPhoneNumberCountryValid(phoneNumberInfo, phoneNumber)) {
                return phoneNumberInfo.getCountry();
            }
        }
        return "";
    }

    private boolean isPhoneNumberCountryValid(PhoneNumbers phoneNumberInfo, String phoneNumber) {
        return phoneNumber.startsWith(phoneNumberInfo.getCountryCode()) &&
                isPhoneNumberLengthValid(phoneNumberInfo, phoneNumber.length()) &&
                isPhoneNumberContainStartNumbers(phoneNumberInfo, phoneNumber);
    }

    private boolean isPhoneNumberContainStartNumbers(PhoneNumbers phoneNumberInfo, String phoneNumber) {
        for (String startNumber : phoneNumberInfo.getStartNumbers()) {
            if (phoneNumber.startsWith(startNumber, phoneNumberInfo.getCountryCode().length())) {
                return true;
            }
        }
        return false;
    }

    private boolean isPhoneNumberLengthValid(PhoneNumbers phoneNumberInfo, int phoneNumberLength) {
        return phoneNumberInfo.getNumberLengths()
                .contains(phoneNumberLength - phoneNumberInfo.getCountryCode().length());
    }

}
