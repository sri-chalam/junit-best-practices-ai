package examples.follow-FIRST-principles;

// ❌ BAD EXAMPLE: Non-repeatable test (ANTI-PATTERN)
public class RepeatablePrincipleBadExample {
    @Test
    void convertAmount_NonRepeatable() {
        // BAD: Uses current date which changes daily
        LocalDate today = LocalDate.now();

        // BAD: Makes real HTTP call to external API
        ExchangeRateService realService = new LiveExchangeRateService();

        // BAD: Uses Random which produces different results each run
        Random random = new Random();
        BigDecimal randomAmount = new BigDecimal(random.nextDouble() * 1000);

        CurrencyConverter converter = new CurrencyConverter(realService);

        // This test will produce different results each time it runs
        Money result = converter.convert(new Money(randomAmount, Currency.USD), Currency.EUR);

        // Assertion might pass today but fail tomorrow
        // assertTrue(result.getAmount().compareTo(new BigDecimal("800")) > 0);
    }
}