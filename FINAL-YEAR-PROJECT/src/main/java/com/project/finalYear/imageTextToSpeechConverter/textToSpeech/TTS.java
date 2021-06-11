package com.project.finalYear.imageTextToSpeechConverter.textToSpeech;

import javax.speech.AudioException;
import javax.speech.Central;
import javax.speech.EngineException;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;
import java.util.List;
import java.util.Locale;

public class TTS {

    public static void speak(List<String> speeches) {
        try {
            // Set property as Kevin Dictionary
            System.setProperty(
                    "freetts.voices",
                    "com.sun.speech.freetts.en.us"
                            + ".cmu_us_kal.KevinVoiceDirectory");

            // Register Engine
            Central.registerEngineCentral(
                    "com.sun.speech.freetts"
                            + ".jsapi.FreeTTSEngineCentral");

            // Create a Synthesizer
            Synthesizer synthesizer
                    = Central.createSynthesizer(
                    new SynthesizerModeDesc(Locale.US));

            // Allocate synthesizer
            synthesizer.allocate();

            // Resume Synthesizer
            synthesizer.resume();

            speeches.forEach(speech -> {
                synthesizer.speakPlainText(speech, null);
                try {
                    synthesizer.waitEngineState(
                            Synthesizer.QUEUE_EMPTY);

                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            // Deallocate the Synthesizer.
            synthesizer.deallocate();

        } catch (AudioException | EngineException | NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }
}
