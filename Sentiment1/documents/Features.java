package documents;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Features {

		private Map<Integer,String> _id2feat;
        private Map<String,Integer> _feat2id;
        private Map<Integer,Integer> f2freq;
        private DocumentsSet ds;
        private List<String> stopwords;
		public Features() {
                this.f2freq = new HashMap<Integer, Integer>();
        }
		
		 public void selectFeaturesByFrequency(int number) {
                createFeat2Freq();
                Map<Integer,List<Integer>> index_new = new HashMap<Integer, List<Integer>>();
                Map<Integer,List<String>> index_new_str = new HashMap<Integer, List<String>>();
                Map<Integer,String> i2f_new = new HashMap<Integer, String>();
                Map<String,Integer> f2i_new = new HashMap<String, Integer>();
                int i = 0;
                int j = 0;
                while(i<_id2feat.size()) {
                        if(this.f2freq.get(i)>=number) {
                                i2f_new.put(j, _id2feat.get(i));
                                f2i_new.put(_id2feat.get(i), j);
                                j++;
                        }
                        i++;
                }
                this._feat2id = f2i_new;
                this._id2feat = i2f_new;
                for (Integer doc : ds.getD2f_train_str().keySet()) {
                        index_new.put(doc, new LinkedList<Integer>());
                        index_new_str.put(doc, new LinkedList<String>());
                        for (String item : ds.getD2f_train_str().get(doc)) {
                                if(_feat2id.get(item)!=null) {
                                        index_new_str.get(doc).add(item);
                                        index_new.get(doc).add(_feat2id.get(item));
                                }
                        }
                        Collections.sort(index_new.get(doc));
                }
                this.ds.setD2f_train_str(index_new_str);
                this.ds.setD2f2(index_new);
                System.out.println("num features " + _id2feat.size());
                System.out.println("num features " + f2freq.size());
                System.out.println("num selected features  " + _feat2id.size());
        }
		
		public void setDs(DocumentsSet ds) {
                this.ds = ds;
        }
		
		public void createFeat2Freq() {
                int app;
                Map<Integer,Integer> f2freq =  new HashMap<Integer, Integer>();
                for (Integer doc : ds.getD2f_train().keySet()) {
                        for (Integer feat : ds.getD2f_train().get(doc)) {
                                if(!f2freq.containsKey(feat))
                                        f2freq.put(feat, 1);
                                else {
                                        app = f2freq.get(feat);
                                        app++;
                                        f2freq.put(feat, app);
                                }
                        }
                }
                this.f2freq = f2freq;
        }
		
		public List<String> createListStopwords() throws IOException {
                List<String> stop = new LinkedList<String>();
                FileInputStream fstream = new FileInputStream("files/stopwords.txt");
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null) {
                stop.add(strLine);
        }
        this.stopwords = stop;
        return stop;
        }
		
		public Map<String, Integer> getF2i() {
                return _feat2id;
        }
		
		public Map<Integer, String> getI2f() {
                return _id2feat;
        }
		
		public void setI2f(Map<Integer, String> i2f) {
                this._id2feat = i2f;
        }

        /**
         * sets a given map that associates a string to a feature's id
         * @param f2i a map that associates a string to a feature's id
         */
        public void setF2i(Map<String, Integer> f2i) {
                this._feat2id = f2i;
        }
}