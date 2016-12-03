package taggers;

import edu.stanford.nlp.tagger.maxent.MaxentTagger;

class TaggerDemo {

	public static void main(String[] args) throws Exception {

		try {
			// Initialize the tagger
			MaxentTagger tagger = new MaxentTagger(
					"taggers/left3words-wsj-0-18.tagger");

			// The sample string
			String sample = "This is a text";

			// The tagged string
			String tagged = tagger.tagString(sample);
			String tt = tagger.tagTokenizedString(sample);
			// String tt = tagger.
			// Output the result
			System.out.println(tagged);
			System.out.println(tt);

			String[] pp = tagged.split(" ");
			String[] posTag = new String[pp.length];
			for (int i = 0; i < pp.length; i++) {
				System.out.println(pp[i]);
				posTag[i] = pp[i].split("/")[1];

			}
			System.out.println(pp.length);

		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
