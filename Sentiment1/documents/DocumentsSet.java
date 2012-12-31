package documents;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import util.Options;

import classifier.Preprocesser;
public class DocumentsSet {
	private Features _feats;
	   private Map<Integer,List<Integer>> _doc2feat_test;
        private Map<Integer,List<Integer>> _doc2feat_train;
        private Map<Integer,List<String>> _doc2feat_train_str;
        private List<String> _polarity;
	public DocumentsSet() {
                this._feats = new Features();
                this._feats.setDs(this);
        }
		
		
	public void createFilePreprocessed(String path_input, String path_output, Options opt) {
                List<String> p = new LinkedList<String>();
                try{
                        Preprocesser pr = new Preprocesser(opt);
            FileInputStream fstream = new FileInputStream(path_input);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            String str, pol;
                         FileOutputStream prova = new FileOutputStream(path_output);
                         PrintStream scrivi = new PrintStream(prova);
            while ((strLine = br.readLine()) != null) {
                        String[] items = strLine.split(";;");
                        str = items[5].toLowerCase();
                        pol = items[0];
                        p.add(pol);
                        scrivi.println(pr.preprocessDocument(str));
            }
            in.close();
        }catch (Exception e){
            System.err.println("Error " + e.getMessage());
        }
        _polarity = p;
    }
	
	public void createIndexTrain(String path_input) throws IOException {
                this._feats.createListStopwords();
                int i = 0;
                int j = 0;
                Map<Integer,List<Integer>> index = new HashMap<Integer, List<Integer>>();
                Map<Integer,List<String>> index_str = new HashMap<Integer, List<String>>();
                Map<Integer,String> i2f = new HashMap<Integer, String>();
                Map<String,Integer> f2i = new HashMap<String, Integer>();
                try{
            FileInputStream fstream = new FileInputStream(path_input);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(strLine, "               \";%{}|");
                index.put(i, new LinkedList<Integer>());
                index_str.put(i, new LinkedList<String>());
                while(st.hasMoreTokens()) {
                        String tmp;
                        int num;
                        tmp = st.nextToken();
						if(tmp.length()<=1)
							continue;
                        if(f2i.get(tmp)!=null) {
                                num = f2i.get(tmp);
                               
                                if(!index.get(i).contains(num)) {
                                        index.get(i).add(num);
                                        index_str.get(i).add(tmp);
                                }
                        } else {//if(!this.feat.getStopwords().contains(tmp)){
                                f2i.put(tmp, j);
                                i2f.put(j, tmp);
                                index.get(i).add(j);
                                index_str.get(i).add(tmp);
                                j++;
                        }
                }
                Collections.sort(index.get(i));
                i++;
            }
            in.close();
        }catch (Exception e){
            System.err.println("Errore: " + e.getMessage());
        }
        this._doc2feat_train = index;
        this._doc2feat_train_str = index_str;
        this._feats.setF2i(f2i);
        this._feats.setI2f(i2f);
        }
		
		 public void createIndexTest(String path_input) throws IOException {
                int i = 0;
                Map<Integer,List<Integer>> index = new HashMap<Integer, List<Integer>>();
                Map<String,Integer> f2i = this._feats.getF2i();
                try{
            FileInputStream fstream = new FileInputStream(path_input);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(strLine, "             \";%{}|");
                index.put(i, new LinkedList<Integer>());
                while(st.hasMoreTokens()) {
                        String tmp;
                        int num;
                        tmp = st.nextToken();
                        if(f2i.get(tmp)!=null) {
                                num = f2i.get(tmp);
                                
                                if(!index.get(i).contains(num))
                                        index.get(i).add(num);
                        }
                }
                Collections.sort(index.get(i));
                i++;
            }
            in.close();
        }catch (Exception e){
            System.err.println("Error " + e.getMessage());
        }
        this._doc2feat_test = index;
        }
		
		public Features getFeat() {
                return _feats;
        }
		
		public Map<Integer, List<String>> getD2f_train_str() {
                return _doc2feat_train_str;
        }
		
		public void setD2f_train_str(Map<Integer, List<String>> d2fTrainStr) {
                _doc2feat_train_str = d2fTrainStr;
        }
		
		public void setD2f2(Map<Integer, List<Integer>> d2f2) {
                this._doc2feat_train = d2f2;
        }
		
		public Map<Integer, List<Integer>> getD2f_train() {
                return _doc2feat_train;
        }
		
		public List<String> getPols() {
                return _polarity;
        }
		
		public Map<Integer, List<Integer>> getD2f_test() {
                return _doc2feat_test;
        }
}