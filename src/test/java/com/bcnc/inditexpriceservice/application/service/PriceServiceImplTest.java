package com.bcnc.inditexpriceservice.application.service;

import com.bcnc.inditexpriceservice.domain.model.Price;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PriceServiceImplTest {

    private PriceServiceImpl priceServiceImpl;

    @BeforeEach
    void setUp() {
        priceServiceImpl = new PriceServiceImpl();
    }

    @Test
    void getPriceAt10amOn14thForProduct35455ForBrand1() {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        Price price1 = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();


        Price result = priceServiceImpl.getApplicablePrice(List.of(price1), applicationDate);


        assertNotNull(result);
        assertEquals(new BigDecimal("35.50"), result.getPrice());
    }

    @Test
    void getPriceAt04pmOn14thForProduct35455ForBrand1() {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price price1 = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        Price price2 = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();


        Price result = priceServiceImpl.getApplicablePrice(List.of(price1, price2), applicationDate);


        assertNotNull(result);
        assertEquals(new BigDecimal("25.45"), result.getPrice());
    }

    @Test
    void getPriceAt09pmOn14thForProduct35455ForBrand1() {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);

        Price price1 = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();


        Price result = priceServiceImpl.getApplicablePrice(List.of(price1), applicationDate);


        assertNotNull(result);
        assertEquals(new BigDecimal("35.50"), result.getPrice());
    }

    @Test
    void getPriceAt10amOn15thForProduct35455ForBrand1() {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        Price price1 = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 15, 11, 0))
                .priority(1)
                .price(new BigDecimal("30.50"))
                .currency("EUR")
                .build();

        Price price2 = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();


        Price result = priceServiceImpl.getApplicablePrice(List.of(price1, price2), applicationDate);


        assertNotNull(result);
        assertEquals(new BigDecimal("30.50"), result.getPrice());
    }

    @Test
    void getPriceAt09pmOn16thForProduct35455ForBrand1() {

        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);

        Price price1 = Price.builder()
                .brandId(1L)
                .productId(35455L)
                .startDate(LocalDateTime.of(2020, 6, 15, 16, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(1)
                .price(new BigDecimal("38.95"))
                .currency("EUR")
                .build();


        Price result = priceServiceImpl.getApplicablePrice(List.of(price1), applicationDate);


        assertNotNull(result);
        assertEquals(new BigDecimal("38.95"), result.getPrice());
    }

    @Test
    void getApplicablePriceWithMultiplePricesDifferentPriorities() {

        List<Price> prices = Arrays.asList(
                Price.builder()
                        .productId(35455L)
                        .brandId(1L)
                        .priceList(1L)
                        .startDate(LocalDateTime.of(2023, 12, 20, 10, 0, 0))
                        .endDate(LocalDateTime.of(2023, 12, 25, 18, 0, 0))
                        .priority(1)
                        .price(BigDecimal.valueOf(50.0))
                        .build(),
                Price.builder()
                        .productId(35455L)
                        .brandId(1L)
                        .priceList(2L)
                        .startDate(LocalDateTime.of(2023, 12, 21, 10, 0, 0))
                        .endDate(LocalDateTime.of(2023, 12, 24, 18, 0, 0))
                        .priority(2)
                        .price(BigDecimal.valueOf(60.0))
                        .build()
        );

        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 23, 15, 0, 0);

        Price result = priceServiceImpl.getApplicablePrice(prices, applicationDate);

        assertEquals(new BigDecimal("60.0"), result.getPrice(), "The price with the higher priority should be selected.");
    }

    @Test
    void getApplicablePriceWithNoMatchingPrices() {


                Price price1 = Price.builder()
                        .productId(35455L)
                        .brandId(1L)
                        .priceList(1L)
                        .startDate(LocalDateTime.of(2023, 12, 20, 10, 0, 0))
                        .endDate(LocalDateTime.of(2023, 12, 25, 18, 0, 0))
                        .priority(1)
                        .price(BigDecimal.valueOf(50.0))
                        .build();

        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 26, 15, 0, 0);

        Price result = priceServiceImpl.getApplicablePrice(List.of(price1), applicationDate);

        assertNull(result, "No price should be returned if none match the application date.");
    }
}
