package examples.keep-independent;

public class KeepTestsIndependentExample {
    private TransactionProcessor processor;
    private CreditCard testCard;
    
    @BeforeEach
    void setUp() {
        processor = new TransactionProcessor();
        testCard = new CreditCard("4532015112830366", "12/25", "123");
    }
    
    @Test
    void firstTest_DoesNotAffectSecondTest() {
        processor.authorize(testCard, new BigDecimal("50.00"));
        // This test is self-contained
    }
    
    @Test
    void secondTest_RunsIndependently() {
        processor.authorize(testCard, new BigDecimal("100.00"));
        // Independent of firstTest
    }
}
