/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taggers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

/**
 * 
 * @author Paul G Mathew
 */
public class CopyOfBigram {
	public static void main(String args[]) throws FileNotFoundException,
			IOException {

		String corporaLocation = " ";
		String splitBy = " ";
		String line = "";
		String pattern = "^[a-zA-Z0-9]*$";
		String[] words = null;// for getting words in each line
		int total = 0;
		String corpus = "";
		HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
		HashMap<String, Integer> bigramCount = new HashMap<String, Integer>();
		HashMap<String, Integer> tagCount = new HashMap<String, Integer>();

		HashMap<String, Integer> tagTransitionCount = new HashMap<String, Integer>();
		HashMap<String, Integer> wordLikelihoodCount = new HashMap<String, Integer>();
		HashMap<String, Double> wordProbability = new HashMap<String, Double>();

		try {

			MaxentTagger tagger = new MaxentTagger(
					"taggers/left3words-wsj-0-18.tagger");

			// UNIGRAM
			try (InputStream fis = new FileInputStream(
					"C:\\Users\\Paul G Mathew\\workspace2\\POSTagger\\src\\taggers\\corpus.txt");
					InputStreamReader isr = new InputStreamReader(fis);
					BufferedReader br = new BufferedReader(isr);) {
				while ((line = br.readLine()) != null) {
					// System.out.println(line);
					corpus = corpus + " " + line.toLowerCase();

					words = line.toLowerCase().split(splitBy);
					// System.out.println(line.toLowerCase());
					for (int i = 0; i < words.length; i++) {
						// if (words[i].matches(pattern)) {
						total++;
						if (wordCount.containsKey(words[i])) {
							int count = wordCount.get(words[i]);
							wordCount.put(words[i], count + 1);
						} else {
							wordCount.put(words[i], 1);
						}
						// }
					}

					// The tagged string
					String tagged = tagger.tagString(corpus);
					String tt = tagger.tagTokenizedString(corpus);
					// String tt = tagger.
					// Output the result
					System.out.println(tagged);
					System.out.println(tt);

					String[] pp = tagged.split(" ");
					String[] posTag = new String[pp.length];
					for (int i = 0; i < pp.length; i++) {
						// System.out.println(pp[i]);
						if (!wordLikelihoodCount.containsKey(pp[i])) {
							wordLikelihoodCount.put(pp[i], 1);
						} else {
							int count = wordLikelihoodCount.get(pp[i]);
							wordLikelihoodCount.put(pp[i], count + 1);
						}

						if (!tagCount.containsKey(pp[i].split("/")[1])) {
							tagCount.put(pp[i].split("/")[1], 1);
						} else {
							int count = tagCount.get(pp[i].split("/")[1]);
							tagCount.put(pp[i].split("/")[1], count + 1);
						}

					}
					for (int i = 0; i < pp.length - 1; i++) {
						String temp = pp[i].split("/")[1] + "/"
								+ pp[i + 1].split("/")[1];
						if (tagTransitionCount.containsKey(temp)) {
							int count = tagTransitionCount.get(temp);
							tagTransitionCount.put(temp, count + 1);
						} else {
							tagTransitionCount.put(temp, 1);
						}
					}

				}
			}

			String[] sss = corpus.split(" " + "\\." + " ");// to calculate
															// beginning of
															// sentence which
															// will be same as
															// end of sentences;
			int noSentences = sss.length;
			// System.out.println("----------------------------------------------------------->"+sss.length);
			// bigram count

			for (Map.Entry<String, Integer> entry : tagTransitionCount
					.entrySet()) {
				// System.out.println("Key : " + entry.getKey() + " Value : "
				// + entry.getValue());
			}

			System.out.println("Total number" + total + " CORPUS -->" + corpus);

			// BIGRAM WORD COUNT
			String[] corpusarray = corpus.toLowerCase().split(splitBy);

			for (int i = 1; i < corpusarray.length - 1; i++) {
				String temp = corpusarray[i] + "/" + corpusarray[i + 1];
				// System.out.println("temp - > " + temp + "i" + i);
				if (bigramCount.containsKey(temp)) {
					int count = bigramCount.get(temp);
					bigramCount.put(temp, count + 1);
				} else {
					bigramCount.put(temp, 1);
				}

			}

			for (Map.Entry<String, Integer> entry : wordLikelihoodCount
					.entrySet()) {
				// System.out.println("Key : " + entry.getKey() + " Value : "
				// + entry.getValue());
			}

			// calculating tag transition Probability
			HashMap<String, Double> tagTransProb = new HashMap<String, Double>();
			for (Map.Entry<String, Integer> entry : tagTransitionCount
					.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : "
						+ entry.getValue());

				String key = entry.getKey();
				String[] a = key.split("/");
				int value = entry.getValue();

				// System.out.println("Prvious -- >>"a[0]);
				double prob = (double) value / (double) tagCount.get(a[0]);
				tagTransProb.put(key, prob);

			}

			System.out.println("Tag transition prob");
			for (Map.Entry<String, Double> entry : tagTransProb.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
			}

			// calculating word Likelihood Probability
			HashMap<String, Double> wordLikelihoodProb = new HashMap<String, Double>();
			for (Map.Entry<String, Integer> entry : wordLikelihoodCount
					.entrySet()) {
				// System.out.println("Key : " + entry.getKey() + " Value : "
				// + entry.getValue());

				String key = entry.getKey();
				String[] a = key.split("/");
				int value = entry.getValue();

				double prob = (double) value / (double) tagCount.get(a[1]);
				wordLikelihoodProb.put(key, prob);

			}
			System.out.println("word Likelihood prob");
			for (Map.Entry<String, Double> entry : wordLikelihoodProb
					.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
			}

			System.out.println("Bigram count");
			for (Map.Entry<String, Integer> entry : bigramCount.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
			}
			System.out.println("HIIIII");

			HashMap<String, Double> bigramProb = new HashMap<String, Double>();
			// / don't forget to calculate bigram probability.
			for (Map.Entry<String, Integer> entry : bigramCount.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : "
						+ entry.getValue());

				String key = entry.getKey();
				String[] a = key.split("/");
				int value = entry.getValue();

				double prob = (double) value / (double) wordCount.get(a[0]);
				bigramProb.put(key, prob);

			}

			System.out.println("bigramProbability");
			for (Map.Entry<String, Double> entry : bigramProb.entrySet()) {
				System.out.println("Key : " + entry.getKey() + " Value : "
						+ entry.getValue());
			}

			// getting declarative statement input from user
			PosTagger pp = new PosTagger();
			String check = pp.check();

			String[] checkgrammer = check.split(" ");
			String[] temp = checkgrammer;

			// Checking the grammer structure
			checkGrammer(checkgrammer, tagTransProb);
			// printting word count
			/*
			 * Set set = bigramCount.entrySet(); Iterator i = set.iterator();
			 * while(i.hasNext()) { Map.Entry me = (Map.Entry)i.next();
			 * 
			 * // System.out.println(me.getKey()+"= "+me.getValue());
			 * 
			 * }
			 */
			// String s1 =
			// "The president has relinquished his control of the company's board";
			// String s2 =
			// "The cheif executive officer said the last year revenue was good";

			// for calculating bigram table and bigram probability

			// ---->>>Bigram b = new Bigram();

			// --->>>>>>
			// b.calculateBigramTable(sent.toUpperCase().toLowerCase(),
			// bigramCount, wordCount, noSentences, total);

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void checkGrammer(String[] checkgrammer,
			HashMap<String, Double> tagTransProb) {
		boolean tagTran = true;

		for (int i = 1; i < checkgrammer.length; i++) {

			String tagTransitionCheck = checkgrammer[i - 1].split("/")[1] + "/"
					+ checkgrammer[i].split("/")[1];
			if (!tagTransProb.containsKey(tagTransitionCheck)) {
				tagTran = false;
				System.out.println("->" + tagTransitionCheck + "position ->"
						+ i);

				// checking for correct tag transition from the corpus
				double max = 0;
				String maxS = " ";

				for (Map.Entry<String, Double> entry : tagTransProb.entrySet()) {
					String tt = checkgrammer[i - 1].split("/")[1] + "/"
							+ entry.getKey();
					if (tagTransProb.containsKey(tt)) {
						System.out.println("correct grammer" + tt);
					}

				}

			}

		}

		if (tagTran) {
			System.out.println("Correct Grammer ");
		} else {
			System.out.println("incorrect grammer");
		}

	}
}
