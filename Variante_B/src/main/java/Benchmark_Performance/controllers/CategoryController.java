package Benchmark_Performance.controllers;

import Benchmark_Performance.entities.Category;
import Benchmark_Performance.entities.Item;
import Benchmark_Performance.repositories.CategoryRepository;
import Benchmark_Performance.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<Category> getAll(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return categoryRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(category);
    }

    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
        try {
            Category savedCategory = categoryRepository.save(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> update(@PathVariable Long id, @RequestBody Category category) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        category.setId(id);
        Category updatedCategory = categoryRepository.save(category);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        categoryRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<Item>> getItemsByCategory(@PathVariable Long id,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        if (!categoryRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        List<Item> items = itemRepository.findByCategoryId(id, PageRequest.of(page, size));
        return ResponseEntity.ok(items);
    }
}