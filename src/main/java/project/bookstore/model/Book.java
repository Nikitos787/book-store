package project.bookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "books")
@RequiredArgsConstructor
@Data
@SQLDelete(sql = "UPDATE books SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false, unique = true)
    private String isbn;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false, name = "cover_image")
    private String coverImage;
    @Column(name = "is_deleted")
    private boolean isDeleted = false;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_categories",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    @PrePersist
    void setIsbn() {
        isbn = UUID.randomUUID().toString();
    }
}
