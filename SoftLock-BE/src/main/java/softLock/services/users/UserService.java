package softLock.services.users;

import softLock.entities.users.User;
import softLock.exceptions.ByIdNotFoundException;
import softLock.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository rep;

    @Autowired
    PasswordEncoder encoder;

    /**
     * Method to save and persist in db a User entity
     */
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return rep.save(user);
    }

    /**
     * Get All Users, return an iterable of Users
     */
    public Iterable<User> getAllUsers() {
        return rep.findAll();
    }

    /**
     * Get All Users, return a pageable of users for lighter payloads
     */
    public Page<User> getAllUsersPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     */
    public User findById(Long id) throws ByIdNotFoundException {
        Optional<User> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("User", id);
    }

    /**
     * update User
     */
    public User updateUser(User updatedUser) throws ByIdNotFoundException {
        rep.save(updatedUser);
        return updatedUser;
    }

    /**
     * throws IllegalArgumentException
     */
    public String deleteUser(Long id) {
        rep.deleteById(id);
        return "User delete successfully";
    }
}

