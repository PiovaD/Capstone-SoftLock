package softLock.Configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import softLock.entities.users.Role;
import softLock.entities.users.User;
import softLock.helper.IgdbHelper;
import softLock.services.users.RoleService;
import softLock.services.users.UserService;

@Component
@Slf4j
public class SetupRunner implements CommandLineRunner {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SetupConfig.class);
    @Autowired
    RoleService rs;
    @Autowired
    UserService us;
    @Autowired
    IgdbHelper igdbHelper;

    @Override
    public void run(String... args) throws Exception {
        log.warn("START SETUP RUNNER");
        //igdbHelper.gamesFiller();
        //createData();
        log.warn("END SETUP RUNNER");
    }


    private void createData() {

        rs.save(ctx.getBean("rAdmin", Role.class));
        rs.save(ctx.getBean("rUser", Role.class));
        rs.save(ctx.getBean("rGamer", Role.class));
        rs.save(ctx.getBean("rPress", Role.class));
        rs.save(ctx.getBean("rInfluencer", Role.class));
        rs.save(ctx.getBean("rDev", Role.class));

        us.save(ctx.getBean("user1", User.class));

    }
}
