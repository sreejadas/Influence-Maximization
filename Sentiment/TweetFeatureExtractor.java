 
 import com.csvreader.CsvReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
 public class TweetFeatureExtractor{
    public static void constructModel()
    {


        Instances instdata = null;

        try
        {
            FastVector atts;

// 1. set up attributes
            atts = new FastVector();
            atts.addElement(new Attribute("content", (FastVector) null));


            
            // Declare the class attribute along with its values
            FastVector fvClassVal = new FastVector(5);
		  fvClassVal.addElement("very bad");
		  fvClassVal.addElement("bad");
            fvClassVal.addElement("neutral");
            fvClassVal.addElement("good");
            fvClassVal.addElement("very good");
     
            
            Attribute ClassAttribute = new Attribute("Class", fvClassVal);
            atts.addElement(ClassAttribute);

            // 2. create Instances object
            instdata = new Instances("tweetData", atts, 0);



            CsvReader data = new CsvReader("traindata.csv");

            int i=0;
            while (data.readRecord())
            {
                double[] vals = new double[instdata.numAttributes()];

                String class_id = data.get(0);
                switch (Integer.parseInt(class_id))
                {

                   case 1:
				   case 0:
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
                String tweet_content = data.get(5);
				tweet_content = TextNormaliser.getTweetWithoutUrlsAnnotations(tweet_content);
                //tweet_content = TextNormaliser.removeDuplicates(tweet_content);
                tweet_content = TextNormaliser.toLowerCase(tweet_content);
                Instance iInst = new Instance(2);
                iInst.setValue((Attribute) atts.elementAt(0), tweet_content);

                iInst.setValue((Attribute) atts.elementAt(1), class_id);



                instdata.add(iInst);

                // perform program logic here
               // System.out.println("["+i+"] "+class_id + ":" + tweet_content);
i++;
            }

            data.close();

            StringToWordVector filter = new StringToWordVector();


            instdata.setClassIndex(instdata.numAttributes() - 1);
            filter.setInputFormat(instdata);
            Instances newdata =   Filter.useFilter(instdata, filter);
			System.out.println(newdata.toString());
            ArffSaver saver = new ArffSaver();
            saver.setInstances(newdata);
            saver.setFile(new File("./data/train2data.arff"));
            saver.writeBatch();

        } catch (Exception ex)
        {
            Logger.getLogger(TweetFeatureExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }
        // 4. output data
        // System.out.println(instdata);

    }
}//construct arff