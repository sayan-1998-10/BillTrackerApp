package com.project.tracker.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tracker.entity.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

	List<Bill> findByName(String name);
	
	@Transactional
	@Modifying
	@Query("UPDATE Bill SET BillExpense = :newPrice WHERE ID = :billId")
	void updateBillExpense(Long billId, BigDecimal newPrice);
	
}
