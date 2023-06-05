package com.onerivet.deskbook.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.onerivet.deskbook.models.entity.ColumnDetails;
import com.onerivet.deskbook.models.entity.SeatNumber;

public interface SeatNumberRepo extends JpaRepository<SeatNumber, Integer> {
	public List<SeatNumber> findByColumn(ColumnDetails column);
	
	@Query(value = "SELECT s.column Column FROM SeatNumber s  INNER JOIN s.column c INNER JOIN c.floor f INNER JOIN f.city ct WHERE s.id=:id")
	public Map<String, ColumnDetails> findColumnFloorCityBySeat(Integer id);
	
	//public SeatNumber findById(int seatId);
	
}
