package com.juanf.customerservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanf.customerservice.entity.Customer;
import com.juanf.customerservice.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class CustomerController{

    @Autowired
    CustomerService customerService;

    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> listAllCustomers() {
        //List<Customer> customers =  new ArrayList<>();
        List<Customer> customers = customerService.findCustomerAll();
		if(customers.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.ok(customers);
    }

    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") long id) {
        log.info("Buscando cliente con id {}", id);
        Customer customer = customerService.getCustomer(id);
        if (  null == customer) {
            log.error("Cliente con id {} no encontrado.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(customer);
    }

    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        log.info("Creando cliente : {}", customer);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

       Customer customerDB = customerService.createCustomer (customer);

        return  ResponseEntity.status( HttpStatus.CREATED).body(customerDB);
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable("id") long id, @RequestBody Customer customer) {
        log.info("Actualizando cliente con id {}", id);

        Customer currentCustomer = customerService.getCustomer(id);

        if ( null == currentCustomer ) {
            log.error("Unable to update. Customer with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        customer.setId(id);
        currentCustomer=customerService.updateCustomer(customer);
        return  ResponseEntity.ok(currentCustomer);
    }

    @DeleteMapping("/customers/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable("id") long id) {
        log.info("Buscando y eliminando cliente con id {}", id);

        Customer customer = customerService.getCustomer(id);
        if ( null == customer ) {
            log.error("No se puede eliminar. Cliente con id {} no encontrado.", id);
            return  ResponseEntity.notFound().build();
        }
        customer = customerService.deleteCustomer(customer);
        return  ResponseEntity.ok(customer);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;

                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}