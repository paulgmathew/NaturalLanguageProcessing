package taggers;

import java.io.IOException;
import java.util.Scanner;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

public class PosTagger {

	public String check() throws ClassNotFoundException, IOException {

		MaxentTagger tagger = new MaxentTagger(
				"taggers/left3words-wsj-0-18.tagger");

		XMLUtil x = new XMLUtil();
		// The sample string
		Scanner scanner = new Scanner(System.in);
		// scanner.useDelimiter("\\n");

		System.out.print("Enter a Declarative statement : ");
		String sample = scanner.nextLine();
		x.Check(sample);
		sample = ". " + sample;
		// String sample = "This is a dog";
		// System.out.println(sample);

		// The tagged string
		String tagged = tagger.tagString(sample);
		String tt = tagger.tagTokenizedString(sample);
		// String tt = tagger.
		// Output the result
		// System.out.println(tagged);
		// System.out.println(tt);

		String[] pp = tagged.split(" ");
		String[] posTag = new String[pp.length];
		for (int i = 0; i < pp.length; i++) {
			// System.out.println(pp[i]);
			posTag[i] = pp[i].split("/")[1];

		}
		// System.out.println(pp.length);

		return tagged;
	}

}
