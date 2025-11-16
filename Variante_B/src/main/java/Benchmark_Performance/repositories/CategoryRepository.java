package Benchmark_Performance.repositories;

import Benchmark_Performance.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface CategoryRepository extends JpaRepository<Category, Long> {}

