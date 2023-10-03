package com.sse.models.repositories;

import com.sse.models.aggregates.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, String> {
}
