

public class Sentiment{

	public static void main(String []args)
	{
			/* preprocessing of training data*/
			//Preprocess.preprocess_Training_data();
			TweetFeatureExtractor.constructModel();
			NBayesCalculator.constructModel();
			System.out.println("\n\n");
			System.out.println(NBayesCalculator.classifyNewTweet( "ahh ive always wanted to see rent  love the soundtrack!!"));
	
	}
}