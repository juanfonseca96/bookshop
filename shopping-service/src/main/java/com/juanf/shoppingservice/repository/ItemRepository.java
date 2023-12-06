package com.juanf.shoppingservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.juanf.shoppingservice.entity.Item;

public interface ItemRepository extends JpaRepository<Item,Long> {
}

