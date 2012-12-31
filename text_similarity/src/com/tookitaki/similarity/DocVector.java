package com.tookitaki.similarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math.linear.OpenMapRealVector;
import org.apache.commons.math.linear.RealVectorFormat;
import org.apache.commons.math.linear.SparseRealVector;

import com.tookitaki.similarity.Tokenize.Token;

public class DocVector {
    public Map<String,Integer> terms;
    public SparseRealVector vector;
    
    public static Map<String,Integer> getCombinedTermMap(List<Token> list1,List<Token> list2) {
    	TermFreqVectorBuilder tvf1 = new TermFreqVectorBuilder(list1);
    	TermFreqVectorBuilder tvf2 = new TermFreqVectorBuilder(list2);
    	
    	String[] terms1 = tvf1.getTerms();
    	String[] terms2 = tvf2.getTerms();
    	
    	Map<String,Integer> indexMap = new HashMap<String,Integer>();
    	int pos = makeUniqueIndex(indexMap, terms1, 0);
    	makeUniqueIndex(indexMap, terms2, pos);
    	
    	return indexMap;    	
    }
    
    private static int makeUniqueIndex(Map<String,Integer> map, String[] str, int startFrom) {
    	int pos = startFrom;
    	for(String s : str){
    		if(map.containsKey(s)){
    			continue;
    		} else {
    			map.put(s, pos++);
    		}
    	}
    	return pos;
    }    
    
    public DocVector(Map<String,Integer> terms) {
      this.terms = terms;
      this.vector = new OpenMapRealVector(terms.size());
    }
    	
    public void setEntry(String term, int freq) {
      if (terms.containsKey(term)) {
        int pos = terms.get(term);
        vector.setEntry(pos, (double) freq);
      }
    }
    
    public void normalize() {
      double sum = vector.getL1Norm();
      vector = (SparseRealVector) vector.mapDivide(sum);
    }
    
    public String toString() {
      RealVectorFormat formatter = new RealVectorFormat();
      return formatter.format(vector);
    }
}