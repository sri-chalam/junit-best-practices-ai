package examples.name-test-behavior-results;

public class CreditCardValidatorTest {
    // ❌ BAD EXAMPLE: Only mentions the action, not the expected outcome (ANTI-PATTERN)
    @Test
    public void testValidation() {
        CreditCardValidator validator = new CreditCardValidator();
        assertFalse(validator.isValid("1234"));
    }
    
    // ✅ GOOD EXAMPLE: Complete behavior description with edge case
    @Test
    public void cardValidation_validateCorrectLengthWithInvalidLuhn_returnsInvalid() {
        CreditCardValidator validator = new CreditCardValidator();
        String invalidChecksum = "4532015112830367"; // Wrong last digit
        
        boolean result = validator.isValid(invalidChecksum);
        
        assertFalse(result);
    }
    
    // ✅ GOOD EXAMPLE: Testing specific card type validation
    @Test
    public void cardValidation_validateMastercardNumberWithValidFormat_returnsValid() {
        CreditCardValidator validator = new CreditCardValidator();
        String validMastercard = "5555555555554444";
        
        boolean result = validator.isValid(validMastercard);
        
        assertTrue(result);
    }
}
