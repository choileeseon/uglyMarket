package com.myapp.uglyMarket.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Table(name = "pages")
@Data //get,set,toString 자동생성
public class Page {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "제목을 입력하세요")
	@Size(min = 2, message = "2자 이상.")
	private String title;
	
	private String slug;
	
	@NotBlank(message = "내용을 입력하세요")
	@Size(min = 3, message = "내용은 3자 이상.")
	private String content;
	
	private int sorting;
	
}
