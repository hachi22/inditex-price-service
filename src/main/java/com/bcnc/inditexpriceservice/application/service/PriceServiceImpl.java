package com.bcnc.inditexpriceservice.application.service;

import com.bcnc.inditexpriceservice.domain.model.Price;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class PriceServiceImpl {

    /**
     * Determines the applicable price from a list of prices based on priority.
     *
     * @param prices the list of prices
     * @return an Optional containing the applicable Price or empty if no prices exist
     */
    public Optional<Price> getApplicablePrice(List<Price> prices) {
        return prices.stream()
                .max(Comparator.comparingInt(Price::getPriority));
    }
}
