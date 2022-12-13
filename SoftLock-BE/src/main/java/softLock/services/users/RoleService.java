package softLock.services.users;

import softLock.entities.users.Role;
import softLock.entities.users.RoleType;
import softLock.exceptions.ByIdNotFoundException;
import softLock.repositories.users.RoleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository rep;

    /**
     * @return All roles in the DB
     */
    public List<Role> getAll() {
        return rep.findAll();
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The roles in the DB paginated
     */
    public Page<Role> getAllPaginate(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * @param id Role ID
     * @return The corresponding role
     */
    public Role getById(Long id) throws ByIdNotFoundException {
        Optional<Role> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Role", id);
    }

    /**
     * @param rt role name
     * @return The corresponding role
     */
    public Role getByRole(RoleType rt) {
        Optional<Role> found = rep.findByRoleType(rt);
        return found.orElse(null);
    }

    /**
     * @param r new role
     */
    public void save(Role r) {
        if (getByRole(r.getRoleType()) == null) {
            rep.save(r);
        }
    }

    /**
     * @param id Role id
     */
    public void delete(Long id) {
        rep.deleteById(id);

    }

}
