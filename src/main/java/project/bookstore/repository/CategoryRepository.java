package project.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.bookstore.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
