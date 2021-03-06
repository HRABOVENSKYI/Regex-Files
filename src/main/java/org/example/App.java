package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class App {
    public static void main(String[] args) {
        // If file is in root of project, you can just write its name
        String FILE_LOCATION1 = "D:\\intellij-idea-projects\\RegularExpressions\\access_log_Jul95";
        String FILE_LOCATION2 = "D:\\English\\text.txt";

        /*
          Print file
         */
//        printFile(FILE_LOCATION1);

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

        /*
          Most repeated word
        */
        printMap(findCountOfWordsInFIle(FILE_LOCATION2));
    }

    public static int getCountOfExpressionsUsingRegExAndContains(String FILE_LOCATION, String dateTimeRegEx, String date) {
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

    public static int getCountOfExpressionsUsingRegEx(String fileLocation, String regEx, String ipAddress) {
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

    public static int getCountOfExpressionsUsingContains(String fileLocation, String ipAddress) {
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

    public static void printFile(String fileLocation) {
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

    public static Map<String, String> findCountOfWordsInFIle(String fileLocation) {

        String line, word = "";
        int count = 0, maxCount = 0;
        ArrayList<String> words = new ArrayList<>();
        Map<String, String> wordMap = new HashMap<>();

        try {
            // Opens file in read mode
            FileReader file = new FileReader(fileLocation);
            BufferedReader br = new BufferedReader(file);

            //Reads each line
            while ((line = br.readLine()) != null) {
                String[] wordArr = line.toLowerCase().split("([,.*/\\-\\s\\d]+)");
                //Adding all words generated in previous step into words
                Collections.addAll(words, wordArr);
            }

            while (!words.isEmpty()) {
                String currentWord = words.get(0);
                int currentWordCount = 1;
                for (int i = 1; i < words.size(); i++) {
                    if (words.get(i).equals(currentWord)) {
                        currentWordCount++;
                    }
                }
                wordMap.put(currentWord, String.format("%d", currentWordCount));
                while (words.contains(currentWord))
                    words.remove(currentWord);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordMap;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map, boolean reverse) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        if (reverse)
            list.sort(Collections.reverseOrder(Map.Entry.comparingByValue())); // DESC
        else
            list.sort(Map.Entry.comparingByValue()); // ASC

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public static void printMap(Map<String, String> map) {
        String sortedAndFormattedMap = sortByValue(map, false).entrySet().stream()
                .map(e -> "Word: " + e.getKey() + "\t\t" + "Count: " + e.getValue())
                .collect(Collectors.joining("\n"));
        System.out.println(sortedAndFormattedMap);
    }
}
