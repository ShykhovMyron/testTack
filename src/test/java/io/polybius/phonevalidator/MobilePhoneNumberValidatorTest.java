package io.polybius.phonevalidator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MobilePhoneNumberValidatorTest {

    private final MobilePhoneNumberValidator validator = new MobilePhoneNumberValidator();

    @ParameterizedTest
    @CsvSource({
            "LT,+37061234567",
            "LT,+370-612-34-567",
            "LT,+370 612 34 567",
            "LT,+370(6)1234567",
            "LT,37061234567",
            "LV,+37121234567",
            "LV,+371-212-34-567",
            "LV,+371 212 34 567",
            "LV,+371(2)1234567",
            "LV,37121234567",
            "EE,+37251234567",
            "EE,+372-512-34-567",
            "EE,+372 512 34 56",
            "EE,+372(5)1234567",
            "EE,3725123456",
            "BE,+32456234567",
            "BE,+324-712-34-567",
            "BE,+324 812 34 567",
            "BE,+324(9)1234567",
            "BE,32456234567",
    })
    public void validPhoneNumbersOneParameterTest(String country, String phoneNumber) {
        validTest(country, phoneNumber);
    }

    @ParameterizedTest
    @CsvSource({
            "LT,+37061234567,+370-612-34-567",
            "LT,+370 612 34 567,+370(6)1234567",
            "LV,+371-212-34-567,+371 212 34 567",
            "LV,37121234567,+37121234567",
            "EE,+37251234567,+372-512-34-567",
            "EE,+372 512 34 56,3725123456",
            "BE,+324 812 34 567,+32456234567",
            "BE,+324(9)1234(567),+324-712-34-567",
    })
    public void validPhoneNumbersTwoParametersTest(
            String country, String phoneNumber1, String phoneNumber2) {
        validTest(country, phoneNumber1, phoneNumber2);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "~37061234567",
            "*370-612-34-567",
            "+370 612 34 d567",
            "+370(6)129934567",
            "+371-2)1(2-34-567",
            "+371 212 (34 567",
            "37121+234567",
            "++37121234567",
            "+3'7121234567"
    })
    public void invalidPhoneNumberTest(String invalidPhoneNumber) {
        invalidTest(invalidPhoneNumber);
    }

    @Test
    public void validAndInvalidPhoneNumbersTest() {
        //Arrange
        String country = "BE";
        String validPhoneNumber1 = "+324 812 34 567";
        String validPhoneNumber2 = "+32456234567";
        String validPhoneNumber3 = "+324(9)1234(567)";
        String invalidPhoneNumberNoClosableBrake = "+324(91234(567)";
        String invalidPhoneNumberToMuchNumbers = "+324(9)1234(5967)";
        List<String> expectedValid = List.of(validPhoneNumber1, validPhoneNumber2, validPhoneNumber3);
        List<String> expectedInvalid = List
                .of(invalidPhoneNumberNoClosableBrake, invalidPhoneNumberToMuchNumbers);
        List<String> validAndInvalidList = new ArrayList<>(expectedValid) {{
            addAll(expectedInvalid);
        }};
        //Act
        ValidationResultDto actual = validator.validate(validAndInvalidList);
        //Assert
        assertEquals(expectedValid, actual.validPhonesByCountry.get(country));
        assertEquals(expectedInvalid, actual.invalidPhones);
    }

    private void invalidTest(String... invalidPhoneNumbers) {
        //Arrange
        List<String> expected = List.of(invalidPhoneNumbers);
        //Act
        ValidationResultDto actual = validator.validate(expected);
        //Assert
        assertEquals(expected, actual.invalidPhones);
        assertTrue(actual.validPhonesByCountry.isEmpty());
    }

    private void validTest(String country, String... phoneNumbers) {
        //Arrange
        List<String> expected = List.of(phoneNumbers);
        //Act
        ValidationResultDto actual = validator.validate(expected);
        //Assert
        assertEquals(expected, actual.validPhonesByCountry.get(country));
        assertTrue(actual.invalidPhones.isEmpty());
    }
}