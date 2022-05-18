/*
 * Copyright 2022 KCodeYT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ms.kevi.skyblock.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings("ALL")
public class Utils {

    private static final char[] UPPER_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] LOWER_ALPHABET = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] NUMBERS = "0123456789".toCharArray();

    private static final Map<Integer, String> ROMAN_MAP = MapUtil.create(new LinkedHashMap<>(), 100, "C", 90, "XC", 50, "L", 40, "XL", 10, "X", 9, "IX", 5, "V", 4, "IV", 1, "I");
    private static final String[] NUMBER_NAMES = new String[]{"k", "M", "B", "T"};

    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getInstance(Locale.US);

    private static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yy", Locale.US);
    private static final SimpleDateFormat MONTH_YEAR_DATE_FORMAT = new SimpleDateFormat("MMMMM yyyy", Locale.US);

    public static char randomAlphabeticChar(Random random) {
        return randomChar(random, UPPER_ALPHABET);
    }

    public static char randomChar(Random random, char[] array) {
        return array[random.nextInt(array.length)];
    }

    public static byte[] randomBytes(int length) {
        final byte[] bytes = new byte[length];
        ThreadLocalRandom.current().nextBytes(bytes);
        return bytes;
    }

    public static String randomString(int length) {
        final Random random = ThreadLocalRandom.current();
        final char[] chars = new char[length];
        for(int i = 0; i < length; i++)
            chars[i] = randomChar(random, random.nextBoolean() ? UPPER_ALPHABET : random.nextBoolean() ? LOWER_ALPHABET : NUMBERS);
        return new String(chars);
    }

    public static String toRoman(int number) {
        if(number > 128)
            return "TOO_HIGH_NUMBER";
        final StringBuilder romanNumber = new StringBuilder();
        while(number > 0) {
            for(int indexNumber : ROMAN_MAP.keySet()) {
                if(indexNumber <= number) {
                    number -= indexNumber;
                    romanNumber.append(ROMAN_MAP.get(indexNumber));
                    break;
                }
            }
        }
        return romanNumber.toString();
    }

    public static int fromRoman(String roman) {
        final char[] chars = roman.toCharArray();
        int value = 0;
        char lastChar = ' ';
        for(char aChar : chars) {
            final int charNumber = romanCharToInt(aChar);
            if(lastChar == ' ' || lastChar == aChar || romanCharToInt(lastChar) > charNumber)
                value += charNumber;
            else {
                value -= romanCharToInt(lastChar);
                if(romanCharToInt(lastChar) < charNumber)
                    value += charNumber - romanCharToInt(lastChar);
                else
                    value += charNumber + romanCharToInt(lastChar);
            }
            lastChar = aChar;
        }
        return value;
    }

    private static int romanCharToInt(char aChar) {
        return ROMAN_MAP.keySet().stream().filter(key -> ROMAN_MAP.get(key).equalsIgnoreCase("" + aChar)).findAny().orElse(0);
    }

    public static int mathInBetween(int i, int min, int max) {
        return i > max ? max : i < min ? min : i;
    }

    public static void writeFile(File file, List<String> lines) {
        final File parentFile;
        if(!file.exists() && !(parentFile = file.getParentFile()).exists() && !parentFile.mkdirs())
            return;

        try(final OutputStream outputStream = new FileOutputStream(file);
            final Writer writer = new OutputStreamWriter(outputStream);
            final BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            for(String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }

            bufferedWriter.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> readFile(File file) {
        if(!file.exists())
            return new ArrayList<>();
        final List<String> lines = new ArrayList<>();
        try(final InputStream inputStream = new FileInputStream(file);
            final Reader reader = new InputStreamReader(inputStream);
            final BufferedReader bufferedReader = new BufferedReader(reader)) {
            String line;
            while((line = bufferedReader.readLine()) != null)
                lines.add(line);
            bufferedReader.close();
            return lines;
        } catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static String readFileStrict(File file) {
        return String.join("", Utils.readFile(file).toArray(new String[0]));
    }

    public static BufferedImage readImageFile(File file) {
        try {
            return ImageIO.read(file);
        } catch(IOException e) {
            return new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        }
    }

    public static String shortenNumber(Number number) {
        final String string = NUMBER_FORMAT.format(number).split("\\.")[0];
        final String[] split = string.split(",");
        if(split.length < 2 || split.length > NUMBER_NAMES.length + 1)
            return string;
        final String name = NUMBER_NAMES[split.length - 2];
        final String first = split[0];
        if(first.length() < 2)
            return first + "." + split[1].charAt(0) + name;
        return first + name;
    }

    public static String roundNumber(double number, int digits) {
        return roundNumber(number, digits, false);
    }

    public static String roundNumber(double number, int digits, boolean signed) {
        return Utils.formatNumber((double) Math.round(number * Math.pow(10D, digits)) / Math.pow(10D, digits), signed);
    }

    public static String formatNumber(Number number) {
        return Utils.formatNumber(number, false);
    }

    public static String formatNumber(Number number, boolean signed) {
        return (signed && number.doubleValue() > 0.0 ? "+" : "") + NUMBER_FORMAT.format(number);
    }

    public static String formatDate(long timeMillis) {
        return MONTH_YEAR_DATE_FORMAT.format(new Date(timeMillis));
    }

    public static String currentDate() {
        return DEFAULT_DATE_FORMAT.format(new Date());
    }

    public static boolean equalsOrNull(Object a, Object b) {
        return a == null || a.equals(b);
    }

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }

}
