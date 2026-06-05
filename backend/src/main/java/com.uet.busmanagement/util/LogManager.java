package com.uet.busmanagement.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager
{
    private static final String LOG_FILE = "system_logs.txt";

    public static void log(String message)
    {
        try (FileWriter fw = new FileWriter(LOG_FILE, true);
             BufferedWriter bw = new BufferedWriter(fw))
        {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            bw.write("[" + timestamp + "] - " + message);
            bw.newLine();
        }
        catch (IOException e)
        {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}