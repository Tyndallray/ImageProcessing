package imageprocessing;

import imagesettings.ImageProcessSetting;
import imageutils.ImageDirectoryProcessor;
import org.opencv.core.Core;

public class MainApplication {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        ImageProcessSetting setting = new ImageProcessSetting();
        setting.setBlurThreshold(150);

        ImageDirectoryProcessor processor = new ImageDirectoryProcessor("./resources/", "./processed-resources/", "./report/", setting);
        processor.process();
    }
}
