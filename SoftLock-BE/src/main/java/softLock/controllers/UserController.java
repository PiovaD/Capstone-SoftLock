package softLock.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import softLock.entities.users.RoleType;
import softLock.entities.users.User;
import softLock.exceptions.ByIdNotFoundException;
import softLock.exceptions.ByNameNotFoundException;
import softLock.exceptions.ByRoleFoundException;
import softLock.services.users.UserService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200/")
public class UserController {

    @Autowired
    UserService serv;

    /*---------------------GET---------------------*/

    @GetMapping("")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        return new ResponseEntity<>(serv.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/pageable")
    public ResponseEntity<Page<User>> getAllUsersPageable(Pageable p) {
        Page<User> foundAll = serv.getAllUsersPageable(p);

        if (foundAll.hasContent()) {
            return new ResponseEntity<>(foundAll, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id")
    public ResponseEntity<User> findById(@RequestParam(name = "id") Long id) {
        try {
            return new ResponseEntity<>(serv.findById(id), HttpStatus.OK);
        } catch (ByIdNotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

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

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestBody User user) {
        try {
            return new ResponseEntity<>(serv.deleteUser(user), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            log.error("Error deleting user (id could be null): " + e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
