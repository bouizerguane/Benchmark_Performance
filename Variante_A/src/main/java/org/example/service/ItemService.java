package org.example.service;

import org.example.dao.ItemDao;
import org.example.model.Item;

import java.util.List;

public class ItemService {

    private final ItemDao itemDao = new ItemDao();

    public boolean save(Item item) {
        return itemDao.save(item);
    }

    public List<Item> findAll() {
        return itemDao.findAll();
    }

    public Item findById(Long id) {
        return itemDao.findById(id);
    }

    public boolean update(Item item) {
        return itemDao.update(item);
    }

    public boolean deleteById(Long id) {
        return itemDao.deleteById(id);
    }

    public List<Item> getItemsByCategory(Long categoryId) {
        return itemDao.findByCategoryId(categoryId);
    }

}
