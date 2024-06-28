package mk.ukim.finki.wp.exam.example.model.exceptions;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(String username) {
        super(String.format("No user with username = %s found.", username));
    }
}
