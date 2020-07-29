package imagesettings;

public class ImageProcessSetting {
    private float blurThreshold;

    public float getBlurThreshold() {
        return blurThreshold;
    }

    public void setBlurThreshold(float blurThreshold) {
        this.blurThreshold = blurThreshold;
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
