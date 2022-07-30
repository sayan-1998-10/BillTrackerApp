package com.project.tracker.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Item")
public class Item {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotNull
	@NotBlank
	private String itemName;
	
	@NotNull
	@NotBlank
	private String category;
	
	private String description;
	
	@NotNull
	private BigDecimal price;
	
	@CreationTimestamp
	@Column(name="created_at",nullable = false,updatable = false)
	private Timestamp createdAt;
	
	@UpdateTimestamp
	@JsonIgnore
	@Column(name="updated_at",updatable = true)
	private Timestamp updatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="billId")
	private Bill bill;
}
