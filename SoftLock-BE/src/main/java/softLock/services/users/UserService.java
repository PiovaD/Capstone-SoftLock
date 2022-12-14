package softLock.services.users;

import softLock.entities.users.Role;
import softLock.entities.users.RoleType;
import softLock.entities.users.User;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.exceptions.ByRoleFoundException;
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
    RoleService roleService;

    @Autowired
    PasswordEncoder encoder;

    /**
     * Method to save and persist in db a User entity
     *
     * @param user New User to add
     * @return The entity saved in the DB
     */
    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.addRole(roleService.getByRole(RoleType.ROLE_USER));
        return rep.save(user);
    }

    /**
     * Get All Users, return an iterable of Users
     *
     * @return All users in the DB
     */
    public Iterable<User> getAllUsers() {
        return rep.findAll();
    }

    /**
     * Get All Users, return a pageable of users for lighter payloads
     *
     * @param p Pageable(page, size, sort)
     * @return The users in the DB paginated
     */
    public Page<User> getAllUsersPageable(Pageable p) {
        return rep.findAll(p);
    }

    /**
     * Find by id, if id is non-existent throws an exception
     *
     * @param id User ID
     * @return The corresponding user
     */
    public User findById(Long id) throws ByIdNotFoundException {
        Optional<User> found = rep.findById(id);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByIdNotFoundException("User", id);
    }

    /**
     * Find by username, if username is non-existent throws an exception
     *
     * @param username User username
     * @return The corresponding user
     */
    public User findByUsername(String username) throws ByNameNotFoundException {
        Optional<User> found = rep.findByUsernameAllIgnoreCase(username);
        if (found.isPresent()) {
            return found.get();
        }
        throw new ByNameNotFoundException("User", username);
    }

    /**
     * update all parameters except passwords and roles
     *
     * @param updatedUser Updated user
     * @return The entity updated in the DB
     */
    public User update(User updatedUser) throws ByIdNotFoundException {
        User oldUser = findById(updatedUser.getId());

        oldUser.setName(updatedUser.getName());
        oldUser.setLastName(updatedUser.getLastName());
        oldUser.setUsername(updatedUser.getUsername());
        oldUser.setEmail(updatedUser.getEmail());

        oldUser.setProfilePicUrl(updatedUser.getProfilePicUrl());

        return rep.save(oldUser);
    }

    /**
     * just update the password
     *
     * @param user Updated user
     * @return The entity updated in the DB
     */
    public User updatePassword(User user) throws ByIdNotFoundException {
        User oldUser = findById(user.getId());

        oldUser.setPassword(encoder.encode(user.getPassword()));

        return rep.save(oldUser);
    }

    /**
     * just update the profile pic
     *
     * @param user Updated user
     * @return The entity updated in the DB
     */
    public User updateProfilePic(User user) throws ByIdNotFoundException {
        User oldUser = findById(user.getId());

        oldUser.setProfilePicUrl(user.getProfilePicUrl());

        return rep.save(oldUser);
    }

    /**
     * Adds only one role
     *
     * @param roleType Role type
     * @param user     The reference user
     * @return The entity updated in the DB
     */
    public User addRole(User user, RoleType roleType) throws ByIdNotFoundException, ByRoleFoundException {
        User oldUser = findById(user.getId());

        Role role = roleService.getByRole(roleType);

        if (role != null) {
            oldUser.addRole(role);
        } else {
            throw new ByRoleFoundException("Role", roleType);
        }

        return rep.save(oldUser);
    }


    /**
     * Updates the roles based on the set present in the updated user
     *
     * @param user Updated user
     * @return The entity updated in the DB
     */
    public User setRoles(User user) throws ByIdNotFoundException {
        User oldUser = findById(user.getId());

        oldUser.setRoles(user.getRoles());

        return rep.save(oldUser);

    }

    /**
     * Remove only one role
     *
     * @param roleType   Role type
     * @param user The reference user
     * @return The entity updated in the DB
     */
    public User removeRole(User user, RoleType roleType) throws ByIdNotFoundException, ByRoleFoundException {
        User oldUser = findById(user.getId());

        Role role = roleService.getByRole(roleType);

        if (role != null) {
            oldUser.removeRole(role);
        } else {
            throw new ByRoleFoundException("Role", roleType);
        }

        return rep.save(oldUser);
    }

    /**
     * throws IllegalArgumentException
     *
     * @param user The user to delete
     * @return string with response
     */
    public String deleteUser(User user) throws ByIdNotFoundException {

        User u = findById(user.getId());
        u.setProfilePicUrl(null);
        u.setEmail(null);
        u.setActive(false);
        u.setRoles(null);
        u.setName("User Deleted");
        u.setLastName("");

        rep.save(u);

        return "";
    }
}

