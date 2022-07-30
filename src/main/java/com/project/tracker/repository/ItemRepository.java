package com.project.tracker.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.project.tracker.entity.Bill;
import com.project.tracker.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	
	List<Item> findByItemName(String name);
	
	@Modifying
	@Transactional
	@Query("DELETE FROM Item WHERE BillId = :billId AND Id = :itemId")
	public Integer deleteByItemId(Long billId,Long itemId); 
	
	@Query("FROM Item WHERE Id = :itemId AND BillID = :billId")
	public Item findByItemIdAndBillId(Long billId,Long itemId);
}
