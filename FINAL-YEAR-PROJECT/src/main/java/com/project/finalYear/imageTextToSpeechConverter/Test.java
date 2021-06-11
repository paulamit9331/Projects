package com.project.finalYear.imageTextToSpeechConverter;

import com.project.finalYear.imageTextToSpeechConverter.imageCapture.Process;
import com.project.finalYear.imageTextToSpeechConverter.imageToText.Text;
import org.opencv.core.Core;

import java.awt.EventQueue;

public class Test {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(()-> {
            Process process = new Process();
            new Thread(process::startProcess).start();
        });
        Text.saveTextFileFromImageFile();
    }
}