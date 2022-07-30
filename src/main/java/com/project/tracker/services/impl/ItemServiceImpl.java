package com.project.tracker.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.tracker.entity.Bill;
import com.project.tracker.entity.Item;
import com.project.tracker.exception.ResourceNotFoundException;
import com.project.tracker.repository.ItemRepository;
import com.project.tracker.services.interfaces.BillService;
import com.project.tracker.services.interfaces.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepo;
	
	@Autowired
	private BillService billService;
	
	@Override
	public String addItem(Item item,Long billId) {
		Bill bill =  billService.fetchBillById(billId);
		if(bill!=null) {
			billService.updateBillExpense(item.getPrice(), bill);
			item.setBill(bill);
			itemRepo.save(item);
			return "Item has been added to the bill";
		}
		else {
			throw new ResourceNotFoundException(String.format("No bill exists for the given id -> %d",billId));
		}
	}

	@Override
	public Item findItemById(Long itemId) {
		Optional<Item> item = itemRepo.findById(itemId);
		if(item.isPresent()) {
			return item.get();
		}
		else {
			throw new ResourceNotFoundException(String.format("Item not found with ID : %d", itemId));
		}
	}

	@Override
	public List<Item> findItemsInBill(Long billId) {
		Bill bill = billService.fetchBillById(billId);
		if(bill!=null) {
			return bill.getItems();
		}
		else {
			throw new ResourceNotFoundException(String.format("No bill exists for the given id -> %d",billId));
		}
	}

	@Override
	public Item findItemByIdInBill(Long billId, Long itemId) {
		Item item = itemRepo.findByItemIdAndBillId(billId, itemId);
		if(item==null) {
			throw new ResourceNotFoundException(String.format("Item not found with ID : %d", itemId));
		}
		return item;
	}

	@Override
	public String deleteItemById(Long billId, Long itemId) {
		Integer noOfRowsDeleted =  itemRepo.deleteByItemId(billId, itemId);
		return noOfRowsDeleted + " rows were deleted";
	}
	
	@Override
	public List<Item> findItemByName(String itemName) {
		List<Item> items = itemRepo.findByItemName(itemName);
		if(items.isEmpty()) {
			throw new ResourceNotFoundException(String.format("Not items exists with the provided name -> %s", itemName));
		}
		return items;
	}

	@Override
	public void updateItem(Long itemId,Item updatedItem) {
		Item item = itemRepo.findById(itemId).orElse(null);
		if(updatedItem!=null && item!=null) {
			if(!updatedItem.getItemName().equals(item.getItemName())) item.setItemName(updatedItem.getItemName());
			if(!updatedItem.getCategory().equals(item.getCategory())) item.setCategory(updatedItem.getCategory());
			if(!updatedItem.getDescription().equals(item.getDescription())) item.setDescription(updatedItem.getDescription());
			if(!updatedItem.getPrice().equals(item.getPrice())) item.setPrice(updatedItem.getPrice());
			
			itemRepo.save(item);
		}
		
		//update the bill expense
		Bill bill = billService.fetchBillById(item.getBill().getId());
		if(bill!=null) {
			billService.updateBillExpense(item.getPrice(), bill);
		}
	}
}
