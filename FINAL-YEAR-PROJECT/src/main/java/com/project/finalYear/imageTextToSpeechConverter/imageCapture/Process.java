package com.project.finalYear.imageTextToSpeechConverter.imageCapture;

import com.project.finalYear.imageTextToSpeechConverter.imageToText.Text;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;

public class Process extends JFrame {
    private final JLabel cameraScreen;
    private boolean isClicked;

    public Process() {
        setLayout(null);

        cameraScreen = new JLabel();
        cameraScreen.setBounds(0, 0, 640, 480);
        add(cameraScreen);

        JButton btnCapture = new JButton("Capture");
        btnCapture.setBounds(210, 500, 80, 40);
        add(btnCapture);
        btnCapture.addActionListener(e -> isClicked = true);

        JButton btnExit = new JButton("Submit");
        btnExit.setBounds(300, 500, 80, 40);
        add(btnExit);
        btnExit.addActionListener(e -> Text.saveTextFileFromImageFile());

        setSize(new Dimension(640, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void startProcess() {
        VideoCapture capture = new VideoCapture(0);
        Mat image = new Mat();
        byte[] imageData;
        ImageIcon icon;

        while (true) {
            capture.read(image);
            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);
            if (isClicked) {
                String name = JOptionPane.showInputDialog(this, "Enter image name");
                if (name == null || name.equals(""))
                    return;
                Imgcodecs.imwrite("out/images/" + name + ".jpg", image);
                isClicked = false;
            }
        }
    }
}