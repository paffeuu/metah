package metah.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Logger {
    private static final Map<String, Logger> instances;

    static {
        instances = new HashMap<>();
    }

    public static Logger getLogger(String name) {
        Logger logger = instances.get(name);
        if (logger == null) {
            logger = new Logger(name);
            instances.put(name, logger);
        }
        return logger;
    }

    private final String name;
    private String instanceName;
    private final List<String> content;

    private Logger(String name) {
        this.name = name;
        this.content = new ArrayList<>();
    }

    public void log(String newContent) {
        this.content.add(newContent);
    }

    public void writeToFile() {
        File file = new File(getFileName());
        try (FileWriter fw = new FileWriter(file)) {
            for (String line : content) {
                fw.write(line);
            }
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileName() {
        StringBuilder sb = new StringBuilder();
        sb.append("log\\");
        sb.append(getTimeStamp());
        sb.append("_");
        sb.append(instanceName);
        sb.append("_");
        sb.append(name);
        sb.append(".log");
        return sb.toString();
    }

    private int getTimeStamp() {
        long currTimeMilis = System.currentTimeMillis();
        long currTimeSeconds = currTimeMilis / 1000L;
        long currTimeSecondInThisYear = currTimeSeconds % (60 * 60 * 24 * 365);
        return (int) currTimeSecondInThisYear;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }
}
