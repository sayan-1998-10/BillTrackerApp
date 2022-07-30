package com.project.tracker.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.tracker.dtos.ItemDto;
import com.project.tracker.entity.Bill;
import com.project.tracker.entity.Item;
import com.project.tracker.services.interfaces.ItemService;

@RestController
@RequestMapping("items")
public class ItemController {

	@Autowired
	private ItemService itemService;
	
	//add an item to a bill
	@PostMapping
	public String addItemToBill(@RequestBody @Valid ItemDto item) {
		Item itemEntity = new Item();
		BeanUtils.copyProperties(item,itemEntity);
		return itemService.addItem(itemEntity, item.getBillId());
	}
	
	//search items by id
	@GetMapping("{id}")
	public ItemDto viewItemDetails(@PathVariable("id") Long itemId) {
		Item item = itemService.findItemById(itemId);
		
		ItemDto response = new ItemDto();
		response.setCategory(item.getCategory());
		response.setDescription(item.getDescription());
		response.setItemName(item.getItemName());
		response.setPrice(item.getPrice());
		
		Bill b = item.getBill();
		b.setItems(null);
		response.setBill(b);
		return response;
	}
	
	//search items by name
	@GetMapping()
	public List<ItemDto> findItemByName(@RequestParam(name = "itemName",required = false) String itemName) {
		List<Item> itemList = itemService.findItemByName(itemName);
		List<ItemDto> response = new ArrayList<ItemDto>();
		itemList.forEach(item->{
			ItemDto i = new ItemDto();
			i.setCategory(item.getCategory());
			i.setDescription(item.getDescription());
			i.setItemName(item.getItemName());
			i.setPrice(item.getPrice());
			Bill b = item.getBill();
			b.setItems(null);
			i.setBill(b);
			response.add(i);
		});
		return response;
	}
	
	//search all items in a specific bill
	@GetMapping("/bills/{id}/items")
	public List<ItemDto> viewAllItemsInBill(@PathVariable("id") Long billId){
		List<ItemDto> response = new ArrayList<ItemDto>();
		List<Item> items = itemService.findItemsInBill(billId);
		
		items.forEach(i ->{
			ItemDto item = new ItemDto();
			item.setCategory(i.getCategory());
			item.setDescription(i.getDescription());
			item.setItemName(i.getItemName());
			item.setPrice(i.getPrice());
			item.setBill(null);
			response.add(item);
		});
		
		return response;
	}
	

	//update an item [name,price,desc,category]
	@PutMapping("{id}/update")
	public void updateItem(@PathVariable("id") Long itemId, @RequestBody Item item) {
		itemService.updateItem(itemId, item);
	}

}
