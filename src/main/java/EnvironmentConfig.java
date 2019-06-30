import org.apache.log4j.LogManager;

import javax.ws.rs.ServiceUnavailableException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

public class EnvironmentConfig {
    private final String databasePassword = "databasePassword";
    private final String manageServerUrl = "manageServerUrl";
    final private String server_environment = "server_environment";
    private final String filePostfix = "_file";
    private final String filePostfixCap = "_FILE";
    HashMap<String, String> serverEnvironment;

    public EnvironmentConfig() {
        readEnvironmentProperties();
    }

    private void readEnvironmentProperties() {

        String serverEnvironmentAddress;
        serverEnvironmentAddress = System.getProperty(this.server_environment);
        if (serverEnvironmentAddress == null) {
            serverEnvironmentAddress = System.getenv(this.server_environment);
        }
        if (serverEnvironmentAddress == null || serverEnvironmentAddress.isEmpty()) {
            throw new ServiceUnavailableException("server_environment missing");
        }
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(serverEnvironmentAddress));
        } catch (IOException e) {
            LogManager.getLogger(EnvironmentConfig.class).fatal(
                    "can not find server_environment file: " + serverEnvironmentAddress);
            throw new ServiceUnavailableException("server_environment missing", 100000l, e);
        }
        serverEnvironment = new HashMap(p);
        for (Map.Entry<Object, Object> keyObject : p.entrySet()) {
            String key = (String) keyObject.getKey();
            String value = (String) keyObject.getValue();
            String trimValue = "";
            if (value != null) trimValue = value.trim();
            if ((key.endsWith(filePostfix) || key.endsWith(filePostfixCap)) && !trimValue.isEmpty()) {
                String fileLocation = trimValue;
                if (trimValue.equals("true")) {
                    fileLocation = System.getenv(key);
                }
                if (fileLocation != null) {
                    try (Scanner s = new Scanner(new File(fileLocation))) {
                        String fileContent = s.nextLine();
                        serverEnvironment.put(key.substring(0, key.length() - filePostfix.length()), fileContent);
                    } catch (FileNotFoundException e) {
                        LogManager.getLogger(EnvironmentConfig.class).error("can not open file " + fileLocation + "to read" + key);
                    }
                }
            }
        }
    }

    public String get(String key) {
        return serverEnvironment.get(key);
    }

    public String getDatabasePassword() {
        return get(databasePassword);
    }

    public String getManageServerUrl() {
        return get(manageServerUrl);
    }
}
