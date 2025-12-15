package examples.avoid-testing-implementation-details;

// ❌ BAD EXAMPLE: Testing the internal Luhn checksum calculation method (ANTI-PATTERN)
@Test
public void shouldCalculateLuhnChecksumCorrectly() {
    CreditCardValidator validator = new CreditCardValidator();
    String cardNumber = "4532015112830366";
    
    // calculateLuhnChecksum() is a private helper method
    // This tests HOW validation works, not WHAT it does
    int checksum = validator.calculateLuhnChecksum(cardNumber);
    
    assertThat(checksum).isEqualTo(0); // Valid Luhn checksum
}

// ❌ BAD EXAMPLE: Testing internal digit doubling logic (ANTI-PATTERN)
@Test
public void shouldDoubleEverySecondDigit() {
    CreditCardValidator validator = new CreditCardValidator();
    
    // Testing private implementation details of Luhn algorithm
    int[] digits = {4, 5, 3, 2, 0, 1, 5, 1};
    int[] doubled = validator.doubleAlternateDigits(digits);
    
    assertThat(doubled).containsExactly(8, 5, 6, 2, 0, 1, 1, 1);
}

// ❌ BAD EXAMPLE: Testing internal card type detection logic (ANTI-PATTERN)
@Test
public void shouldIdentifyCardTypeByBIN() {
    CreditCardValidator validator = new CreditCardValidator();
    
    // Testing private method that identifies card type
    CardType type = validator.detectCardType("4532015112830366");
    
    assertThat(type).isEqualTo(CardType.VISA);
}
// ✅ GOOD EXAMPLE: Testing public behavior with invalid card
@Test
public void isValid_shouldReturnFalse_whenCardNumberFailsLuhnCheck() {
    CreditCardValidator validator = new CreditCardValidator();
    String invalidCard = "4532015112830367"; // Last digit wrong
    
    // Don't care HOW it validates, just that it rejects invalid cards
    boolean result = validator.isValid(invalidCard);
    
    assertThat(result).isFalse();
}

// ✅ GOOD EXAMPLE: Testing public API - card type is determined implicitly
@Test
public void validate_shouldAcceptMultipleCardTypes() {
    CreditCardValidator validator = new CreditCardValidator();
    
    // Test behavior: validator accepts different card types
    // Don't care about internal BIN detection logic
    assertThat(validator.isValid("4532015112830366")).isTrue(); // Visa
    assertThat(validator.isValid("5555555555554444")).isTrue(); // Mastercard
    assertThat(validator.isValid("378282246310005")).isTrue();  // Amex
}

// ✅ GOOD EXAMPLE: Testing validation result, not implementation
@Test
public void validate_shouldProvideValidationResult_withDetailedErrors() {
    CreditCardValidator validator = new CreditCardValidator();
    String invalidCard = "1234567890123456";
    
    // Test the public contract: get validation result
    ValidationResult result = validator.validate(invalidCard);
    
    assertThat(result.isValid()).isFalse();
    assertThat(result.getErrors()).contains("Invalid card number format");
    // We don't test HOW it determined invalidity, just THAT it did
}
