package com.uet.busmanagement.config;

import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class logger {
    private static final Logger logger = Logger.getLogger("UETBusManagementLogger");

    static {
        try {
            // Logs store karne ke liye folder banana
            File logDir = new File("logs");
            if (!logDir.exists()) logDir.mkdir();

            // Logger File Handler configuration (logs/app.log file banegi)
            FileHandler fileHandler = new FileHandler("logs/app.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);

            logger.info("Logger system initialized successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Logger getLogger() {
        return logger;
    }
}