package io.polybius.phonevalidator;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MobilePhoneNumberValidatorTest {

    private final MobilePhoneNumberValidator validator = new MobilePhoneNumberValidator();

    @ParameterizedTest
    @CsvSource({
            "+37061234567,LT",
            "+370-612-34-567,LT",
            "+370 612 34 567,LT",
            "+370(6)1234567,LT",
            "37061234567,LT",
    })
    public void validateLithuania(String phoneNumber, String country) {
        correctTest(phoneNumber,country);
    }

    private void correctTest(String phoneNumber, String country) {
        //Arrange
        List<String> expected = List.of(phoneNumber);
        //Act
        ValidationResultDto actual = validator.validate(expected);
        //Assert
        assertEquals(expected,actual.validPhonesByCountry.get(country));
        assertTrue(actual.invalidPhones.isEmpty());
    }
    private List<String> getNumbersByCountry(String country, List<String> numbers) {
        ValidationResultDto result = validator.validate(numbers);
        return result.validPhonesByCountry.get(country);
    }
//    @Test
//    public void validateLatvia() {
//        String country = "LV";
//        List<String> numbers = List.of(
//                "+37121234567", "+371-212-34-567", "+371 212 34 567", "+371(2)1234567"
//        );
//        assertEquals(numbers, getNumbersByCountry(country, numbers));
//    }
//
//    @Test
//    public void validateLatviaOnlyNumbers() {
//        String country = "LV";
//        List<String> numbers = List.of(
//                "37121234567"
//        );
//        assertEquals(numbers, getNumbersByCountry(country, numbers));
//    }
//
//    @Test
//    public void validateEstonia() {
//        String country = "EE";
//        List<String> numbers = List.of(
//                "+37251234567", "+372-512-34-567", "+372 512 34 567", "+372(5)1234567"
//        );
//        assertEquals(numbers, getNumbersByCountry(country, numbers));
//    }
//
//    @Test
//    public void validateEstoniaOnlyNumbers() {
//        String country = "EE";
//        List<String> numbers = List.of(
//                "37251234567"
//        );
//        assertEquals(numbers, getNumbersByCountry(country, numbers));
//    }
//
//    @Test
//    public void validateBelgium() {
//        String country = "BE";
//        List<String> numbers = List.of(
//                "+32456234567", "+324-712-34-567", "+324 812 34 567", "+324(9)1234567"
//        );
//        assertEquals(numbers, getNumbersByCountry(country, numbers));
//    }
//
//    @Test
//    public void validateBelgiumOnlyNumbers() {
//        String country = "BE";
//        List<String> numbers = List.of(
//                "32456234567"
//        );
//        assertEquals(numbers, getNumbersByCountry(country, numbers));
//    }
//
//    @Test
//    public void validateDoublePlus() {
//        List<String> numbers = List.of(
//                "++32456234567"
//        );
//        assertEquals(numbers, validator.validate(numbers).invalidPhones);
//    }
//
//    @Test
//    public void validateInvalidNumbers() {
//        List<String> numbers = List.of(
//                "+304-712-34-567", "+3706123456"
//        );
//        assertEquals(numbers, validator.validate(numbers).invalidPhones);
//    }
//
//    @Test
//    public void validateNoClosingBrace() {
//        List<String> numbers = List.of(
//                "+324(91234567"
//        );
//        assertEquals(numbers, validator.validate(numbers).invalidPhones);
//    }
//
//    @Test
//    public void validateInvalidCharacter() {
//        List<String> numbers = List.of(
//                "~32456234567"
//        );
//        assertEquals(numbers, validator.validate(numbers).invalidPhones);
//    }
}