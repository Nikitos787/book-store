package project.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.dto.CartItemRequestDto;
import project.bookstore.dto.ShoppingCartResponseDto;
import project.bookstore.dto.ShoppingCartUpdateQuantityRequestDto;
import project.bookstore.model.User;
import project.bookstore.service.ShoppingCartService;

@Tag(name = "ShoppingCart management", description = "endpoints for managing shopping carts")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String USER = "ROLE_USER";

    private final ShoppingCartService shoppingCartService;

    @PostMapping
    @Operation(summary = "endpoint for add book to shopping cart")
    @Secured({ADMIN, USER})
    public ShoppingCartResponseDto addBookToShoppingCart(
            @RequestBody
            @Parameter(schema = @Schema(implementation = CartItemRequestDto.class))
            CartItemRequestDto cartItemRequestDto,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        cartItemRequestDto.setShoppingCartId(user.getId());
        return shoppingCartService.addCartItemToShoppingCart(cartItemRequestDto);
    }

    @GetMapping
    @Operation(summary = "endpoint for getting own shopping cart")
    @Secured({ADMIN, USER})
    public ShoppingCartResponseDto getMyShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.findByUser(user.getId());
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "endpoint for update quantity of book in shopping cart")
    @Secured({ADMIN, USER})
    public ShoppingCartResponseDto updateQuantity(
            @PathVariable
            @Parameter(description = "Cart item id")
            Long cartItemId,
            @Parameter(schema =
            @Schema(implementation = ShoppingCartUpdateQuantityRequestDto.class))
            @RequestBody
            ShoppingCartUpdateQuantityRequestDto dto,
            Authentication authentication) {
        shoppingCartService.updateCartItemQuantity(cartItemId, dto);
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.findByUser(user.getId());
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "endpoint for remove book from shopping cart")
    @Secured({ADMIN, USER})
    public ShoppingCartResponseDto removeBookFromShoppingCart(
            @PathVariable
            @Parameter(description = "Cart item id") Long cartItemId,
            Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.removeBookFromShoppingCart(cartItemId,
                user.getId());
    }
}
