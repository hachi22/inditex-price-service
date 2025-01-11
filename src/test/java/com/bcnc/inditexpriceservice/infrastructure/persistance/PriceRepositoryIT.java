package com.bcnc.inditexpriceservice.infrastructure.persistance;

import com.bcnc.inditexpriceservice.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PriceRepositoryIT {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void shouldFindPriceWithinGivenDateRange() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndDate(1L, 35455L, applicationDate);

        assertFalse(prices.isEmpty(), "Should find at least one price.");
        assertEquals(2L, prices.size(), "Should return the correct price list.");
        assertTrue(prices.getFirst().getStartDate().isBefore(applicationDate) ||
                prices.getFirst().getStartDate().isEqual(applicationDate), "Start date should be before or equal to application date.");
        assertTrue(prices.getFirst().getEndDate().isAfter(applicationDate) ||
                prices.getFirst().getEndDate().isEqual(applicationDate), "End date should be after or equal to application date.");
    }

    @Test
    void shouldNotFindPriceOutsideDateRange() {
        LocalDateTime applicationDate = LocalDateTime.of(2019, 12, 31, 23, 59);
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndDate(1L, 35455L, applicationDate);

        assertTrue(prices.isEmpty(), "Should not find any price outside the date range.");
    }

    @Test
    void shouldReturnMultiplePricesIfOverlapping() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 15, 0);
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndDate(1L, 35455L, applicationDate);

        assertEquals(2, prices.size(), "Should return multiple prices if overlapping.");
        assertTrue(prices.stream().allMatch(price ->
                price.getStartDate().isBefore(applicationDate) ||
                        price.getStartDate().isEqual(applicationDate)), "All prices should have valid start dates.");
        assertTrue(prices.stream().allMatch(price ->
                price.getEndDate().isAfter(applicationDate) ||
                        price.getEndDate().isEqual(applicationDate)), "All prices should have valid end dates.");
    }

    @Test
    void shouldFindHighestPriorityPrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndDate(1L, 35455L, applicationDate);

        Price highestPriorityPrice = prices.stream()
                .max(Comparator.comparingInt(Price::getPriority))
                .orElseThrow(() -> new AssertionError("No prices found."));

        assertEquals(2L, highestPriorityPrice.getPriceList(), "Should return the price with the highest priority.");
        assertEquals(1, highestPriorityPrice.getPriority(), "The highest priority should be 1.");
    }

    @Test
    void shouldHandleEmptyResultGracefully() {
        LocalDateTime applicationDate = LocalDateTime.of(2022, 1, 1, 0, 0);
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndDate(1L, 35455L, applicationDate);

        assertNotNull(prices, "Prices list should not be null.");
        assertTrue(prices.isEmpty(), "Prices list should be empty for a non-matching query.");
    }
}