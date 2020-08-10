package imageutils;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Image {
    private BufferedImage image;
    private Mat matrix;
    private ImageWriter imageWriter;
    private File file;
    private boolean isBlurred;
    private double blurFactor;
    private double largestContourArea;

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

    public void getDocument(Size blurSize, int lowThresh, int ratio) {
        Random rng = new Random(12345);
        Mat blur = new Mat();
        Mat grayscale = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Mat cannyOutput = new Mat();
        Imgproc.blur(this.matrix, blur, blurSize);
        Imgproc.cvtColor(blur, grayscale, Imgproc.COLOR_RGB2GRAY);
        Imgproc.Canny(grayscale, cannyOutput, lowThresh, lowThresh * ratio);

        Mat hierarchy = new Mat();
        Imgproc.findContours(cannyOutput, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Mat drawing = Mat.zeros(cannyOutput.size(), CvType.CV_8UC3);
        if(!contours.isEmpty()) {
            System.out.println("Processing");
            int largestContourIndex = 0;
            largestContourArea = Imgproc.contourArea(contours.get(0));
            Moments moments = Imgproc.moments(contours.get(0));
//            System.out.println(moments.get);
            for (int i = 0; i < contours.size(); i++) {
//                double newArea = Imgproc.contourArea(contours.get(i));
//                if(largestContourArea < newArea) {
//                    largestContourIndex = i;
//                }

                Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
                Imgproc.drawContours(drawing, contours, i, color, 2);
            }

//            Scalar color = new Scalar(rng.nextInt(256), rng.nextInt(256), rng.nextInt(256));
//            Imgproc.drawContours(drawing, contours, largestContourIndex, color);

            this.save(drawing, "Edge-Detection");
        } else {
            System.out.println("Processing skipped: Contour size is " + contours.size());
        }
    }

    public void saveGrayscale() {
        Mat destination = new Mat();
        Imgproc.cvtColor(this.matrix, destination, Imgproc.COLOR_RGB2GRAY);

        //this.save(destination, "grayscale");
    }

    public void save(Mat outputMatrix, String operation) {
        imageWriter.saveImage(outputMatrix, operation + "-" + getFileNameWithoutExtension() + ".jpg");
    }

    public void save(java.awt.Image image, String operation) {
        imageWriter.saveImage(image, operation + "-" + getFileNameWithoutExtension() + ".jpg");
    }

    public String getFileNameWithoutExtension() {
        int pos = file.getName().lastIndexOf(".");
        if (pos == -1) return file.getName();
        return file.getName().substring(0, pos);
    }

    public void adjustBrightnessAndContrast(double contrastControl, double brightnessControl, String name) {
        Mat newImage = Mat.zeros(this.matrix.size(), this.matrix.type());
        byte[] imageData = new byte[(int) (this.matrix.total()*this.matrix.channels())];
        this.matrix.get(0, 0, imageData);
        byte[] newImageData = new byte[(int) (newImage.total()*newImage.channels())];
        for (int y = 0; y < this.matrix.rows(); y++) {
            for (int x = 0; x < this.matrix.cols(); x++) {
                for (int c = 0; c < this.matrix.channels(); c++) {
                    double pixelValue = imageData[(y * this.matrix.cols() + x) * this.matrix.channels() + c];
                    pixelValue = pixelValue < 0 ? pixelValue + 256 : pixelValue;
                    newImageData[(y * this.matrix.cols() + x) * this.matrix.channels() + c]
                            = saturate(contrastControl * pixelValue + brightnessControl);
                }
            }
        }
        newImage.put(0, 0, newImageData);
        imageWriter.saveImage(newImage, name + getFileNameWithoutExtension() + ".jpg");
//        HighGui.imshow(name, newImage);
    }

    private byte saturate(double val) {
        int iVal = (int) Math.round(val);
        iVal = iVal > 255 ? 255 : (Math.max(iVal, 0));
        return (byte) iVal;
    }

    @Override
    public String toString() {
        return "=========================================" + "\n" +
                "File Name: " + this.file.getName() + "\n" +
                "Blur Factor: " + this.blurFactor + "\n" +
                "Is Blurred: " + this.isBlurred + "\n" +
                "Largest Countour Area: " + this.largestContourArea + "\n" +
                "=========================================" + "\n" +
                "\n";
    }
}
