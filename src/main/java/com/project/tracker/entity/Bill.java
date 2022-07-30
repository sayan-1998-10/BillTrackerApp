package com.project.tracker.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bill")
public class Bill {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@NotBlank(message = "Bill name cannot be empty!!")
	@Column(name="BillName")
	private String name;
	
	@NotNull(message = "Bill Creation Date cannot be null!!")
	@Column(name="BillDate")
	private Date date;
	
	@Column(name="BillExpense")
	private BigDecimal totalExpense = BigDecimal.ZERO;
	
	@CreationTimestamp
	@Column(name="created_at",nullable = false,updatable = false)
	private Timestamp createdAt;
	
	@JsonIgnore
	@UpdateTimestamp
	@Column(name="updated_at",updatable = true)
	private Timestamp updatedAt;
	
	@OneToMany(mappedBy = "bill",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Item> items;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private UserEntity user;
	
}
