package SoftLock.Services;

import SoftLock.Entities.Role;
import SoftLock.Exceptions.ByIdNotFoundException;
import SoftLock.Repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RoleRepository repo;

    public List<Role> getAll() {
        return repo.findAll();
    }

    public Page<Role> getAllPaginate(Pageable p) {
        return repo.findAll(p);
    }

    public Role getById(Long id) throws ByIdNotFoundException {
        Optional<Role> found = repo.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("Role", id);

    }

    public void save(Role r) {
        repo.save(r);
    }

    public String delete(Long id) {

        repo.deleteById(id);

        return "Role deleted successfully.";

    }

}
