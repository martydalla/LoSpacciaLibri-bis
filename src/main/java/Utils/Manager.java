package Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;


/*FUNZIONI UTILI*/

public class Manager {
    public static ImageIcon resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        /*La funzione converte da Buffered image a ImageIcon scalando*/
        ImageIcon inputImmagine = new ImageIcon(originalImage);
        ImageIcon outputImmagine = new ImageIcon(inputImmagine.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT));
        return outputImmagine;
    }

    public static InputStream bufferedImageToInputStream(BufferedImage immagine) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(immagine, "png", output);
        InputStream input = new ByteArrayInputStream(output.toByteArray());
        return input;
    }

    public static BufferedImage inputStreamToBufferedImage(InputStream input) throws IOException {
        return ImageIO.read(input);
    }

    public static InputStream blobToInputStream(Blob blob) throws SQLException {
        return blob.getBinaryStream(1, blob.length());
    }
}
