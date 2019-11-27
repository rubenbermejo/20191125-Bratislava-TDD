package ca.jbrains.pos.test;

import io.vavr.control.Option;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class FindPriceInMemoryCatalogTest {
    @Test
    void productFound() throws Exception {
        final Price matchingPrice = Price.cents(795);
        Assertions.assertEquals(Option.of(matchingPrice), new InMemoryCatalog(
                new HashMap<>() {{
                    put("12345", matchingPrice);
                }}
        ).findPrice("12345"));
    }

    @Test
    void productNotFound() throws Exception {
        Assertions.assertEquals(Option.none(), new InMemoryCatalog(
                new HashMap<>() {{
                    put("99999", Price.cents(0));
                }}
        ).findPrice("12345"));
    }

    private static class InMemoryCatalog {
        private final Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
            this.pricesByBarcode = pricesByBarcode;
        }

        public Option<Price> findPrice(String barcode) {
            return Option.of(pricesByBarcode.get(barcode));
        }
    }
}
