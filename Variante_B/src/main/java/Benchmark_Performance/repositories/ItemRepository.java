package Benchmark_Performance.repositories;

import Benchmark_Performance.entities.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategoryId(Long categoryId, Pageable pageable);
    boolean existsById(Long id);}