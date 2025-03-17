import java.util.Scanner;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入两个素数 p 和 q
        System.out.println("3121004646陈星君\n请输入两个素数 p 和 q：");
        BigInteger p = getPrimeFromUser(scanner, "p");
        BigInteger q = getPrimeFromUser(scanner, "q");

        // 计算 n 和 φ(n)
        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        // 随机生成 e
        BigInteger e = generateRandomE(phi);

        // 计算 d
        BigInteger d = e.modInverse(phi);

        // 显示公钥和私钥
        System.out.println("公钥 <e, n>：" + "<" + e + ", " + n + ">");
        System.out.println("私钥 <d, n>：" + "<" + d + ", " + n + ">");

        // 用户输入明文
        System.out.println("请输入要加密的消息（数字）：");
        BigInteger plaintext = scanner.nextBigInteger();

        // 加密
        BigInteger ciphertext = plaintext.modPow(e, n);
        System.out.println("加密后的消息：" + ciphertext);

        // 解密
        BigInteger decryptedText = ciphertext.modPow(d, n);
        System.out.println("解密后的消息：" + decryptedText);

        scanner.close();
    }

    // 从用户输入获取素数
    private static BigInteger getPrimeFromUser(Scanner scanner, String primeName) {
        BigInteger prime;
        do {
            System.out.print("请输入素数 " + primeName + "：");
            while (!scanner.hasNextBigInteger()) {
                System.out.println("请输入一个有效的整数！");
                scanner.next();
            }
            prime = scanner.nextBigInteger();
        } while (!isPrime(prime));

        return prime;
    }

    // 判断一个数是否为素数
    private static boolean isPrime(BigInteger num) {
        return num.isProbablePrime(100); // 使用概率素数测试
    }

    // 随机生成 e
    private static BigInteger generateRandomE(BigInteger phi) {
        BigInteger e;
        SecureRandom random = new SecureRandom();

        do {
            e = new BigInteger(phi.bitLength(), random);
        } while (e.compareTo(BigInteger.ONE) <= 0 || e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE));

        return e;
    }
}
