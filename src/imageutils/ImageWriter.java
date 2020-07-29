package imageutils;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ImageWriter {
    private static ImageWriter imageWriter = null;

    private String directoryPath;

    private ImageWriter(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    public static ImageWriter getInstance(String directoryPath) {
        if(imageWriter == null) imageWriter = new ImageWriter(directoryPath);
        return imageWriter;
    }

    public boolean saveImage(Image image, String fileName) {
        try {
            File outputFile = new File(directoryPath + fileName);
            outputFile.createNewFile();

            javax.imageio.ImageIO.write(image.getImage(), "jpg", outputFile);
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public void saveImage(Mat matrix, String fileName) {
        Imgcodecs.imwrite(directoryPath + fileName, matrix);
    }
}
