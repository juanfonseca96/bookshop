package com.juanf.shoppingservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanf.shoppingservice.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    public List<Invoice> findByCustomerId(Long customerId );
    public Invoice findByNumberInvoice(String numberInvoice);
}
