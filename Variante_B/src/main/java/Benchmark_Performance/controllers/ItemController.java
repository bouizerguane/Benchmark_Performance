package Benchmark_Performance.controllers;

import Benchmark_Performance.entities.Category;
import Benchmark_Performance.entities.Item;
import Benchmark_Performance.repositories.CategoryRepository;
import Benchmark_Performance.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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
    public List<Item> getAllItems(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return itemRepository.findByCategoryId(categoryId);
        }
        return itemRepository.findAll(PageRequest.of(page, size)).getContent();
    }

    @GetMapping("/{id}")
    public Item getItem(@PathVariable Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
    }

    @PostMapping
    public Item createItem(@RequestBody Item item) {
        Category category = categoryRepository.findById(item.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        item.setCategory(category);
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public Item updateItem(@PathVariable Long id, @RequestBody Item itemData) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        item.setSku(itemData.getSku());
        item.setName(itemData.getName());
        item.setPrice(itemData.getPrice());
        item.setStock(itemData.getStock());

        Category category = categoryRepository.findById(itemData.getCategory().getId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        item.setCategory(category);

        return itemRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}
