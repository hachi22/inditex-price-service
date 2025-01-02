package com.bcnc.inditexpriceservice.infrastructure.persistance;

import com.bcnc.inditexpriceservice.domain.model.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

}
