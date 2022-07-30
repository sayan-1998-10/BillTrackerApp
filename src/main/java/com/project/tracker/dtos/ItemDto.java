package com.project.tracker.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.project.tracker.entity.Bill;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemDto {
	
	@NotNull
	@NotBlank
	String itemName;
	
	@NotNull
	@NotBlank
	String category;
	
	String description;
	
	@NotNull
	BigDecimal price;
	
	Bill bill; 
	
	@NotNull
	Long billId;
}
