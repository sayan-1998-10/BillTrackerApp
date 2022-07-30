package com.project.tracker.dtos;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.project.tracker.entity.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillDto {
	String name;
	BigDecimal totalExpense;
	Date date;
	List<Item> items;
	Long userId;
}
