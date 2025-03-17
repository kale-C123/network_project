import java.io.*;
import java.util.*;

public class CaesarCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("欢迎使用凯撒密码加密解密程序");

        while (true) {
            System.out.println("请选择操作:");
            System.out.println("1. 加密文本");
            System.out.println("2. 解密文本");
            System.out.println("3. 退出程序");
            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    System.out.println("请输入要加密的文本:");
                    String plainText = scanner.nextLine();
                    System.out.println("请输入密钥 (1-25):");
                    int key = scanner.nextInt();
                    scanner.nextLine(); // 消耗换行符

                    String encryptedText = encrypt(plainText, key);
                    System.out.println("加密后的文本: " + encryptedText);
                    displayFrequencyStatistics(plainText, encryptedText);
                    break;

                case 2:
                    System.out.println("请输入要解密的文本:");
                    String cipherText = scanner.nextLine();
                    System.out.println("请输入密钥 (1-25):");
                    key = scanner.nextInt();
                    scanner.nextLine(); // 消耗换行符

                    String decryptedText = decrypt(cipherText, key);
                    System.out.println("解密后的文本: " + decryptedText);
                    displayFrequencyStatistics(cipherText, decryptedText);
                    break;

                case 3:
                    System.out.println("感谢使用，再见!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("无效的选择，请重新输入.");
                    break;
            }
        }
    }

    public static String encrypt(String text, int key) {
        StringBuilder encryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isLowerCase(c) ? 'a' : 'A';
                int offset = c - base;
                char encryptedChar = (char) (((offset + key) % 26) + base);
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    public static String decrypt(String text, int key) {
        return encrypt(text, 26 - key);
    }

    public static void displayFrequencyStatistics(String originalText, String modifiedText) {
        Map<Character, Integer> originalFrequency = calculateFrequency(originalText);
        Map<Character, Integer> modifiedFrequency = calculateFrequency(modifiedText);

        System.out.println("字符频率统计:");
        System.out.println("原始文本频率统计:");
        displayFrequency(originalFrequency);

        System.out.println("加密/解密后的文本频率统计:");
        displayFrequency(modifiedFrequency);
    }

    public static Map<Character, Integer> calculateFrequency(String text) {
        Map<Character, Integer> frequencyMap = new TreeMap<>();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
            }
        }
        return frequencyMap;
    }

    public static void displayFrequency(Map<Character, Integer> frequencyMap) {
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}