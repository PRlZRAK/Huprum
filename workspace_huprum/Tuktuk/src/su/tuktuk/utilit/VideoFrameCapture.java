package su.tuktuk.utilit;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_videoio.*;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;


public class VideoFrameCapture {

    public static void main(String[] args) {
        int height = 240;
        int width = 320;

        Mat frame = new Mat(height, width, CV_8UC1);

        VideoCapture capture = new VideoCapture("C:\\TMP\\video\\pub3.mp4");

        if (capture.isOpened()) {
            capture.read(frame);
        }

        if (frame != null) {
            imwrite("C:\\TMP\\video\\cover.jpg", frame);
        }
    }
}