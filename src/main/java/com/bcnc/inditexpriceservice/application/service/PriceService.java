package com.bcnc.inditexpriceservice.application.service;

import com.bcnc.inditexpriceservice.domain.model.dto.PriceDTO;
import com.bcnc.inditexpriceservice.domain.model.entity.Price;
import com.bcnc.inditexpriceservice.domain.service.PriceServiceImpl;
import com.bcnc.inditexpriceservice.infrastructure.persistance.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public PriceDTO getApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        List<Price> prices = priceRepository.findByBrandIdAndProductIdAndDate(brandId, productId, applicationDate);

        return priceServiceImpl.getApplicablePrice(prices, applicationDate)
                .map(price -> new PriceDTO(
                        price.getProductId(),
                        price.getBrandId(),
                        price.getPriceList(),
                        price.getStartDate(),
                        price.getEndDate(),
                        price.getPrice()))
                .orElseThrow(() -> new RuntimeException("No applicable price found"));
    }
}
