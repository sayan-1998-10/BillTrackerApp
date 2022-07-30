package com.project.tracker.services.interfaces;

import java.math.BigDecimal;
import java.util.List;

import com.project.tracker.entity.Bill;

public interface BillService {
	
	Bill fetchBillById(Long Id);
	
	List<Bill> fetchAllBills();
	
	String createBill(Long userId,Bill newBill);

	List<Bill> findBillByName(String name);
	
	String deleteBillById(Long id);
	
	void updateBillExpense(BigDecimal itemPrice, Bill bill);
	
	void modifyBill(Long billId,Bill bill);
}
