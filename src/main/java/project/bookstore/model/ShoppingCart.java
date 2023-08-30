package project.bookstore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "shopping_carts")
@Data
@SQLDelete(sql = "UPDATE shopping_carts SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted = false")
public class ShoppingCart {
    @Id
    private Long id;
    @MapsId
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;
    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CartItem> cartItems;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
