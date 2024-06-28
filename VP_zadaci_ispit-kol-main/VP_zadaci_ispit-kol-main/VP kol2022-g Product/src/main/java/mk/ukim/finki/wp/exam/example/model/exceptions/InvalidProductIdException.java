package mk.ukim.finki.wp.exam.example.model.exceptions;

public class InvalidProductIdException extends RuntimeException {
    public InvalidProductIdException(Long id) {
        super(String.format("No product with Id = %d found.", id));
    }
}
