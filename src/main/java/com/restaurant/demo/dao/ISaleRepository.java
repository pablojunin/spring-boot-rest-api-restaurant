package com.restaurant.demo.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.restaurant.demo.model.Sale;

public interface ISaleRepository extends JpaRepository<Sale, Long> {

	@Query("Select c from Sale c where c.date = :currentDate")
	List<Sale> getCurrentSales(@Param("currentDate") LocalDate currentDate);
}
