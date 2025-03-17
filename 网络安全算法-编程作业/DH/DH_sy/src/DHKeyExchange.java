import java.util.ArrayList;
import java.util.Scanner;

public class DHKeyExchange {
    // 判断是否为素数
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (int i = 5; i * i <= n; i = i + 6) {
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        }
        return true;
    }

    // 计算最大公因子
    public static int GCD(int a, int b) {
        if (b == 0) return a;
        return GCD(b, a % b);
    }

    // 计算 b^n mod m
    public static int ExpMod(int b, int n, int m) {
        if (n == 0) return 1;

        long t = ExpMod(b, n / 2, m);
        long result = (t * t) % m;
        if (n % 2 == 1)
            result = (result * b) % m;

        return (int) result;
    }

    // 判断是否为模 p 的生成元
    public static boolean isPrimeRoot(int g, int p) {
        ArrayList<Integer> powers = new ArrayList<>();
        int phi = p - 1;

        for (int i = 1; i <= phi; i++) {
            powers.add(ExpMod(g, i, p));
        }

        powers.sort(Integer::compareTo);

        for (int i = 1; i <= phi; i++) {
            if (powers.get(i - 1) == powers.get(i % phi)) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("DH密钥交换协议示例3121004646陈星君");
        System.out.print("请输入一个素数 p（大于1）：");
        int p = scanner.nextInt();

        if (p <= 1 || !isPrime(p)) {
            System.out.println("输入不合法，请输入一个大于1的素数。");
            return;
        }

        System.out.print("请输入一个作为生成元的整数 g（小于 p）：");
        int g = scanner.nextInt();

        if (g >= p || g <= 1 || !isPrimeRoot(g, p)) {
            System.out.println("输入不合法，请输入小于 p 且符合要求的生成元。");
            return;
        }

        System.out.println("已选择素数 p = " + p + " 和生成元 g = " + g);

        System.out.print("请输入 Alice 的私有密钥：");
        int aPrivate = scanner.nextInt();

        System.out.print("请输入 Bob 的私有密钥：");
        int bPrivate = scanner.nextInt();

        // 计算 Alice 和 Bob 的公共密钥
        int aPublic = ExpMod(g, aPrivate, p);
        int bPublic = ExpMod(g, bPrivate, p);

        // 计算共享密钥
        int sharedKeyAlice = ExpMod(bPublic, aPrivate, p);
        int sharedKeyBob = ExpMod(aPublic, bPrivate, p);

        // 打印共享密钥
        System.out.println("Alice 的共享密钥：" + sharedKeyAlice);
        System.out.println("Bob 的共享密钥：" + sharedKeyBob);

        // 关闭 Scanner
        scanner.close();
    }
}
