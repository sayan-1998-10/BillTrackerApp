package com.project.tracker.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String username;

	@Column(unique = true, nullable = false)
	private String emailAddress;
	
	@JsonIgnore
	@Column(nullable = false)
	private String password;
	
	private String phoneNumber;
	
	@OneToMany(mappedBy="user",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
//	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<Bill> bills;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Timestamp createdAt;
	
	@JsonIgnore
	@UpdateTimestamp
	private Timestamp updatedAt;
	
}
