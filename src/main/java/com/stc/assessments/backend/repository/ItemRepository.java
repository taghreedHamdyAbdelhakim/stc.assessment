package com.stc.assessments.backend.repository;

import com.stc.assessments.backend.model.Item;
import com.stc.assessments.backend.model.enums.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
     Item findByName(String name);
     List<Item> findByType(ItemType itemType );
}
