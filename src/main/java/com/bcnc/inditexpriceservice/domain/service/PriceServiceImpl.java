package com.bcnc.inditexpriceservice.domain.service;

import com.bcnc.inditexpriceservice.domain.model.Price;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class PriceServiceImpl {

    /**
     * Determines the applicable price based on the application date and given list of prices.
     *
     * @param prices           The list of price objects to evaluate.
     * @param applicationDate  The date and time for which the applicable price is determined.
     * @return The applicable price or null if no valid price exists.
     */
    public Optional<Price> getApplicablePrice(List<Price> prices, LocalDateTime applicationDate) {
        return prices.stream()
                .filter(price -> isWithinRange(price, applicationDate))
                .max(Comparator.comparingInt(Price::getPriority));
    }

    /**
     * Checks if the application date falls within the start and end dates of the price.
     *
     * @param price            The price object to evaluate.
     * @param applicationDate  The date to check.
     * @return True if the date is within the range, false otherwise.
     */
    private boolean isWithinRange(Price price, LocalDateTime applicationDate) {
        return !applicationDate.isBefore(price.getStartDate()) && !applicationDate.isAfter(price.getEndDate());
    }
}
