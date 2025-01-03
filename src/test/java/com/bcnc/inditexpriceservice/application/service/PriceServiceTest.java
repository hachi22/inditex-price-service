package com.bcnc.inditexpriceservice.application.service;
import com.bcnc.inditexpriceservice.domain.model.Price;
import com.bcnc.inditexpriceservice.infrastructure.persistance.PriceRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class PriceServiceTest {

    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceServiceImpl priceServiceImpl;

    @InjectMocks
    private PriceService priceService;

    @Test
    void getPriceAt10amOn14th() {

        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

        Price expectedPrice = Price.builder()
                .id(1L)
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(expectedPrice));
        when(priceServiceImpl.getApplicablePrice(List.of(expectedPrice)))
                .thenReturn(Optional.of(expectedPrice));


        Optional<Price> result = priceService.getPrice(brandId, productId, applicationDate);


        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
    }

    @Test
    void getPriceAt4pmOn14th() {

        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);

        Price price1 = Price.builder()
                .id(1L)
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.of(2020, 6, 14, 0, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(0)
                .price(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        Price price2 = Price.builder()
                .id(2L)
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.of(2020, 6, 14, 15, 0))
                .endDate(LocalDateTime.of(2020, 6, 14, 18, 30))
                .priority(1)
                .price(BigDecimal.valueOf(25.45))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(price1, price2));
        when(priceServiceImpl.getApplicablePrice(List.of(price1, price2)))
                .thenReturn(Optional.of(price2));


        Optional<Price> result = priceService.getPrice(brandId, productId, applicationDate);


        assertTrue(result.isPresent());
        assertEquals(price2, result.get());
    }

    @Test
    void getPriceAt9pmOn14th() {

        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of());
        when(priceServiceImpl.getApplicablePrice(List.of()))
                .thenReturn(Optional.empty());


        Optional<Price> result = priceService.getPrice(brandId, productId, applicationDate);


        assertFalse(result.isPresent());
    }

    @Test
    void getPriceAt10amOn15th() {

        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);

        Price expectedPrice = Price.builder()
                .id(3L)
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.of(2020, 6, 15, 0, 0))
                .endDate(LocalDateTime.of(2020, 6, 15, 11, 0))
                .priority(1)
                .price(BigDecimal.valueOf(30.50))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(expectedPrice));
        when(priceServiceImpl.getApplicablePrice(List.of(expectedPrice)))
                .thenReturn(Optional.of(expectedPrice));


        Optional<Price> result = priceService.getPrice(brandId, productId, applicationDate);


        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
    }

    @Test
    void getPriceAt9pmOn16th() {

        Long brandId = 1L;
        Long productId = 35455L;
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);

        Price expectedPrice = Price.builder()
                .id(4L)
                .brandId(brandId)
                .productId(productId)
                .startDate(LocalDateTime.of(2020, 6, 15, 16, 0))
                .endDate(LocalDateTime.of(2020, 12, 31, 23, 59))
                .priority(1)
                .price(BigDecimal.valueOf(38.95))
                .currency("EUR")
                .build();

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(expectedPrice));
        when(priceServiceImpl.getApplicablePrice(List.of(expectedPrice)))
                .thenReturn(Optional.of(expectedPrice));


        Optional<Price> result = priceService.getPrice(brandId, productId, applicationDate);


        assertTrue(result.isPresent());
        assertEquals(expectedPrice, result.get());
    }

    @Test
    void noPricesAvailable_shouldReturnEmptyOptional() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 17, 10, 0); // Date not covered by any price
        Long brandId = 1L;
        Long productId = 35455L;

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of());

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.empty(), actualPrice);
    }

    @Test
    void invalidBrandOrProductId_shouldReturnEmptyOptional() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long brandId = 99L; // Nonexistent brand
        Long productId = 99999L; // Nonexistent product

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
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

        when(priceRepository.findByBrandIdProductIdAndDate(brandId, productId, applicationDate))
                .thenReturn(List.of(price1, price2));
        when(priceServiceImpl.getApplicablePrice(List.of(price1, price2)))
                .thenReturn(Optional.of(price1));

        Optional<Price> actualPrice = priceService.getPrice(brandId, productId, applicationDate);

        assertEquals(Optional.of(price1), actualPrice);
    }
}
