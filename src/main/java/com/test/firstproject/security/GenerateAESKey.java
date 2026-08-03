//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import java.util.Base64;
//
//public class GenerateAESKey {
//
//    public static void main(String[] args) throws Exception {
//
//        KeyGenerator generator =
//                KeyGenerator.getInstance("AES");
//
//        generator.init(256);
//
//        SecretKey key =
//                generator.generateKey();
//
//        System.out.println(
//                Base64.getEncoder()
//                        .encodeToString(key.getEncoded())
//        );
//
//    }
//
//}