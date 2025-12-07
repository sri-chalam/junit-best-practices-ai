package examples.use-setup-methods;

class SetupMethodsBeforeEachExample {
    private PaymentProcessor processor;
    private PaymentGateway mockGateway;
    private CreditCard validCard;
    
    // ✅ GOOD EXAMPLE: @BeforeEach creates fresh instances for each test
    @BeforeEach
    void setUp() {
        // Each test gets its own fresh instances
        mockGateway = mock(PaymentGateway.class);
        processor = new PaymentProcessor(mockGateway);
        
        // Fresh card object for each test
        validCard = new CreditCard(
            "4532015112830366",
            "12/26",
            "123"
        );
    }
    
    @Test
    void paymentProcessing_processValidCard_returnsSuccess() {
        when(mockGateway.authorize(any())).thenReturn(
            new GatewayResponse("APPROVED", "AUTH123")
        );
        
        PaymentResult result = processor.processPayment(
            validCard, 
            new BigDecimal("99.99")
        );
        
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
    }
    
    @Test
    void paymentProcessing_processWithGatewayTimeout_retriesAndSucceeds() {
        when(mockGateway.authorize(any()))
            .thenThrow(new GatewayTimeoutException())
            .thenReturn(new GatewayResponse("APPROVED", "AUTH456"));
        
        PaymentResult result = processor.processPayment(
            validCard, 
            new BigDecimal("50.00")
        );
        
        assertEquals(PaymentStatus.SUCCESS, result.getStatus());
        verify(mockGateway, times(2)).authorize(any());
    }
    
    @AfterEach
    void tearDown() {
        // Clean up resources if needed
        // Mocks are automatically reset between tests
        processor = null;
        mockGateway = null;
        validCard = null;
    }
}
