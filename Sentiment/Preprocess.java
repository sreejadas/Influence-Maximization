
import com.csvreader.CsvReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Preprocess
{

	public void preprocess_Training_data()
	{
			
			testData();
	
	}
	 
	public static void testData()
    {
        LinkedList<String> content = new LinkedList<String>();
        LinkedList<String> classes = new LinkedList<String>();
        try
        {
            CsvReader data = new CsvReader("training.csv");


            while (data.readRecord())
            {

                String class_id = data.get(0);

                String tweet_content = data.get(5);

                
                tweet_content = TextNormaliser.getTweetWithoutUrlsAnnotations(tweet_content);
                tweet_content = TextNormaliser.removeDuplicates(tweet_content);
                tweet_content = TextNormaliser.toLowerCase(tweet_content);
                content.add(tweet_content);
                       
                
                switch (Integer.parseInt(class_id))
                {

                    case 1:
                        class_id = "very bad";
                        break;
                    case 2:
                        class_id = "bad";
                        break;
					case 3:
                        class_id = "neutral";
                        break;
                    case 4:
                        class_id = "good";
                        break;
					case 5:
                        class_id = "very good";
                        break;


                }
                classes.add(class_id);
            }
        }catch(IOException ex){
           // Logger.getLogger(TesterSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
       // NBayesCalculator.evaluateModel(content, classes);
    }
}