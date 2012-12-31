/*
 * Class which classifies tweets based on a Naibe Bayes Model
 * 
 */


import java.io.*;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
//import sentimentanalysis.common.LocalTweet;
//import sentimentanalysis.database.Operator;
//import sentimentanalysis.twitterapi.TweetOperator;
//import twitter4j.TwitterException;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesMultinomial;
import weka.core.*;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

/**
 *Class which loads a NaiveBayesClassifier from given data(16.000 tweets)
 * Available classes: 0(negative),2(neutral),4(positive)
 * The classifier will serialized in order to be able to used as classifier for new data
 * @author maryger
 */
public class NBayesCalculator
{

    /**
     * Serialize Naive Bayes classifier
     */
   public static double constructModel(String tw)
    {
	double label=0;
        try
        {
            BufferedReader reader = new BufferedReader(
                    new FileReader("./data/train2data.arff"));

            Instances data = new Instances(reader);


            reader.close();
//            // setting class attribute
//            data.setClassIndex(data.numAttributes() - 1);


            // train J48 and output model
            NaiveBayes classifier = new NaiveBayes();
            data.setClassIndex(0);
            classifier.buildClassifier(data);
            serialize(classifier);
            // evaluate classifier and print some statistics
			
			 NaiveBayes cls = (NaiveBayes) weka.core.SerializationHelper.read("nBayes.model");
			 FastVector atts;

            // 1. set up attributes
            atts = new FastVector();
            atts.addElement(new Attribute("content", (FastVector) null));


            // Declare the class attribute along with its values
            FastVector fvClassVal = new FastVector(4);
          
            
            
            Attribute ClassAttribute = new Attribute("Class", fvClassVal);
            atts.addElement(ClassAttribute);
			 Instances instdata = new Instances("testData", atts, 0);
            Instance iInst = new Instance(2);
            iInst.setValue((Attribute) atts.elementAt(0), tw);
			 instdata.add(iInst);
			 StringToWordVector filter = new StringToWordVector();


            instdata.setClassIndex(instdata.numAttributes() - 1);
			
			filter.setDoNotOperateOnPerClassBasis(true); 
            filter.setInputFormat(instdata);
            Instances newdata = Filter.useFilter(instdata, filter);
            Evaluation eval = new Evaluation(data);
			
			 PrintWriter o1 = new PrintWriter(new BufferedWriter(new FileWriter("out.txt")));
            eval.evaluateModel(cls, data);
            //o1.println(eval.toSummaryString("\nResults\n======\n", false));
            //o1.println("\n\nClassifier model:\n\n" + classifier);
			for(int i=0;i<data.numInstances();++i)
			{
				//System.out.println(data.instance(i));
				double pred = cls.classifyInstance(data.instance(i));
   System.out.print("ID: " + data.instance(i).value(0));
   System.out.print(", actual: " + data.classAttribute().value((int) data.instance(i).classValue()));
   System.out.println(", predicted: " + data.classAttribute().value((int) pred));
			}
			cls.updateClassifier(newdata.instance(0));
			 label=cls.classifyInstance(newdata.instance(0));
			System.out.println("label "+label);
        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
		return label;

    }

    /**
     * Retrieve the classifier Naive model
     * for new data
     * the classification result can be used as an 
     * additional score(weight) to the existing scores.
     */
    private Object deserialize()
    {
        NaiveBayesMultinomial classifier = null;
        ObjectInputStream in = null;
        try
        {
            // Deserialize from a file
            File file = new File("naiveClassifier.mod");
            in = new ObjectInputStream(new FileInputStream(file));
            // Deserialize the object
            classifier = (NaiveBayesMultinomial) in.readObject();
            in.close();
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            try
            {
                in.close();
            } catch (IOException ex)
            {
                Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return classifier;
    }

    private static void serialize(Classifier object)
    {
        try
        {
            weka.core.SerializationHelper.write("nBayes.model", object);
        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    /**
     * Method which returns an additional score based on previous tweets
     * We have trained a NaiveBayes model from previous tweets
     * with labels positive negative and neutral
     * When it gets the new tweet it will predict the most possible
     * class bases on its content.
     * @param LocalTweet instance
     * @return a score which can be combined with the existing heuristics
     */
    public static double classifyNewTweet(String tw)
    {
        double score = 0,clsLabel=0.0;
        try
        {
            NaiveBayes cls = (NaiveBayes) weka.core.SerializationHelper.read("nBayes.model");

            FastVector atts;

            // 1. set up attributes
            atts = new FastVector();
            atts.addElement(new Attribute("content", (FastVector) null));


            // Declare the class attribute along with its values
            FastVector fvClassVal = new FastVector(4);
          
            
            
            Attribute ClassAttribute = new Attribute("Class", fvClassVal);
            atts.addElement(ClassAttribute);

            // 2. create Instances object
            Instances instdata = new Instances("testData", atts, 0);
            Instance iInst = new Instance(2);
            iInst.setValue((Attribute) atts.elementAt(0), tw);

            //iInst.setValue((Attribute) atts.elementAt(1), '?');

System.out.println("from here "+tw);

            instdata.add(iInst);


            StringToWordVector filter = new StringToWordVector();


            instdata.setClassIndex(instdata.numAttributes() - 1);
			
			filter.setDoNotOperateOnPerClassBasis(true); 
            filter.setInputFormat(instdata);
            Instances newdata = Filter.useFilter(instdata, filter);



          System.out.println(newdata.toString());
            newdata.setClassIndex(0);
			/*System.out.println("call2");
            clsLabel = cls.classifyInstance(newdata.instance(0));*/
			 System.out.println("call1");
			 
			double[] clsarr=cls.distributionForInstance(newdata.instance(0));
			for(int i=0;i<clsarr.length;++i)
			System.out.println(clsarr[i]);
            int label = (int) clsLabel;
			//System.out.println("\n"+label+"\t"+clsLabel);
            switch (label)
            {

                case 3:
                    score = +0.5;
                    break;
                case 2:
                    score = -0.5;
                    break;
                case 1:
                    score = +0.0;
                    break;
            }

            //      eTest.evaluateModel(cls, isTrainingSet);
        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return clsLabel;
    }
    
    public static void evaluateModel(LinkedList<String> tw,LinkedList<String> classes){
        try
        {
            Classifier cls = (Classifier) weka.core.SerializationHelper.read("nBayes.model");
            
            
            BufferedReader reader = new BufferedReader(
                    new FileReader("./data/train2data.arff"));

            Instances traindata = new Instances(reader);


            reader.close();
            
            
            FastVector atts;

            // 1. set up attributes
            atts = new FastVector();
            atts.addElement(new Attribute("content", (FastVector) null));


            // Declare the class attribute along with its values
            FastVector fvClassVal = new FastVector(3);
         fvClassVal.addElement("");
		  fvClassVal.addElement("very bad");
		  fvClassVal.addElement("bad");
            fvClassVal.addElement("neutral");
            fvClassVal.addElement("good");
            fvClassVal.addElement("very good");
            
            
            Attribute ClassAttribute = new Attribute("Class", fvClassVal);
            atts.addElement(ClassAttribute);

            // 2. create Instances object
            Instances instdata = new Instances("testData", atts, 0);
            int i=0;
            for(String tweet:tw){
            Instance iInst = new Instance(2);
            iInst.setValue((Attribute) atts.elementAt(0), tweet);

           if(!classes.isEmpty())
           {
           iInst.setValue((Attribute) atts.elementAt(1), classes.get(i));
           
           }


            instdata.add(iInst);
           i++;
            }


            StringToWordVector filter = new StringToWordVector();


            instdata.setClassIndex(instdata.numAttributes() - 1);
            filter.setInputFormat(instdata);
            Instances newdata = Filter.useFilter(instdata, filter);
            ArffSaver saver = new ArffSaver();
            saver.setInstances(newdata);
            saver.setFile(new File("../data/testdata2.arff"));
            saver.writeBatch();


           // System.out.println(newdata.toString());
           traindata.setClassIndex(0);
             Evaluation eval = new Evaluation(traindata);
            eval.evaluateModel(cls, newdata);
//            String[] options = new String[6];
//            options[0] = "-T";
//            options[1] = "../data/testdata2.arff";
//            options[2] = "-l";
//            options[3] = "nBayes.model";
//            options[4] = "-p";
//            options[5] = "0";
//            
            
           // System.out.println(Evaluation.evaluateModel(cls, options));
            
           System.out.println(eval.toSummaryString("\nResults\n======\n", false));
            System.out.println("\n\nClassifier model:\n\n" + cls);
            System.out.println(instdata.numClasses());
            
            Enumeration br = newdata.enumerateInstances();
            int ind=0;
            while(br.hasMoreElements()){
            double clsLabel = cls.classifyInstance((Instance)br.nextElement());
            String cl ="";
            switch((int)clsLabel){
                case 0: cl ="";break;
                case 1: cl ="neutral"; break;
                case 2:cl= "negative"; break;
                case 3:cl = "positive"; break;
            
            
            }
            
            System.out.println("cont: "+tw.get(ind)+" pred_class: "+cl + " actual: "+classes.get(ind));
            ind++;
            }
        
        } catch (Exception ex)
        {
            Logger.getLogger(NBayesCalculator.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    
    }
}