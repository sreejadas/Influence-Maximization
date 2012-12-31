package util;
import java.util.LinkedList;
import java.util.List;

import classifier.WekaClassifier;
public class Options {

		private int numFeatures;
        private boolean selectedFeaturesByFrequency;
        private boolean removeEmoticons;
        private String classifierName;
        private WekaClassifier constructedClassifier;
        private List<String> wmClassifiersName;

		public Options() {
                this.numFeatures = 0;
                this.selectedFeaturesByFrequency = false;
                this.removeEmoticons = false;
                this.wmClassifiersName = new LinkedList<String>();
        }
		 public void setSelectedFeaturesByFrequency(boolean selectedFeaturesByFrequency) {
                this.selectedFeaturesByFrequency = selectedFeaturesByFrequency;
        }
		 public void setNumFeatures(int numFeatures) {
                this.numFeatures = numFeatures;
        }
		public void setRemoveEmoticons(boolean removeEmoticons) {
                this.removeEmoticons = removeEmoticons;
        }
		
		public boolean isSelectedFeaturesByFrequency() {
                return selectedFeaturesByFrequency;
        }
		
		public int getNumFeatures() {
                return numFeatures;
        }
		
		public void setConstructedClassifier(WekaClassifier constructedClassifier) {
                this.constructedClassifier = constructedClassifier;
        }
		
		public boolean isRemoveEmoticons() {
                return removeEmoticons;
        }
}