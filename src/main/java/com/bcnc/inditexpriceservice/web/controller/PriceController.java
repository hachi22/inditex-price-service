package com.bcnc.inditexpriceservice.web.controller;

import com.bcnc.inditexpriceservice.application.dto.PriceDTO;
import com.bcnc.inditexpriceservice.application.service.PriceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
    private final PriceService priceService;

    public PriceController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public PriceDTO getPrice(
            @RequestParam LocalDateTime applicationDate,
            @RequestParam Long productId,
            @RequestParam Long brandId) {
        return priceService.getApplicablePrice(applicationDate, productId, brandId);
    }
}
