package com.juanf.customerservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
@Table(name="customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El número de identificación no puede ser vacío")
    @Size( min = 8 , max = 8, message = "El tamaño del número de identificación es 8")
    @Column(name = "run" , unique = true ,length = 8, nullable = false)
    private String run;

    @NotEmpty(message = "El nombre no debe ser vacío")
    @Column(name="first_name", nullable=false)
    private String firstName;

    @NotEmpty(message = "El apellido no debe ser vacío")
    @Column(name="last_name", nullable=false)
    private String lastName;
    
    @NotNull
    @Min(20000000)
    @Max(99999999)
    private Integer phone;

    @NotEmpty(message = "el correo no debe estar vacío")
    @Email(message = "no es un dirección de correo bien formada")
    @Column(unique=true, nullable=false)
    private String email;
    
    //@Value("CREATED")
    private String state;

}