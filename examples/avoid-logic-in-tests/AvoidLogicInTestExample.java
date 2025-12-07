package examples.avoid-logic-in-tests;

public class AvoidLogicInTestExample { 
  // ✅ GOOD EXAMPLE - Simple, clear test
  @Test
  public void processRefund_ShouldCreditAccount_WhenRefundIsValid() {
    RefundProcessor processor = new RefundProcessor();
    Transaction originalTransaction = createTransaction("99.99");
    
    RefundResult result = processor.refund(originalTransaction);
    
    assertEquals(RefundStatus.COMPLETED, result.getStatus());
  }

  // ❌ BAD EXAMPLE: AVOID - Logic in test - (ANTI-PATTERN)
  @Test
  public void processRefund_BadExample() {
    // Complex loops and conditionals make tests hard to understand
    for (int i = 0; i < 10; i++) {
        if (i % 2 == 0) {
            // This is a code smell
        }
    }
  }
}  
