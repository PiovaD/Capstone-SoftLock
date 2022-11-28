package softLock.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import softLock.entities.users.Role;
import softLock.entities.users.RoleType;
import softLock.entities.users.User;

@Configuration
public class SetupConfig {

    @Bean(name = "rAdmin")
    @Scope("singleton")
    public Role roleAdmin() {
        return new Role(RoleType.ROLE_ADMIN);
    }

    @Bean(name = "rUser")
    @Scope("singleton")
    public Role roleUser() {
        return new Role(RoleType.ROLE_USER);
    }
    @Bean(name = "rGamer")
    @Scope("singleton")
    public Role roleGamer() {
        return new Role(RoleType.ROLE_GAMER);
    }
    @Bean(name = "rPress")
    @Scope("singleton")
    public Role rolePress() {
        return new Role(RoleType.ROLE_PRESS);
    }
    @Bean(name = "rInfluencer")
    @Scope("singleton")
    public Role roleInfluencer() {
        return new Role(RoleType.ROLE_INFLUENCER);
    }
    @Bean(name = "rDev")
    @Scope("singleton")
    public Role roleDev() {
        return new Role(RoleType.ROLE_DEV);
    }

    @Bean(name = "user1")
    @Scope("singleton")
    public User user1() {
        User u = new User("ajeje", "Aldo", "Baglio", "dexter@garolfo.cops", "test");
        u.getRoles().add(roleDev());
        u.getRoles().add(roleAdmin());
        return u;
    }

}
