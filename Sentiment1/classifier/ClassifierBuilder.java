package classifier;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;


import util.ArffFileCreator;

import weka.classifiers.Classifier;
import weka.classifiers.functions.MultilayerPerceptron;
import documents.DocumentsSet;
import util.Options;



public class ClassifierBuilder {

		DocumentsSet _ds;
        private Options opt;
        
        public ClassifierBuilder() {
                _ds = new DocumentsSet();
        }
		 public void setOpt(Options opt) {
                this.opt = opt;
        }
		 public void prepareTrain() throws IOException {
                _ds.createFilePreprocessed("files/train.txt", "files/train_doc.txt", opt);
                _ds.createIndexTrain("files/train_doc.txt");
                if(this.opt.isSelectedFeaturesByFrequency())
                        _ds.getFeat().selectFeaturesByFrequency(2);
                ArffFileCreator fc = new ArffFileCreator();
                fc.setDs(_ds);
                fc.createArff_train("files/train1.arff");
        }
        
        /**
         * prepares data structures for classifier test
         * @throws IOException
         */
        public void prepareTest() throws IOException {
                _ds.createFilePreprocessed("files/test_base.txt", "files/test_doc.txt", opt);
                _ds.createIndexTest("files/test_doc.txt");
                ArffFileCreator fc = new ArffFileCreator();
                fc.setDs(_ds);
                fc.createArff_test("files/test1.arff");
                
        }
		public WekaClassifier constructClassifier(Classifier classifier) throws Exception {
                WekaClassifier clas = new WekaClassifier();
                clas.setClassifier(classifier);
                if(this.opt.getNumFeatures()!=0)
                        clas.selectFeatures(opt.getNumFeatures());
                
                clas.train();
                ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("files/" + classifier.getClass().getName() + ".model"));
                os.writeObject(clas);
                this.opt.setConstructedClassifier(clas);
                os.close();
                return clas;
        }
}