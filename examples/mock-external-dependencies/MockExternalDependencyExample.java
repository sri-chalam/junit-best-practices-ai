package examples.mock-external-dependencies;

public class MockExternalDependencyExample {
    @Test
    void chargeCard_ShouldCallGateway_WhenProcessingPayment() {
        // Arrange
        PaymentGateway mockGateway = mock(PaymentGateway.class);
        when(mockGateway.charge(any(), any())).thenReturn(new GatewayResponse("SUCCESS"));
    
        PaymentService service = new PaymentService(mockGateway);
        CreditCard card = new CreditCard("4532015112830366", "12/25", "123");
    
        // Act
        service.processPayment(card, new BigDecimal("99.99"));
    
        // Assert
        verify(mockGateway, times(1)).charge(eq(card), eq(new BigDecimal("99.99")));
    }
}

