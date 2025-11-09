package org.example.service;

import org.example.dao.CategoryDao;
import org.example.dao.ItemDao;
import org.example.model.Category;
import org.example.model.Item;

import java.util.List;

public class CategoryService {

    private final CategoryDao categoryDao = new CategoryDao();
    private final ItemDao itemDao = new ItemDao(); // ← pour récupérer les items

    public boolean save(Category category) {
        return categoryDao.save(category);
    }

    public List<Category> findAll(int page, int size) {
        return categoryDao.findAll(page, size);
    }

    public Category findById(Long id) {
        return categoryDao.findById(id);
    }

    public boolean update(Category category) {
        return categoryDao.update(category);
    }

    public boolean deleteById(Long id) {
        return categoryDao.deleteById(id);
    }

    public List<Item> getItemsByCategory(Long categoryId, int page, int size) {
        return itemDao.findByCategoryId(categoryId, page, size);
    }
}
