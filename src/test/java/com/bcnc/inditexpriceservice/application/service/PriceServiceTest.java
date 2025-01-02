package com.bcnc.inditexpriceservice.application.service;
import com.bcnc.inditexpriceservice.domain.model.Price;
import com.bcnc.inditexpriceservice.infrastructure.persistance.PriceRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PriceServiceTest {

    private PriceRepository priceRepository;
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        priceRepository = Mockito.mock(PriceRepository.class);
        priceService = new PriceService(priceRepository);
    }

    @Test
    void returnCorrectPriceFor10AMOn14June2020() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price expectedPrice = Price.builder()
                .id(1L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1L)
                .productId(productId)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(expectedPrice));

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.of(expectedPrice), actualPrice);
    }

    @Test
    void returnCorrectPriceFor4PMOn14June2020() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price higherPriorityPrice = Price.builder()
                .id(2L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priceList(2L)
                .productId(productId)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        Price lowerPriorityPrice = Price.builder()
                .id(1L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1L)
                .productId(productId)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(lowerPriorityPrice, higherPriorityPrice));

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.of(higherPriorityPrice), actualPrice);
    }

    @Test
    void returnCorrectPriceFor9PMOn14June2020() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price defaultPrice = Price.builder()
                .id(1L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(1L)
                .productId(productId)
                .priority(0)
                .price(new BigDecimal("35.50"))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(defaultPrice));

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.of(defaultPrice), actualPrice);
    }

    @Test
    void returnCorrectPriceFor10AMOn15June2020() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price specialPrice = Price.builder()
                .id(3L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 15, 11, 0))
                .priceList(3L)
                .productId(productId)
                .priority(1)
                .price(new BigDecimal("30.50"))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(specialPrice));

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.of(specialPrice), actualPrice);
    }

    @Test
    void returnCorrectPriceFor9PMOn16June2020() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        Long brandId = 1L;
        Long productId = 35455L;

        Price defaultPrice = Price.builder()
                .id(4L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 15, 16, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priceList(4L)
                .productId(productId)
                .priority(1)
                .price(new BigDecimal("38.95"))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(defaultPrice));

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.of(defaultPrice), actualPrice);
    }

    @Test
    void noPricesAvailable_shouldReturnEmptyOptional() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 17, 10, 0); // Date not covered by any price
        Long brandId = 1L;
        Long productId = 35455L;

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of());

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.empty(), actualPrice);
    }

    @Test
    void invalidBrandOrProductId_shouldReturnEmptyOptional() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long brandId = 99L; // Nonexistent brand
        Long productId = 99999L; // Nonexistent product

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of());

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.empty(), actualPrice);
    }

    @Test
    void multiplePricesSamePriority_shouldReturnFirstInList() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0); // Overlapping prices
        Long brandId = 1L;
        Long productId = 35455L;

        Price price1 = Price.builder()
                .id(1L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priceList(2L)
                .productId(productId)
                .priority(1)
                .price(new BigDecimal("25.45"))
                .currency("EUR")
                .build();

        Price price2 = Price.builder()
                .id(2L)
                .brandId(brandId)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priceList(3L)
                .productId(productId)
                .priority(1)
                .price(new BigDecimal("20.00"))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(price1, price2));

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.of(price1), actualPrice);
    }
}
