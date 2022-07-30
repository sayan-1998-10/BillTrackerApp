package com.project.tracker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.tracker.dtos.BillDto;
import com.project.tracker.dtos.ItemDto;
import com.project.tracker.entity.Bill;
import com.project.tracker.entity.Item;
import com.project.tracker.exception.ResourceNotFoundException;
import com.project.tracker.exception.ErrorDto;
import com.project.tracker.services.interfaces.BillService;
import com.project.tracker.services.interfaces.ItemService;

@Validated
@RestController
@RequestMapping("bills")
public class BillController {
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private ItemService itemService;
	
	
	//fetch a bill by its ID
	@GetMapping("{id}")
	public BillDto fetchAnyBillById(@PathVariable Long id) {
		Bill bill = billService.fetchBillById(id);
		BillDto response = new BillDto();
		response.setName(bill.getName());
		response.setDate(bill.getDate());
		response.setTotalExpense(bill.getTotalExpense());
		List<Item> items = new ArrayList<Item>();

		bill.getItems().forEach(item->{
			item.setBill(null);
			items.add(item);
		});
		response.setItems(items);
		return response;
	}
	
	//fetch all the bills
	@GetMapping()
	public List<BillDto> fetchAllBills(@RequestParam(required = false) String name){
		List<Bill> bills = (name==null || name.isBlank()) ? billService.fetchAllBills() : billService.findBillByName(name.trim());
		List<BillDto> response = new ArrayList<BillDto>();
		
		bills.forEach(b ->{
			BillDto billDTO = new BillDto();
			billDTO.setName(b.getName());
			billDTO.setDate(b.getDate());
			billDTO.setTotalExpense(b.getTotalExpense());
			List<Item> items = new ArrayList<Item>();
			b.getItems().forEach(i ->{
				i.setBill(null);
				items.add(i);
			});
			billDTO.setItems(items);
			response.add(billDTO);
		});
		
		
		return response;
	}
	
	//add a new bill
	@PostMapping()
	public String createBill(@RequestBody @Valid BillDto newBill) {
		Bill billEntity = new Bill();
		BeanUtils.copyProperties(newBill, billEntity);
		return billService.createBill(newBill.getUserId(),billEntity);
	}
	
	//delete a bill
	@DeleteMapping("{id}/delete-bill")
	public String removeBillById(@PathVariable Long id) {
		return billService.deleteBillById(id);
	}

	//Modify bill
	@PutMapping("{id}/update-bill")
	public void updateBill(@PathVariable("id") Long billId, @RequestBody Bill bill) {
		billService.modifyBill(billId, bill);
	}
	
	//search a particular item in a bill
	@GetMapping("{billId}/items/{itemId}/delete-item")
	public ItemDto findItemByIdInBill(@PathVariable("billId") Long billId,@PathVariable("itemId") Long itemId) {
		Item item = itemService.findItemByIdInBill(billId, itemId);
		ItemDto response = new ItemDto();
		response.setCategory(item.getCategory());
		response.setDescription(item.getDescription());
		response.setItemName(item.getItemName());
		response.setPrice(item.getPrice());
		response.setBill(null);
		return response;
	}
	
	//delete an item from a bill
	@DeleteMapping("{billId}/items/{itemId}")
	public String deleteItemById(@PathVariable("billId") Long billId,@PathVariable("itemId") Long itemId) {
		return itemService.deleteItemById(billId, itemId);
	}

}
