import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class QRUtils {
    public static void genQR(String id) {
        try(FileOutputStream foo = new FileOutputStream("QRCODE.jpg")) {
            QRCode.from(id).to(ImageType.JPG).writeTo(foo);
        } catch (IOException e) {
            System.out.println("Quelque chose s'est mal passé avec l'écriture du code QR vers votre ordinateur. Avons-nous le write-access?");
        }
    }

    public static void genQR(int id) {
        genQR(Integer.toString(id));
    }

    /**
     * Lis l'image QRCODE_INPUT.jpg et retourne l'int qui y a été encodé.
     *
     * Fortement inspiré de ceci, https://www.callicoder.com/qr-code-reader-scanner-in-java-using-zxing/ mais il n'y a
     * pas quarante façons de coder cela...
     *
     * @return Le int
     */
    public static int readQR() {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File("QRCODE.jpg"));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            return Integer.parseInt(result.getText());

        } catch (IOException | NotFoundException e) {
            System.out.println("Il n'y a pas de code QR dans l'image. Veuillez rééssayer.");
            return -1;
        }
    }
}
