package softLock.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import softLock.entities.users.Role;
import softLock.entities.users.RoleType;
import softLock.entities.users.User;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.exceptions.ByRoleFoundException;
import softLock.services.users.RoleService;
import softLock.services.users.UserService;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    @Autowired
    UserService serv;

    @Autowired
    RoleService roleService;

    /*---------------------GET---------------------*/

    /**
     * @return All roles in the DB
     */
    @GetMapping("roles")
    public ResponseEntity<Iterable<Role>> getAllRoles() {
        return new ResponseEntity<>(roleService.getAll(), HttpStatus.OK);
    }

    /**
     * @return All users in the DB
     */
    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return new ResponseEntity<>(serv.getAllUsers(), HttpStatus.OK);
    }

    /**
     * @param p Pageable(page, size, sort)
     * @return The users in the DB paginated
     */
    @GetMapping("/pageable")
    public ResponseEntity<Page<User>> getAllUsersPageable(Pageable p) {
        Page<User> foundAll = serv.getAllUsersPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param id User ID
     * @return The corresponding user
     */
    @GetMapping("/id")
    public ResponseEntity<User> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(serv.findById(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * @param username User username
     * @return The corresponding user
     */
    @GetMapping("/username")
    public ResponseEntity<User> findByUsername(@RequestParam(name = "username") String username) {
        try {
            return new ResponseEntity<>(serv.findByUsername(username), HttpStatus.OK);
        } catch (ByNameNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /*---------------------POST---------------------*/

    /**
     * @param user New User to add
     * @return The entity saved in the DB
     */
    @PostMapping("/new")
    public ResponseEntity<User> createNewUser(@RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.save(user), HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error saving user: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*---------------------PUT---------------------*/

    /**
     * update all parameters except passwords and roles
     *
     * @param updatedUser Updated user
     * @return The entity updated in the DB
     */
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        try {
            return new ResponseEntity<>(serv.update(updatedUser), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating user: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * just update the password
     *
     * @param user Updated user
     * @return The entity updated in the DB
     */
    @PutMapping("/update-password")//TODO sicurezza login utente tramite jwt
    public ResponseEntity<User> updatePassword(@RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.updatePassword(user), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating user: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * just update the profile pic
     *
     * @param user Updated user
     * @return The entity updated in the DB
     */
    @PutMapping("/update-profile-pic")
    public ResponseEntity<User> updateProfilePic(@RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.updateProfilePic(user), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error updating user: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Updates the roles based on the set present in the updated user
     *
     * @param user Update user
     * @return The entity updated in the DB
     */
    @PutMapping("/set-roles")
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
    public ResponseEntity<User> setRoles(@RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.setRoles(user), HttpStatus.OK);

        } catch (ByIdNotFoundException e) {
            log.error("Error set roles: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Adds only one role
     *
     * @param rt   Role name
     * @param user The reference user
     * @return The entity updated in the DB
     */
    @PutMapping("/add-role/{rt}")
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
    public ResponseEntity<User> updateAddRole(@PathVariable RoleType rt, @RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.addRole(user, rt), HttpStatus.OK);

        } catch (ByIdNotFoundException | ByRoleFoundException e) {
            log.error("Error adding role: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /*---------------------DELETE---------------------*/

    /**
     * Remove only one role
     *
     * @param rt   Role name
     * @param user The reference user
     * @return The entity updated in the DB
     */
    @DeleteMapping("/remove-role/{rt}")
    @PreAuthorize("hasAnyRole('DEV', 'ADMIN')")
    public ResponseEntity<User> deleteRemoveRole(@PathVariable RoleType rt, @RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.removeRole(user, rt), HttpStatus.OK);

        } catch (ByIdNotFoundException | ByRoleFoundException e) {
            log.error("Error remove role: " + e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * @param user The user to delete
     * @return string with response
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.deleteUser(user), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error("Error deleting user (id could be null): " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error("Error deleting user (id could be null): " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
