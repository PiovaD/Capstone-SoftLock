package softLock.exceptions;

import softLock.entities.users.RoleType;

public class ByRoleFoundException extends Throwable {
    public ByRoleFoundException(String entity, RoleType roleType) {
        super(entity + " with role: " + roleType + " not found");
    }
}
