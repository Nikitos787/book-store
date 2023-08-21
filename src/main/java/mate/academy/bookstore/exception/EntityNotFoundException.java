package mate.academy.bookstore.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
