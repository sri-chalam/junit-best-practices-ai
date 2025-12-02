package examples.follow-FIRST-principles;

public class IndependentPrincipleGoodExample {
    // ✅ GOOD EXAMPLE: INDEPENDENT: Each test sets up its own data and doesn't share state.
    // Tests can run in any order without affecting each other.
    @Test
    void processTransaction_ShouldSucceed_ForValidDebitCard() {
        // Arrange - Each test creates its own fresh instances
        TransactionProcessor processor = new TransactionProcessor();
        DebitCard debitCard = new DebitCard(
                "4532015112830366",
                "12/25",
                "123",
                new BigDecimal("1000.00") // Available balance
        );

        // Act
        TransactionResult result = processor.process(
                debitCard,
                new BigDecimal("50.00")
        );

        // Assert
        assertEquals(TransactionStatus.APPROVED, result.getStatus());
    }

    // ✅ GOOD EXAMPLE: INDEPENDENT: Each test sets up its own data and doesn't share state.
    // Tests can run in any order without affecting each other.
    @Test
    void processTransaction_ShouldDecline_WhenInsufficientFunds() {
        // Arrange - Independent setup, doesn't rely on previous test
        TransactionProcessor processor = new TransactionProcessor();
        DebitCard debitCard = new DebitCard(
                "4532015112830366",
                "12/25",
                "123",
                new BigDecimal("25.00") // Low balance
        );

        // Act
        TransactionResult result = processor.process(
                debitCard,
                new BigDecimal("50.00")
        );

        // Assert
        assertEquals(TransactionStatus.DECLINED, result.getStatus());
        assertEquals("INSUFFICIENT_FUNDS", result.getDeclineReason());
    }


}
