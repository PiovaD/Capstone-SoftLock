package softLock.services.users;

import softLock.entities.users.Role;
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

    public List<Role> getAll() {
        return rep.findAll();
    }

    public Page<Role> getAllPaginate(Pageable p) {
        return rep.findAll(p);
    }

    public Role getById(Long id) throws ByIdNotFoundException {
        Optional<Role> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Role", id);

    }

    public void save(Role r) {
        rep.save(r);
    }

    public String delete(Long id) {

        rep.deleteById(id);

        return "Role deleted successfully.";

    }

}