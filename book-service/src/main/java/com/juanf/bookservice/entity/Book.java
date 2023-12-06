package com.juanf.bookservice.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "El nombre del campo no debe ser vacío")
	private String name;
	private String author;
	@Positive(message = "El stock debe ser mayor que cero")
	private double stock;
	@Positive(message = "El stock debe ser mayor que cero")
	private double price;
	private String status;
	@Column(name = "create_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt;
	
	@NotNull(message = "El género no puede ser vacío")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "genre_id")
	@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
	private Genre genre;

}
