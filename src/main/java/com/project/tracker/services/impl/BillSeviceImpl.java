package com.project.tracker.services.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.project.tracker.entity.Bill;
import com.project.tracker.entity.UserEntity;
import com.project.tracker.exception.ResourceNotFoundException;
import com.project.tracker.exception.ErrorDto;
import com.project.tracker.repository.BillRepository;
import com.project.tracker.repository.UserRepository;
import com.project.tracker.services.interfaces.BillService;

@Service
public class BillSeviceImpl implements BillService {

	@Autowired
	private BillRepository billRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public Bill fetchBillById(Long Id) {
		Optional<Bill> container = billRepo.findById(Id);
		if(container.isPresent()) {	
			return container.get();
		}
		else {
			throw new ResourceNotFoundException(String.format("No bill exists for the given id -> %d",Id));
		}
	}
	
	@Override
	public List<Bill> fetchAllBills() {
		return billRepo.findAll();
	}

	@Override
	public String createBill(Long userId,Bill bill) {
		UserEntity userEntity = this.userRepo.findById(userId).get();
		bill.setUser(userEntity);
		billRepo.save(bill);
		return String.format("New Bill has been added to user %s",userEntity.getUsername());
	}

	public List<Bill> findBillByName(String name){
		List<Bill> bills = billRepo.findByName(name);
		if(bills.isEmpty()) {
			throw new ResourceNotFoundException(String.format("No such bills exists by name of --> %s", name));
		}
		return bills;
	}
	
	@Override
	public String deleteBillById(Long id) {
		billRepo.deleteById(id);
		return MessageFormat.format("Bill {0} removed",id);
	}
	
	@Override
	public void updateBillExpense(BigDecimal itemPrice, Bill bill) {
		BigDecimal currExpense = bill.getTotalExpense();
		bill.setTotalExpense(currExpense.add(itemPrice));
		billRepo.save(bill);
		System.out.println("Updated Bill Price --> "+bill.getTotalExpense());
		
	}
	
	@Override
	public void modifyBill(Long billId, Bill modifiedBill) {
		Bill bill = fetchBillById(billId);
		if(bill!=null && modifiedBill!=null) {
			if(!bill.getName().equals(modifiedBill.getName())) bill.setName(modifiedBill.getName());
			if(!bill.getDate().equals(modifiedBill.getDate())) bill.setDate(modifiedBill.getDate());
			
			billRepo.save(bill);
		}
	}
}
