package examples.expected-exception;

public class ExpectedExceptionExample {
	@Test
    void validateCard_ShouldThrowException_WhenNumberIsEmpty() {
        CreditCardValidator validator = new CreditCardValidator();

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> validator.isValid("")
        );

        assertEquals("Card number cannot be empty", exception.getMessage());
    }

    @Test
    void processPayment_ShouldThrowException_WhenAmountIsNegative() {
        PaymentProcessor processor = new PaymentProcessor();

        assertThrows(
            InvalidAmountException.class,
            () -> processor.process(testCard, new BigDecimal("-10.00"))
        );
    }
}
