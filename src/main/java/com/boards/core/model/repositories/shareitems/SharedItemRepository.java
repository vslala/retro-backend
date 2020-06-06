package com.boards.core.model.repositories.shareitems;

import com.boards.core.model.entities.shareitems.SharedItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SharedItemRepository extends CrudRepository<SharedItem, String> {
    List<SharedItem> findAllByItemId(String itemId);
}
