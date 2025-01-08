package com.bcnc.inditexpriceservice.application.service;

import com.bcnc.inditexpriceservice.domain.model.Price;
import com.bcnc.inditexpriceservice.infrastructure.persistance.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService {

    private final PriceRepository priceRepository;
    private final PriceServiceImpl priceServiceImpl;

    public PriceService(PriceRepository priceRepository, PriceServiceImpl priceServiceImpl) {
        this.priceRepository = priceRepository;
        this.priceServiceImpl = priceServiceImpl;
    }

    /**
     * Retrieves the applicable price for a product.
     *
     * @param brandId         the brand ID
     * @param productId       the product ID
     * @param applicationDate the application date
     */
    public Price getPrice(Long brandId, Long productId, LocalDateTime applicationDate) {

        List<Price> prices = priceRepository.findByBrandIdProductIdAndDate(brandId, productId);
        return priceServiceImpl.getApplicablePrice(prices, applicationDate);
    }
}
