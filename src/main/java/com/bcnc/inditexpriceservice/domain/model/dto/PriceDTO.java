package com.bcnc.inditexpriceservice.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PriceDTO {

    private Long productId;
    private Long brandId;
    private Long priceList;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
}
