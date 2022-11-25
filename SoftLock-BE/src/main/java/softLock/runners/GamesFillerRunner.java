package softLock.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import softLock.helper.IgdbHelper;

@Component
public class GamesFillerRunner implements CommandLineRunner {

    @Autowired
    IgdbHelper igdbHelper;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("START RUNNER");
        igdbHelper.gamesFiller();
        System.out.println("END RUNNER");
    }


}
