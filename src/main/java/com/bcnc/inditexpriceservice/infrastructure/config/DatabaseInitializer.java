package com.bcnc.inditexpriceservice.infrastructure.config;

import com.bcnc.inditexpriceservice.domain.model.Price;
import com.bcnc.inditexpriceservice.infrastructure.persistance.PriceRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner loadData(PriceRepository priceRepository) {
        return inserts -> {

            priceRepository.save(Price.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 14, 0, 0,0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59,59))
                    .priceList(1L)
                    .productId(35455L)
                    .priority(0)
                    .price(new BigDecimal("35.50"))
                    .currency("EUR")
                    .build());

            priceRepository.save(Price.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 14, 15, 0,0))
                    .endDate(LocalDateTime.of(2020, 6, 14, 18, 30,0))
                    .priceList(2L)
                    .productId(35455L)
                    .priority(1)
                    .price(new BigDecimal("25.45"))
                    .currency("EUR")
                    .build());

            priceRepository.save(Price.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 15, 0, 0,0))
                    .endDate(LocalDateTime.of(2020, 6, 15, 11, 0,0))
                    .priceList(3L)
                    .productId(35455L)
                    .priority(1)
                    .price(new BigDecimal("30.50"))
                    .currency("EUR")
                    .build());

            priceRepository.save(Price.builder()
                    .brandId(1L)
                    .startDate(LocalDateTime.of(2020, 6, 15, 16, 0,0))
                    .endDate(LocalDateTime.of(2020, 12, 31, 23, 59,59))
                    .priceList(4L)
                    .productId(35455L)
                    .priority(1)
                    .price(new BigDecimal("38.95"))
                    .currency("EUR")
                    .build());

            System.out.println("Data inserted successfully.");
        };
    }
}
