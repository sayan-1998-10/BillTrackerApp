package com.project.tracker.services.interfaces;

import java.util.List;

import com.project.tracker.entity.Item;

public interface ItemService {

	public String addItem(Item i,Long Bill_Id);
	public Item findItemById(Long Item_Id);
	public List<Item> findItemsInBill(Long Bill_id);
	public Item findItemByIdInBill(Long Bill_id,Long Item_Id);
	public String deleteItemById(Long billId,Long itemId);
	public List<Item> findItemByName(String itemName);
	public void updateItem(Long itemId,Item updatedItem);
}
