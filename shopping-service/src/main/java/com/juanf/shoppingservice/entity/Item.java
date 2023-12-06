package com.juanf.shoppingservice.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Entity
@Data
@Table(name = "items")
public class Item  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Positive(message = "La cantidad debe ser mayor que cero")
    private Double quantity;
    private Double  price;

    @Column(name = "book_id")
    private Long bookId;


    @Transient
    private Double subTotal;


    public Double getSubTotal(){
        if (this.price >0  && this.quantity >0 ){
            return this.quantity * this.price;
        }else {
            return (double) 0;
        }
    }
    public Item(){
        this.quantity=(double) 0;
        this.price=(double) 0;

    }
}
