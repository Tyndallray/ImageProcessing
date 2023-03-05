# Image Processing
This project is designed to generated report for blurred images using computer vision techniques. The project is built using the OpenCV library, which is a powerful computer vision and image processing toolkit that can be used with Java.

## Blur Factor
The blur factor is calculated on a grayscale matrix of an image, which is used to calculate the variance of the laplacian.

### Code
```
Mat destination = new Mat();
Mat matGray=new Mat();

Imgproc.cvtColor(this.matrix, matGray, Imgproc.COLOR_BGR2GRAY);
Imgproc.Laplacian(matGray, destination, 3);

MatOfDouble median = new MatOfDouble();
MatOfDouble std= new MatOfDouble();

Core.meanStdDev(destination, median , std);

this.blurFactor = Math.pow(std.get(0,0)[0],2);
  ```

## Input and Output
The project is designed to take input from `./resources/` directory, output filtered images to `./processed-resources` directory and output reports to `./report` directory.

## Settings
In addition to image blur detection, the project provides a range of customization options that allow us to adjust blur threshold and other parameters for `ImageDirectoryProcessor` class. [Click here](https://github.com/Tyndallray/ImageProcessing/blob/master/src/imageprocessing/MainApplication.java) to see an example

# Conclusion
Overall, this project for filtering out blurred images is a powerful tool that can help users easily access filtered images and view reports of images that did not meet the criteria.
