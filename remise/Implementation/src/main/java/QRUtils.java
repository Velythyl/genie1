import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Utilitary class for QR codes. In the future (in real life), will contain methods to read them too.
 */
public class QRUtils {
    /**
     * Generates a QR code image for a given String
     *
     * @param id the string
     */
    public static void genQR(String id) {
        try(FileOutputStream foo = new FileOutputStream("QRCODE.jpg")) {
            QRCode.from(id).to(ImageType.JPG).writeTo(foo);
        } catch (IOException e) {
            System.out.println("Quelque chose s'est mal passé avec l'écriture du code QR vers votre ordinateur. Avons-nous le write-access?");
        }
    }
}
