package imagesettings;

import org.opencv.core.Size;

public class ImageProcessSetting {
    private float blurThreshold;
    private Size blurSize;
    private int lowThreshold;
    private int ratio;
    private double brightnessControl;
    private double contrastControl;

    public float getBlurThreshold() {
        return blurThreshold;
    }

    public void setBlurThreshold(float blurThreshold) {
        this.blurThreshold = blurThreshold;
    }

    public Size getBlurSize() {
        return blurSize;
    }

    public void setBlurSize(Size blurSize) {
        this.blurSize = blurSize;
    }

    public int getLowThreshold() {
        return lowThreshold;
    }

    public void setLowThreshold(int lowThreshold) {
        this.lowThreshold = lowThreshold;
    }

    public int getRatio() {
        return ratio;
    }

    public double getBrightnessControl() {
        return brightnessControl;
    }

    public void setBrightnessControl(double brightnessControl) {
        this.brightnessControl = brightnessControl;
    }

    public double getContrastControl() {
        return contrastControl;
    }

    public void setContrastControl(double contrastControl) {
        this.contrastControl = contrastControl;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    @Override
    public String toString() {
        return "=========================================" + "\n" +
                "Settings" + "\n" +
                "Blur Threshold: " + this.blurThreshold + "\n" +
                "=========================================" + "\n" +
                "\n";
    }

}
