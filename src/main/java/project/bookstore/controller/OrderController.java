package project.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.bookstore.dto.OrderItemResponseDto;
import project.bookstore.dto.OrderRequestDto;
import project.bookstore.dto.OrderResponseDto;
import project.bookstore.dto.StatusDto;
import project.bookstore.model.User;
import project.bookstore.service.OrderService;

@Tag(name = "Order management", description = "endpoints for managing orders")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private static final String ADMIN = "ROLE_ADMIN";
    private static final String USER = "ROLE_USER";

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "endpoint for submit your order and clear your shopping cart")
    @Secured(USER)
    public OrderResponseDto save(@RequestBody
                                 @Valid OrderRequestDto requestDto,
                                 Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.saveOrder(user.getId(), requestDto.getShippingAddress());
    }

    @GetMapping
    @Operation(summary = "endpoint for get your own order history ")
    @Secured(USER)
    public List<OrderResponseDto> findAll(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.findAllOrdersHistoryByUser(user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items")
    @Operation(summary = "endpoint for get order items by particular order")
    @Secured(USER)
    public List<OrderItemResponseDto> findByOrderId(@PathVariable
                                                    @Parameter(description = "Order id")
                                                    Long orderId,
                                                    Authentication authentication,
                                                    Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.findOrderItemsByOrderId(orderId, user.getId(), pageable);
    }

    @GetMapping("/{orderId}/items/{orderItemId}")
    @Operation(summary = "endpoint for get particular order item from particular order")
    @Secured(USER)
    public OrderItemResponseDto findParticularOrderItemByIdAndOrderId(@PathVariable
                                              @Parameter(description = "Order id")
                                              Long orderId,
                                              @PathVariable
                                              @Parameter(description = "order Item id")
                                              Long orderItemId,
                                              Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.findParticularOrderItemByOrderId(orderId, orderItemId, user.getId());
    }

    @PatchMapping("/{orderId}")
    @Operation(summary = "endpoint for Admin for change status of order")
    @Secured(ADMIN)
    public OrderResponseDto changeStatus(@PathVariable
                                         @Parameter(description = "Order id")
                                         Long orderId,
                                         @RequestBody
                                         @Parameter(schema =
                                         @Schema(implementation = StatusDto.class))
                                         StatusDto statusDto) {
        return orderService.changeOrderStatus(orderId, statusDto);
    }
}
