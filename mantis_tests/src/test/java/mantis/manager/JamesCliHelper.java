package mantis.manager;

import org.openqa.selenium.io.CircularOutputStream;
import org.openqa.selenium.os.CommandLine;

public class JamesCliHelper extends HelperBase {
    public JamesCliHelper(ApplicationManager appManager) {
        super(appManager);
    }

    public void addUser(String email, String password) {
        CommandLine cmd = new CommandLine(
                "java", "-cp", "\"james-server-jpa-app.lib/*\"",
                "org.apache.james.cli.ServerCmd", "AddUser", email, password);
        CircularOutputStream out = new CircularOutputStream();
        cmd.setWorkingDirectory(appManager.getProperty("james.workdir"));
        cmd.copyOutputTo(out);
        cmd.execute();
        cmd.waitFor();
        System.out.println(out);
    }
}
