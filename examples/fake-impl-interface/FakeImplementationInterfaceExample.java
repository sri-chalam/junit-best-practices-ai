package examples.fake_vs_mock_interface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Example demonstrating interface-based fake implementation for testing
 * a card payment processor that stores receipts in AWS S3.
 *
 * Key Points:
 * 1. S3StorageService interface enables fake implementation for testing
 * 2. Production code uses dependency injection (constructor)
 * 3. FakeS3StorageService provides in-memory, stateful behavior
 * 4. Tests verify state (files stored) not interactions
 */

// ============================================================================
// INTERFACE FOR S3 STORAGE
// ============================================================================

/**
 * Interface for S3 storage operations.
 * Production code uses real AWS S3 implementation.
 * Test code uses FakeS3StorageService implementation.
 */
interface S3StorageService {
    /**
     * Stores receipt in S3
     * @param transactionId Transaction identifier
     * @param receiptContent Receipt text content
     * @return true if stored successfully
     */
    boolean storeReceipt(String transactionId, String receiptContent);
}

// ============================================================================
// PRODUCTION CODE
// ============================================================================

/**
 * Processes card payments and stores receipts using S3.
 * Uses S3StorageService interface (not direct AWS SDK calls).
 */
class CardPaymentProcessor {
    private final S3StorageService s3Service;

    // Constructor injection enables testing with fake implementation
    public CardPaymentProcessor(S3StorageService s3Service) {
        this.s3Service = s3Service;
    }

    public String processPayment(String cardNumber, double amount) {
        if (!isValidCard(cardNumber) || amount <= 0) {
            return null;
        }

        String transactionId = "TXN-" + System.currentTimeMillis();
        String receipt = generateReceipt(transactionId, cardNumber, amount);

        boolean stored = s3Service.storeReceipt(transactionId, receipt);
        return stored ? transactionId : null;
    }

    private boolean isValidCard(String cardNumber) {
        return cardNumber != null && cardNumber.length() >= 13;
    }

    private String generateReceipt(String transactionId, String cardNumber, double amount) {
        String maskedCard = "****-" + cardNumber.substring(cardNumber.length() - 4);
        return String.format("Transaction: %s\nCard: %s\nAmount: $%.2f",
                           transactionId, maskedCard, amount);
    }
}

// ============================================================================
// FAKE IMPLEMENTATION FOR TESTING
// ============================================================================

/**
 * Fake S3 implementation using in-memory HashMap.
 * Provides realistic, stateful behavior without actual AWS infrastructure.
 */
class FakeS3StorageService implements S3StorageService {
    private final Map<String, String> storage = new HashMap<>();

    @Override
    public boolean storeReceipt(String transactionId, String receiptContent) {
        if (transactionId == null || receiptContent == null) {
            return false;
        }
        storage.put(transactionId, receiptContent);
        return true;
    }

    // Helper method for test verification
    public String getReceipt(String transactionId) {
        return storage.get(transactionId);
    }

    public int getReceiptCount() {
        return storage.size();
    }
}

// ============================================================================
// TESTS USING FAKE IMPLEMENTATION
// ============================================================================

class CardPaymentProcessorTest {
    private FakeS3StorageService fakeS3;
    private CardPaymentProcessor processor;

    @BeforeEach
    void setUp() {
        fakeS3 = new FakeS3StorageService();
        processor = new CardPaymentProcessor(fakeS3);
    }

    @Test
    void shouldProcessPaymentAndStoreReceiptInS3() {
        // Act
        String transactionId = processor.processPayment("4532123456789010", 99.99);

        // Assert - Verify state, not interactions
        assertNotNull(transactionId);
        assertEquals(1, fakeS3.getReceiptCount());

        String receipt = fakeS3.getReceipt(transactionId);
        assertNotNull(receipt);
        assertTrue(receipt.contains(transactionId));
        assertTrue(receipt.contains("$99.99"));
    }

    @Test
    void shouldRejectInvalidCardNumber() {
        // Act
        String transactionId = processor.processPayment("123", 50.00);

        // Assert
        assertNull(transactionId);
        assertEquals(0, fakeS3.getReceiptCount());
    }

    @Test
    void shouldHandleMultiplePayments() {
        // Act
        String txn1 = processor.processPayment("4532111111111111", 100.00);
        String txn2 = processor.processPayment("5105222222222222", 200.00);

        // Assert
        assertNotNull(txn1);
        assertNotNull(txn2);
        assertEquals(2, fakeS3.getReceiptCount());
        assertNotNull(fakeS3.getReceipt(txn1));
        assertNotNull(fakeS3.getReceipt(txn2));
    }
}
