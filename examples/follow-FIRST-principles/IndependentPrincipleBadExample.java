package examples.follow-FIRST-principles;

public class IndependentPrincipleBadExample {
    // Shared state - causes tests to be dependent
    private static Account sharedAccount;

    @Test
    @Order(1) // Test order matters - this is a red flag!
    void test1_CreateAccount() {
        sharedAccount = new Account(new BigDecimal("1000.00"));
        assertEquals(new BigDecimal("1000.00"), sharedAccount.getBalance());
    }

    @Test
    @Order(2) // This test DEPENDS on test1 running first
    void test2_DeductFunds() {
        // PROBLEM: Fails if test1 doesn't run first
        sharedAccount.deduct(new BigDecimal("100.00"));
        assertEquals(new BigDecimal("900.00"), sharedAccount.getBalance());
    }
}
