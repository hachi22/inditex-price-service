package com.bcnc.inditexpriceservice.application.service;

import com.bcnc.inditexpriceservice.domain.model.Price;
import com.bcnc.inditexpriceservice.domain.service.PriceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PriceServiceImplTest {

    private PriceServiceImpl priceServiceImpl;

    @BeforeEach
    void setUp() {
        priceServiceImpl = new PriceServiceImpl();
    }

    @Test
    void getApplicablePriceWithSingleMatchingPrice() {

                Price price1 = Price.builder()
                        .productId(35455L)
                        .brandId(1L)
                        .priceList(1L)
                        .startDate(LocalDateTime.of(2023, 12, 20, 10, 0, 0))
                        .endDate(LocalDateTime.of(2023, 12, 25, 18, 0, 0))
                        .priority(1)
                        .price(BigDecimal.valueOf(50.0))
                        .build();

        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 23, 15, 0, 0);

        Optional<Price> result = priceServiceImpl.getApplicablePrice(List.of(price1), applicationDate);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("50.0"), result.get().getPrice(), "The price should match the single applicable price.");
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

        Optional<Price> result = priceServiceImpl.getApplicablePrice(prices, applicationDate);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("60.0"), result.get().getPrice(), "The price with the higher priority should be selected.");
    }

    @Test
    void testGetApplicablePrice_withMultipleMatchingPricesSamePriority() {

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
                        .startDate(LocalDateTime.of(2023, 12, 20, 10, 0, 0))
                        .endDate(LocalDateTime.of(2023, 12, 25, 18, 0, 0))
                        .priority(1)
                        .price(BigDecimal.valueOf(60.0))
                        .build()
        );

        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 23, 15, 0, 0);

        Optional<Price> result = priceServiceImpl.getApplicablePrice(prices, applicationDate);

        assertTrue(result.isPresent());
        assertEquals(new BigDecimal("50.0"), result.get().getPrice(), "The first matching price should be selected when priorities are equal.");
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

        Optional<Price> result = priceServiceImpl.getApplicablePrice(List.of(price1), applicationDate);

        assertTrue(result.isEmpty(), "No price should be returned if none match the application date.");
    }

    @Test
    void testGetApplicablePrice_withNoPricesProvided() {

        List<Price> prices = List.of();

        LocalDateTime applicationDate = LocalDateTime.of(2023, 12, 23, 15, 0, 0);

        Optional<Price> result = priceServiceImpl.getApplicablePrice(prices, applicationDate);

        assertTrue(result.isEmpty(), "No price should be returned if the price list is empty.");
    }
}
