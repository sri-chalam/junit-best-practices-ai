package examples.use-setup-methods;

public class SetupMethodsBeforeAllExample {
    private static CardNetworkRules networkRules;
    private static BINDatabase binDatabase;
    private CreditCardValidator validator;
    
    // ✅ GOOD EXAMPLE: @BeforeAll for expensive one-time setup of immutable data
    @BeforeAll
    static void setUpOnce() {
        // Load card network rules once (expensive operation)
        networkRules = CardNetworkRules.loadFromFile("card-network-rules.json");
        
        // Initialize BIN database once (large dataset)
        binDatabase = BINDatabase.loadFromCSV("bin-ranges.csv");
        
        // These are immutable and can be safely shared across all tests
    }
    
    @BeforeEach
    void setUp() {
        // Create fresh validator for each test, using shared immutable rules
        validator = new CreditCardValidator(networkRules, binDatabase);
    }
    
    @Test
    void cardValidation_validateVisaCard_returnsValid() {
        String visaCard = "4532015112830366";
        
        boolean result = validator.isValid(visaCard);
        
        assertTrue(result);
    }
    
    @Test
    void binLookup_identifyCardIssuer_returnsCorrectBank() {
        String cardNumber = "4532015112830366";
        
        CardIssuer issuer = validator.identifyIssuer(cardNumber);
        
        assertEquals("Chase Bank", issuer.getBankName());
        assertEquals(CardNetwork.VISA, issuer.getNetwork());
    }
    
    @AfterAll
    static void tearDownOnce() {
        // Clean up expensive resources
        networkRules = null;
        binDatabase = null;
    }
}
