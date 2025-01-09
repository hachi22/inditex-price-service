package com.bcnc.inditexpriceservice.web.controller;

import com.bcnc.inditexpriceservice.application.dto.PriceDTO;
import com.bcnc.inditexpriceservice.application.service.PriceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "Get applicable price", description = "Fetch the applicable price based on date, product, and brand.")
    @GetMapping
    public PriceDTO getPrice(
            @RequestParam @Parameter LocalDateTime applicationDate,
            @RequestParam @Parameter Long productId,
            @RequestParam @Parameter Long brandId) {
        return priceService.getApplicablePrice(applicationDate, productId, brandId);
    }
}
