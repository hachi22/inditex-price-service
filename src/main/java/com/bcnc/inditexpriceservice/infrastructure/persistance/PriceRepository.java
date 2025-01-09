package com.bcnc.inditexpriceservice.infrastructure.persistance;

import com.bcnc.inditexpriceservice.domain.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    /**
     * Finds prices by brand, product, and application date.
     *
     * @param brandId         the brand ID
     * @param productId       the product ID
     * @return a list of prices matching the criteria
     */
    @Query("SELECT p FROM Price p WHERE p.brandId = :brandId AND p.productId = :productId " +
            "AND :applicationDate BETWEEN p.startDate AND p.endDate")
    List<Price> findByBrandIdProductIdAndDate(@Param("brandId") Long brandId,
                                              @Param("productId") Long productId);

}
