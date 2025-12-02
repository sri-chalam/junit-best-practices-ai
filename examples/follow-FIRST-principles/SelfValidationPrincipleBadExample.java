package examples.follow-FIRST-principles;

// ❌ BAD EXAMPLE: - Not self-validating (ANTI-PATTERN)
public class SelfValidationPrincipleBadExample {
    @Test
    void detectFraud_NotSelfValidating() {
        FraudDetectionService fraudService = new FraudDetectionService();
        Transaction transaction = createTransaction();

        // BAD: Writes to log file - requires manual inspection
        fraudService.analyze(transaction);
        System.out.println("Check fraud_detection.log to see if fraud was detected");

        // BAD: No assertions - test always passes
        // Developer must manually verify the log file to know if test passed

        // BAD: Writes to database - requires manual query
        // "SELECT * FROM fraud_alerts WHERE transaction_id = ?"
    }
}