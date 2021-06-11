package com.project.finalYear.imageTextToSpeechConverter.imageToText;

import com.project.finalYear.imageTextToSpeechConverter.textToSpeech.TTS;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Text {
    private static List<String> speechs = new ArrayList<>();

    private static void processImg(BufferedImage ipImage)
            throws IOException, TesseractException {

        ITesseract tesseract = new Tesseract();

        tesseract.setDatapath("src/main/resources/tessdata");

        // doing OCR on the image
        // and storing result in string str
        String str = tesseract.doOCR(ipImage);
        speechs.add(str);
        System.out.println(str);
    }

    public static void saveTextFileFromImageFile() {

        List<File> images = Arrays.asList(new File("out/images/").listFiles());
        images.forEach(image -> {
            try {
                BufferedImage ipImage = ImageIO.read(image);
                processImg(ipImage);

            } catch (IOException | TesseractException | NullPointerException e) {
                System.err.println(e.getMessage());
            }
        });
        TTS.speak(speechs);
    }
}