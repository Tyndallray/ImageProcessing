package imageprocessing;

import imagesettings.ImageProcessSetting;
import imageutils.ImageDirectoryProcessor;
import org.opencv.core.Core;
import org.opencv.core.Size;

public class MainApplication {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        ImageProcessSetting setting = new ImageProcessSetting();
        setting.setBlurThreshold(150);
        setting.setBlurSize(new Size(3, 3));
        setting.setLowThreshold(100);
        setting.setRatio(2);
        setting.setBrightnessControl(-350);
        setting.setContrastControl(3);

        ImageDirectoryProcessor processor = new ImageDirectoryProcessor("./resources/", "./processed-resources/", "./report/", setting);
        processor.process();
    }
}
