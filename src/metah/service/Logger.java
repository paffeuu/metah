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
    private final List<String> content;

    private Logger(String name) {
        this.name = name;
        this.content = new ArrayList<>();
    }

    public void log(String newContent) {
        this.content.add(newContent);
    }

    public void writeToFile() {
        File file = new File("log\\" + name + ".log");
        try (FileWriter fw = new FileWriter(file)) {
            for (String line : content) {
                fw.write(line);
            }
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
