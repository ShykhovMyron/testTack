package io.polybius.phonevalidator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

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
            "BE,+324(9)1234567,+324-712-34-567",
    })
    public void validPhoneNumbersTwoParametersTest(String country, String phoneNumber1, String phoneNumber2) {
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

    @ParameterizedTest
    @CsvSource({
            "LT,+37061234567,++370-612-34-567",
            "LV,+371-212-34-567,~371 212 34 567",
            "EE,+37251234567,+37)(2-512-34-567",
            "BE,+324 812 34 567,3256234567",
    })
    public void validAndInvalidPhoneNumbersTest(
            String country, String validPhoneNumber, String invalidPhoneNumber
    ) {
        validAndInvalidTest(country,validPhoneNumber,invalidPhoneNumber);
    }

    private void validAndInvalidTest(String country, String validPhoneNumber, String invalidPhoneNumber) {
        //Arrange
        List<String> expectedValid = List.of(validPhoneNumber);
        List<String> expectedInvalid = List.of(invalidPhoneNumber);
        //Act
        ValidationResultDto actual = validator.validate(List.of(validPhoneNumber,invalidPhoneNumber));
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