package com.myapp.uglyMarket.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "이름을 입력하세요")
	private String name;
	
	private String slug;
	
	@NotBlank(message = "설명을 입력하세요")
	@Size(min = 3, message = "3자 이상")
	private String description;
	
	private String image;
	
	@Pattern(regexp = "^[1-9][0-9]*")
	private String price;
	
	@Pattern(regexp = "^[1-9][0-9]*", message = "카테고리를 선택해주세요")
	@Column(name = "category_id")
	private String categoryId;
	
	@Column(name = "created_at", updatable = false) //처음 생성할땐 업뎃이 안됨
	@CreationTimestamp		//생성될 때마다 자동생성(insert)
	private LocalDateTime createAt;
	
	@Column(name = "updated_at")  
	@UpdateTimestamp		//수정될 때마다 자동생성(update)
	private LocalDateTime updateAt;
	
	@NotBlank(message = "원산지를 입력하세요")
	@Size(min = 1, message = "1자 이상")
	private String origin;
	
}
