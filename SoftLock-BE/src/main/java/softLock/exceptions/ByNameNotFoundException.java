package softLock.exceptions;

public class ByNameNotFoundException extends Throwable {
    public ByNameNotFoundException(String entity, String name) {
        super(entity + " with name: " + name + " not found");
    }
}
