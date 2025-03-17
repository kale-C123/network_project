import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;


public class Hash {

    public static void main(String[] args) {
        ArrayList<File> filepath = new ArrayList<>();
        ArrayList<String> MD5value = new ArrayList<>();
        File fl  = new File("D:\\Hash测试文件");
        File[] file = fl.listFiles();
        open(file,filepath,MD5value);
        checkRepeat(filepath,MD5value);
    }

    public static void checkRepeat( ArrayList<File> filepath, ArrayList<String> MD5value){
        for (int i = 0; i < MD5value.size(); i++) {
            String str = MD5value.get(i);
            for (int j = i+1; j < MD5value.size(); j++) {
                if (str.equals(MD5value.get(j))){
                    System.out.println("重复文件路径为：");
                    System.out.println(filepath.get(i));
                    System.out.println(filepath.get(j));
                    System.out.println();
                }
            }
        }
    }

    public static void fileTraverse(File files, ArrayList<File> filepath, ArrayList<String> MD5value) {
        // 创建文件数组
        File[] arrFile = files.listFiles();
        ArrayList<File> filepaths = filepath;
        ArrayList<String> MD5values = MD5value;
        // 遍历文件数组（增强for循环）
        for (File file : arrFile) {
            // 进行判断，判断遍历出的文件是否为文件目录
            if (file.isDirectory()) {
                // 打印出文件的绝对路径
                filepath.add(new File(file.getAbsolutePath()));
                try {
                    MD5value.add(md5HashCode(file.getAbsolutePath()));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //System.out.println("这是目录：" + file.getAbsolutePath());
                // 调用文件遍历的方法，再次遍历此文件目录下的文件
                fileTraverse(file ,filepaths, MD5values);
            } else {
                // 打印所有文件
                //System.out.println("这是文件：" + file.getAbsolutePath());
                // 调用文件读取的方法
                //fileRead(file);
            }

        }
    }

    public static void open(File[] fl2, ArrayList<File> filepath, ArrayList<String> MD5value) {
        ArrayList<File> filepaths = filepath;
        ArrayList<String> MD5values = MD5value;
        try {
            //利用for循环，再依次判断文件类型，为一般文件则打印出来，为目录则重复调用。
            for (int i = 0; i < fl2.length; i++) {
                if (fl2[i].isFile()) {

                    filepaths.add(fl2[i]);
                    MD5values.add(md5HashCode(String.valueOf(fl2[i])));
                }else {
                    File[] fl3 = fl2[i].listFiles();
                    open(fl3,filepaths,MD5values);
                }
            }
        } catch (Exception e) {

        }
    }

    public static String md5HashCode(String filePath) throws FileNotFoundException{
        FileInputStream fis = new FileInputStream(filePath);
        return md5HashCode(fis);
    }

    public static String md5HashCode(InputStream fis) {
        try {
            //拿到一个MD5转换器,如果想使用SHA-1或SHA-256，则传入SHA-1,SHA-256
            MessageDigest md = MessageDigest.getInstance("MD5");

            //分多次将一个文件读入，对于大型文件而言，比较推荐这种方式，占用内存比较少。
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = fis.read(buffer, 0, 1024)) != -1) {
                md.update(buffer, 0, length);
            }
            fis.close();
            //转换并返回包含16个元素字节数组,返回数值范围为-128到127
            byte[] md5Bytes  = md.digest();
            BigInteger bigInt = new BigInteger(1, md5Bytes);//1代表绝对值
            return bigInt.toString(16);//转换为16进制
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}