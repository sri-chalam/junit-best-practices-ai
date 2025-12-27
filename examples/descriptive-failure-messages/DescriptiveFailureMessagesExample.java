package examples.descriptive-failure-messages;

public class DescriptiveFailureMessagesExample {
/**
 * Examples demonstrating clear vs unclear failure messages in unit tests.
 * Tests use Credit/Debit Card processing scenarios.
 */
class DescriptiveFailureMessagesExample {

    // BAD: Unclear failure message - only shows true/false
    @Test
    void processPayment_insufficientFunds_unclearMessage() {
        // Given
        DebitCard card = new DebitCard("6011123456789012", 25.00);
        PaymentProcessor processor = new PaymentProcessor();
        
        // When
        PaymentResult result = processor.processPayment(card, 100.00);
        
        // Then
        // BAD: Failure message would be: "expected: <true> but was: <false>"
        assertEquals(false, result.isApproved());
    }

    // GOOD: Clear failure message showing expected vs actual
    @Test
    void processPayment_insufficientFunds_clearMessage() {
        // Given
        DebitCard card = new DebitCard("6011123456789012", 25.00);
        PaymentProcessor processor = new PaymentProcessor();
        
        // When
        PaymentResult result = processor.processPayment(card, 100.00);
        
        // Then
        // GOOD: Failure message: "Expected payment to be DECLINED, but got status APPROVED"
        assertThat(result.getStatus())
            .as("Expected payment to be DECLINED, but got status %s with reason '%s'", 
                result.getStatus(), result.getDeclineReason())
            .isEqualTo(PaymentStatus.DECLINED);
            
        assertThat(result.getDeclineReason())
            .as("Insufficient funds for transaction")
            .isEqualTo("INSUFFICIENT_FUNDS");
    }

    // BAD: Generic assertion with no context
    @Test
    void validateCard_blockedCard_unclearMessage() {
        // Given
        CreditCard card = new CreditCard("4532123456789012", "12/25", "123");
        card.setStatus(CardStatus.BLOCKED);
        card.setBlockedReason("FRAUD_SUSPECTED");
        CardValidator validator = new CardValidator();
        
        // When
        ValidationResult result = validator.validate(card);
        
        // Then
        // BAD: Failure message would be: "expected: <ACTIVE> but was: <BLOCKED>"
        assertEquals(CardStatus.ACTIVE, card.getStatus());
    }

    // GOOD: Descriptive message with object context
    @Test
    void validateCard_blockedCard_clearMessage() {
        // Given
        CreditCard card = new CreditCard("4532123456789012", "12/25", "123");
        card.setStatus(CardStatus.BLOCKED);
        card.setBlockedReason("FRAUD_SUSPECTED");
        CardValidator validator = new CardValidator();
        
        // When
        ValidationResult result = validator.validate(card);
        
        // Then
        // GOOD: Failure message includes full context:
        // "Expected card state ACTIVE, but got card <{number: '****9012', state: 'BLOCKED', blockedReason: 'FRAUD_SUSPECTED'}>"
        assertThat(card.getStatus())
            .as("Expected card state ACTIVE, but got card <{number: '%s', state: '%s', blockedReason: '%s'}>",
                card.getMaskedNumber(), card.getStatus(), card.getBlockedReason())
            .isEqualTo(CardStatus.ACTIVE);
    }

    // Supporting classes
    static class DebitCard {
        private String number;
        private double balance;
        
        public DebitCard(String number, double balance) {
            this.number = number;
            this.balance = balance;
        }
        
        public double getBalance() { return balance; }
        public String getMaskedNumber() { return "****" + number.substring(number.length() - 4); }
    }
    
    static class CreditCard {
        private String number;
        private String expiry;
        private String cvv;
        private CardStatus status = CardStatus.ACTIVE;
        private String blockedReason;
        
        public CreditCard(String number, String expiry, String cvv) {
            this.number = number;
            this.expiry = expiry;
            this.cvv = cvv;
        }
        
        public String getMaskedNumber() { return "****" + number.substring(number.length() - 4); }
        public CardStatus getStatus() { return status; }
        public void setStatus(CardStatus status) { this.status = status; }
        public String getBlockedReason() { return blockedReason; }
        public void setBlockedReason(String reason) { this.blockedReason = reason; }
    }
    
    static class PaymentProcessor {
        public PaymentResult processPayment(DebitCard card, double amount) {
            if (card.getBalance() < amount) {
                return new PaymentResult(PaymentStatus.DECLINED, "INSUFFICIENT_FUNDS");
            }
            return new PaymentResult(PaymentStatus.APPROVED, null);
        }
    }
    
    static class PaymentResult {
        private PaymentStatus status;
        private String declineReason;
        
        public PaymentResult(PaymentStatus status, String declineReason) {
            this.status = status;
            this.declineReason = declineReason;
        }
        
        public boolean isApproved() { return status == PaymentStatus.APPROVED; }
        public PaymentStatus getStatus() { return status; }
        public String getDeclineReason() { return declineReason; }
    }
    
    static class CardValidator {
        public ValidationResult validate(CreditCard card) {
            return new ValidationResult(card.getStatus() == CardStatus.ACTIVE);
        }
    }
    
    static class ValidationResult {
        private boolean valid;
        public ValidationResult(boolean valid) { this.valid = valid; }
        public boolean isValid() { return valid; }
    }
    
    enum PaymentStatus { APPROVED, DECLINED }
    enum CardStatus { ACTIVE, BLOCKED, EXPIRED }
}	
}
