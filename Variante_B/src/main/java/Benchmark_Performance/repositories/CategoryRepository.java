package Benchmark_Performance.repositories;

import Benchmark_Performance.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {}

