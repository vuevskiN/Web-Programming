package mk.ukim.finki.wp.june2022.g1.model.exceptions;

public class InvalidUsernameException extends RuntimeException{
    public InvalidUsernameException() {
        super("No user with that username found.");
    }
}
