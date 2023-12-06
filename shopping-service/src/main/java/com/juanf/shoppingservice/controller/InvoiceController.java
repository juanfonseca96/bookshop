package com.juanf.shoppingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juanf.shoppingservice.entity.Invoice;
import com.juanf.shoppingservice.service.InvoiceService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @GetMapping("/invoices")
    public ResponseEntity<List<Invoice>> listAllInvoices() {
        List<Invoice> invoices = invoiceService.findInvoiceAll();
        if (invoices.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(invoices);
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoice(@PathVariable("id") long id) {
        log.info("Obtener factura con id {}", id);
        Invoice invoice  = invoiceService.getInvoice(id);
        if (null == invoice) {
            log.error("Factura con id {} no encontrada.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(invoice);
    }

    @PostMapping("/invoices")
    public ResponseEntity<Invoice> createInvoice(@Valid @RequestBody Invoice invoice, BindingResult result) {
        log.info("Creando factura : {}", invoice);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        Invoice invoiceDB = invoiceService.createInvoice (invoice);

        return  ResponseEntity.status( HttpStatus.CREATED).body(invoiceDB);
    }

    @PutMapping("/invoices/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable("id") long id, @RequestBody Invoice invoice) {
        log.info("Actualizando factura con id {}", id);

        invoice.setId(id);
        Invoice currentInvoice=invoiceService.updateInvoice(invoice);

        if (currentInvoice == null) {
            log.error("No se puede actualizar. Factura con id {} no encontrada.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(currentInvoice);
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Invoice> deleteInvoice(@PathVariable("id") long id) {
        log.info("Buscando y eliminando factura con id {}", id);

        Invoice invoice = invoiceService.getInvoice(id);
        if (invoice == null) {
            log.error("No se puede eliminar. Factura con id {} no nencontrada.", id);
            return  ResponseEntity.notFound().build();
        }
        invoice = invoiceService.deleteInvoice(invoice);
        return ResponseEntity.ok(invoice);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
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
