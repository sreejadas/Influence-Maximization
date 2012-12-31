

public class Sentiment{

	public static void main(String []args)
	{
			/* preprocessing of training data*/
			//Preprocess.preprocess_Training_data();
			TweetFeatureExtractor.constructModel();
			//NBayesCalculator.constructModel();
			System.out.println("\n\n");
			System.out.println(NBayesCalculator.constructModel( "I think that you both will get on well with each other..."));
	
	}
}