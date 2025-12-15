package examples.follow-FIRST-principles;

// ✅ GOOD EXAMPLE: SELF-VALIDATING: Test automatically determines pass/fail without manual inspection.
// No need to check logs, databases, or console output.
public class SelfValidationPrincipleGoodExample {
    @Test
    void detectFraud_ShouldFlagHighRiskTransaction_Automatically() {
        // Arrange
        FraudDetectionService fraudService = new FraudDetectionService();

        Transaction suspiciousTransaction = Transaction.builder()
                .amount(new BigDecimal("9999.99"))
                .cardNumber("4532015112830366")
                .merchantCountry("NG") // High-risk country
                .transactionTime(LocalTime.of(3, 30)) // Unusual hour
                .isOnlineTransaction(true)
                .customerLocation("US")
                .build();

        // Act
        FraudScore score = fraudService.analyze(suspiciousTransaction);

        // Assert - Clear pass/fail without manual checking
        assertTrue(score.isHighRisk(), "Transaction should be flagged as high risk");
        assertTrue(score.getScore() > 75, "Fraud score should exceed 75");
        assertThat(score.getRiskFactors())
                .contains("HIGH_AMOUNT", "HIGH_RISK_COUNTRY", "UNUSUAL_TIME");
    }
}
