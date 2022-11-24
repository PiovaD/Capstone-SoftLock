package SoftLock.Exceptions;

public class ByIdNotFoundException extends Throwable {
    public ByIdNotFoundException(String entity, Long id) {
        super(entity + " with id: " + id + " not found");
    }
}
