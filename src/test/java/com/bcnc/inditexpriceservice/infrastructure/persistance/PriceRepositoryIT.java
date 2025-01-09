package com.bcnc.inditexpriceservice.infrastructure.persistance;

import com.bcnc.inditexpriceservice.domain.model.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class PriceRepositoryIT {

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void testFindByBrandIdAndProductIdAndDate() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndDate(1L, 35455L, applicationDate);

        assertEquals(1, prices.size(), "Should return one applicable price.");
        assertEquals(2L, prices.getFirst().getPriceList(), "Should return the correct price list.");
    }
}