package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        // If file is in root of project, you can just write its name
        String FILE_LOCATION = "D:\\intellij-idea-projects\\RegularExpressions\\access_log_Jul95";

        /*
          Print file
         */
        printFile(FILE_LOCATION);

        /*
          IP Address
         */
        // To see RegEx in good format, you should paste it right in Pattern.compile()
        String ipAddressRegEx = "\\d{2,3}\\.\\d{2,3}\\.\\d{2,3}\\.\\d{2,3}";
        String ipAddress = "205.189.154.54";
        // Slow
//        System.out.println(getCountOfExpressionsUsingRegEx(FILE_LOCATION, ipAddressRegEx, ipAddress));
        // Faster
//        System.out.println(getCountOfExpressionsUsingContains(FILE_LOCATION, ipAddress));

        /*
          Date and time
         */
        String dateTimeRegEx = "\\[\\d{2}/\\p{Upper}\\p{Lower}{2,3}/\\d{4}:\\d{2}:\\d{2}:\\d{2} -\\d*\\]";
        String date = "01/Jul/1995";
        // Slow, but accurate
//        System.out.println(getCountOfExpressionsUsingRegExAndContains(FILE_LOCATION, dateTimeRegEx, date));
        // Faster, but less accurate (neglect the moment when dateTime is in URL)
//        System.out.println(getCountOfExpressionsUsingContains(FILE_LOCATION, date));
    }

    private static int getCountOfExpressionsUsingRegExAndContains(String FILE_LOCATION, String dateTimeRegEx, String date) {
        try {
            FileReader fileReader = new FileReader(FILE_LOCATION);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(dateTimeRegEx);
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (matcher.group().contains(date)) {
                        count++;
                    }
                }
            }

            fileReader.close();

            return count;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private static int getCountOfExpressionsUsingRegEx(String fileLocation, String regEx, String ipAddress) {
        try {
            FileReader fileReader = new FileReader(fileLocation);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                Pattern pattern = Pattern.compile(regEx);
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    if (matcher.group().equals(ipAddress))
                        count++;
                }
            }

            fileReader.close();

            return count;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static int getCountOfExpressionsUsingContains(String fileLocation, String ipAddress) {
        try {
            FileReader fileReader = new FileReader(fileLocation);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            int count = 0;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(ipAddress)) {
                    count++;
                }
            }

            fileReader.close();

            return count;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static void printFile(String fileLocation) {
        try {
            FileReader fileReader = new FileReader(fileLocation);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            fileReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
