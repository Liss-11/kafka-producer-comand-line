package com.debuggeandoideas;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.setProperty("log4j.configurationFile","./path_to_the_log4j2_config_file/log4j2.xml");

        var kafkaProducer = Producer.getInstance();

        var scanner = new Scanner(System.in);
        do {
            var line = scanner.next();
            var isValidLine = validFormat(line);
            if (isValidLine || line.equals("exit")) {
                if (line.equals("exit")) {
                    log.info("Finish");
                    kafkaProducer.closeProducer();
                    break;
                }
                var keyAndMessage = line.split(":");
                kafkaProducer.send(keyAndMessage[0], keyAndMessage[1]);
            } else {
                log.error("The formant must be string:string");
            }
        } while (true);

    }

    private static Boolean validFormat(String line) {
        var regex = Pattern.compile("\\w+:\\w+");
        var matcher = regex.matcher(line);
        return matcher.matches();
    }

    private static final Logger log = LogManager.getLogger(Main.class);

}