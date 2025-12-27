package examples.organize-using-given-when-then;


public class GivenWhenThenGoodExample {
	@Test
        public void shouldApproveTransactionWhenCardHasSufficientBalance() {
        	// given
        	CreditCard card = new CreditCard("4532-1111-2222-3333", LocalDate.of(2027, 12, 31));
        	card.setAvailableCredit(new BigDecimal("5000.00"));
        	CardProcessor processor = new CardProcessor();
        	BigDecimal purchaseAmount = new BigDecimal("100.00");

        	// when
        	TransactionResult result = processor.processTransaction(card, purchaseAmount);

        	// then
        	assertTrue(result.isApproved());
        	assertEquals("APPROVED", result.getStatus());
        	assertEquals(new BigDecimal("4900.00"), card.getAvailableCredit());
        }
}
