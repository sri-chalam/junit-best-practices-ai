package examples.organize-using-given-when-then;

public class GivenWhenThenBadExample {
    @Test
    void testCardProcessing() {
        // Bad: No clear Given-When-Then structure
        // Bad: Testing multiple behaviors in one test
        CreditCard card = new CreditCard("4532-1111-2222-3333", LocalDate.of(2027, 12, 31));
        card.setAvailableCredit(new BigDecimal("5000.00"));
        CardProcessor processor = new CardProcessor();
        
        TransactionResult result1 = processor.processTransaction(card, new BigDecimal("100.00"));
        assertTrue(result1.isApproved()); // First behavior tested
        
        TransactionResult result2 = processor.processTransaction(card, new BigDecimal("10000.00"));
        assertFalse(result2.isApproved()); // Second behavior tested - insufficient funds
        
        card.setExpirationDate(LocalDate.of(2023, 1, 1));
        TransactionResult result3 = processor.processTransaction(card, new BigDecimal("50.00"));
        assertFalse(result3.isApproved()); // Third behavior tested - expired card
    }	
}
