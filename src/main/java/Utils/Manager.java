package Utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;


/*FUNZIONI UTILI*/

public class Manager {

    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    public  static InputStream bufferedImageToInputStream (BufferedImage immagine) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(immagine,"jpeg",output);
        InputStream input =  new ByteArrayInputStream(output.toByteArray());
        return input;
    }

    public static BufferedImage inputStreamToBufferedImage (InputStream input) throws IOException {
        return  ImageIO.read(input);
    }

    public static InputStream blobToInputStream (Blob blob) throws SQLException {
        return blob.getBinaryStream(1,blob.length());
    }
}
