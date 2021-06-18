package com.project.finalYear.imageTextToSpeechConverter;

import com.project.finalYear.imageTextToSpeechConverter.capture.Process;
import com.project.finalYear.imageTextToSpeechConverter.convert.Text;
import nu.pattern.OpenCV;
import org.opencv.core.Core;

import java.awt.*;

public class Test {

    public static void main(String[] args) {
        OpenCV.loadLocally();
        EventQueue.invokeLater(() -> {
            Process process = new Process();
            new Thread(process::startProcess).start();
        });
    }
}
