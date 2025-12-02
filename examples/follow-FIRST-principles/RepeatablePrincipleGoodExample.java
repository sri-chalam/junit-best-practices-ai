package examples.follow-FIRST-principles;

// ✅ GOOD EXAMPLE: REPEATABLE: Test produces same results every time, regardless of environment.
// No dependency on current date, random values, or external systems.
public class RepeatablePrincipleGoodExample {
    @Test
    void calculateExchangeFee_ShouldBeRepeatable() {
        // Arrange
        FeeCalculator calculator = new FeeCalculator();
        Money amount = Money.dollars(1000.00);

        // Act - Run multiple times to prove repeatability
        BigDecimal fee1 = calculator.calculateForeignExchangeFee(amount);
        BigDecimal fee2 = calculator.calculateForeignExchangeFee(amount);
        BigDecimal fee3 = calculator.calculateForeignExchangeFee(amount);

        // Assert - All results are identical
        assertEquals(fee1, fee2);
        assertEquals(fee2, fee3);
        assertEquals(new BigDecimal("30.00"), fee1); // 3% fee
    }
}