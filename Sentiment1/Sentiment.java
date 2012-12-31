
import classifier.ClassifierBuilder;
import util.Options;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import classifier.WekaClassifier;

public class Sentiment {
        
        public static void main(String[] args) throws Exception {
            
               
                ClassifierBuilder clb = new ClassifierBuilder();
                Options opt = new Options();
                clb.setOpt(opt);
                opt.setSelectedFeaturesByFrequency(true);
                opt.setNumFeatures(150);
                opt.setRemoveEmoticons(true);
                clb.prepareTrain();
                clb.prepareTest();
                NaiveBayes nb = new NaiveBayes();
                WekaClassifier wc = clb.constructClassifier(nb);
                System.out.println(wc.classify("this is unbelievably good"));
			}
}