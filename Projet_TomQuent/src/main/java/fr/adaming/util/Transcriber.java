package fr.adaming.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class Transcriber {

//	public static void main(String[] args) throws Exception {
//
//		Configuration configuration = new Configuration();
//
//		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
//		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
//		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
//
//		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
//		// Start recognition process pruning previously cached data.
//		recognizer.startRecognition(true);
//		SpeechResult result;
//		// Pause recognition process. It can be resumed then with
//		// startRecognition(false).
//		String okay = "exit";
//		while ((result = recognizer.getResult()) != null) {
//			
//			System.out.format("Hypothesis: %s\n", result.getHypothesis());
//
//			if (result.getHypothesis().toString().equals(okay)) {
//				System.out.format("Hypothesis: %s\n", result.getHypothesis());
//			}
//		}
//		recognizer.stopRecognition();
//	}

}
