package imageutils;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    private BufferedImage image;
    private Mat matrix;
    private ImageWriter imageWriter;
    private File file;
    private boolean isBlurred;
    private double blurFactor;

    public Image(File file, String outputDirectory) {
        try{
            this.file = file;
            this.image = ImageIO.read(file);
            this.matrix = Imgcodecs.imread(file.getPath());
            imageWriter = ImageWriter.getInstance(outputDirectory);
            System.out.println("Image is loaded");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public BufferedImage getImage() {
        return this.image;
    }

    public void calculateBlurFactor(double blueFactorThreshold) {
        Mat destination = new Mat();
        Mat matGray=new Mat();

        Imgproc.cvtColor(this.matrix, matGray, Imgproc.COLOR_BGR2GRAY);
        Imgproc.Laplacian(matGray, destination, 3);

        MatOfDouble median = new MatOfDouble();
        MatOfDouble std= new MatOfDouble();

        Core.meanStdDev(destination, median , std);

        this.blurFactor = Math.pow(std.get(0,0)[0],2);

        this.isBlurred = this.blurFactor < blueFactorThreshold;

        this.save(matrix, String.valueOf(this.isBlurred));
    }

    public void saveGrayscale() {
        Mat destination = new Mat();
        Imgproc.cvtColor(this.matrix, destination, Imgproc.COLOR_RGB2GRAY);

        //this.save(destination, "grayscale");
    }

    public void save(Mat outputMatrix, String operation) {
        imageWriter.saveImage(outputMatrix, operation + "-" + getFileNameWithoutExtension() + ".jpg");
    }

    public String getFileNameWithoutExtension() {
        int pos = file.getName().lastIndexOf(".");
        if (pos == -1) return file.getName();
        return file.getName().substring(0, pos);
    }

    @Override
    public String toString() {
        return "=========================================" + "\n" +
                "File Name: " + this.file.getName() + "\n" +
                "Blur Factor: " + this.blurFactor + "\n" +
                "Is Blurred: " + this.isBlurred + "\n" +
                "=========================================" + "\n" +
                "\n";
    }
}
