package examples.follow-FIRST-principles;

public class FastPrincipleExample {
    // ✅ GOOD EXAMPLE: FAST: This test executes in milliseconds by mocking external payment gateway
    // instead of making real network calls which would take seconds.
    @Test
    void authorizePayment_ShouldComplete_InMilliseconds() {
        // Arrange - Use mocks to avoid slow external calls
        PaymentGateway mockGateway = mock(PaymentGateway.class);
        when(mockGateway.authorize(any(), any()))
                .thenReturn(new AuthResponse("AUTH123", AuthStatus.APPROVED));

        PaymentAuthorizationService service = new PaymentAuthorizationService(mockGateway);
        CreditCard card = new CreditCard("4532015112830366", "12/25", "123");
        Money amount = Money.dollars(250.00);

        long startTime = System.currentTimeMillis();

        // Act
        AuthorizationResult result = service.authorize(card, amount);

        long executionTime = System.currentTimeMillis() - startTime;

        // Assert
        assertEquals(AuthStatus.APPROVED, result.getStatus());
        assertTrue(executionTime < 100,
                "Test should execute in less than 100ms, took: " + executionTime + "ms");
    }

    // ❌ BAD EXAMPLE: SLOW (ANTI-PATTERN) - Avoid this approach
    @Test
    @Disabled("This test is too slow - makes real database and API calls")
    void authorizePayment_SlowVersion() {
        // BAD: Creates real database connection
        DatabaseConnection db = new DatabaseConnection("jdbc:mysql://localhost:3306/payments");

        // BAD: Makes real HTTP call to payment gateway (takes 2-5 seconds)
        PaymentGateway realGateway = new VisaPaymentGateway("https://api.visa.com");

        // This test would take several seconds instead of milliseconds
        PaymentAuthorizationService service = new PaymentAuthorizationService(realGateway, db);
        // ... rest of test
    }
}
