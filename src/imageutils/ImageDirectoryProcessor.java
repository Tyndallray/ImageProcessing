package imageutils;

import imagesettings.ImageProcessSetting;
import org.opencv.highgui.HighGui;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImageDirectoryProcessor {
    private String inputDirectory;
    private String outputDirectory;
    private String reportDirectory;
    private List<Image> images;
    private ImageProcessSetting setting;

    public ImageDirectoryProcessor(String inputDirectory, String outputDirectory, String reportDirectory, ImageProcessSetting setting) {
        this.inputDirectory = inputDirectory;
        this.outputDirectory = outputDirectory;
        this.reportDirectory = reportDirectory;
        this.images = new ArrayList<>();
        this.setting = setting;
        initializeImages();
    }

    private void initializeImages() {
        File directory = new File(inputDirectory);
        for (File file: Objects.requireNonNull(directory.listFiles())) {
            Image image = new Image(file, outputDirectory);
            images.add(image);
        }
    }

    private void deleteAllProcessed() {
        File directory = new File(outputDirectory);
        for (File file: Objects.requireNonNull(directory.listFiles())) {
            file.delete();
        }
    }

    public void process() {
        System.out.println("===== Deleting old images =====");
        this.deleteAllProcessed();

//        System.out.println("===== Converting to grayscale =====");
//        this.convertToGrayscale();

//        System.out.println("===== Calculating blur factor =====");
//        this.calculateBlurFactor();

//        System.out.println("===== Contour detection =====");
//        this.getDocument();

        this.adjustBrightnessAndContrast();

        System.out.println("===== Saving report =====");
        this.saveReport();
    }

    private void convertToGrayscale() {
        images.forEach(Image::saveGrayscale);
    }

    private void calculateBlurFactor() {
        images.forEach(x -> x.calculateBlurFactor(setting.getBlurThreshold()));
    }

    private void adjustBrightnessAndContrast() {
        images.forEach(x -> x.adjustBrightnessAndContrast(1, 0, "Original"));
        images.forEach(x -> x.adjustBrightnessAndContrast(2, -150, "Contrast 1"));
        images.forEach(x -> x.adjustBrightnessAndContrast(3, -350, "Contrast 2"));
//        HighGui.waitKey();
    }

    private void getDocument() { images.forEach(x -> x.getDocument(setting.getBlurSize(), setting.getLowThreshold(), setting.getRatio()));}

    public void saveReport() {
        try {
            String output = "";
            output += setting.toString();
            for (Image image: images) {
                output += image.toString();
            }

            File report = new File(reportDirectory + "report.txt");
            report.createNewFile();

            BufferedWriter writer = new BufferedWriter(new FileWriter(report));
            writer.write(output);
            writer.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
