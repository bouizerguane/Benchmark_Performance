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
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public List<Item> getAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "10") int size,
                             @RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return itemRepository.findByCategoryId(categoryId, PageRequest.of(page, size));
        }
        return itemRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getById(@PathVariable Long id) {
        Item item = itemRepository.findById(id).orElse(null);
        if (item == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(item);
    }

    @PostMapping
    public ResponseEntity<Item> create(@RequestBody Item item) {
        try {
            // Vérifier que la catégorie existe
            if (item.getCategory() != null && item.getCategory().getId() != null) {
                Category category = categoryRepository.findById(item.getCategory().getId())
                        .orElseThrow(() -> new RuntimeException("Category not found"));
                item.setCategory(category);
            }

            Item savedItem = itemRepository.save(item);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item item) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Vérifier que la catégorie existe
        if (item.getCategory() != null && item.getCategory().getId() != null) {
            Category category = categoryRepository.findById(item.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            item.setCategory(category);
        }

        item.setId(id);
        Item updatedItem = itemRepository.save(item);
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}