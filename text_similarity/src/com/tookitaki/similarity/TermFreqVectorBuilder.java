package com.tookitaki.similarity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.index.TermFreqVector;

import com.tookitaki.similarity.Tokenize.Token;


public class TermFreqVectorBuilder implements TermFreqVector {

	private String[] terms;
	private int[] frequency;
	
	public TermFreqVectorBuilder(List<Token> tokens) {
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		
		for( Token t : tokens ) {
			String value = t.getValue();
			if(map.containsKey(value)){
				int count = map.get(value);
				map.put(value, count+1);
			}
			else{
				map.put(value, 1);
			}
		}
		int size = map.size();
		this.terms = new String[size];
		this.frequency = new int[size];
		int i=0;
		for(Map.Entry<String, Integer> entry : map.entrySet() ){
			String key = entry.getKey();
			Integer freq = entry.getValue();
			terms[i] = key;
			frequency[i] = freq;
			i++;			
		}
	}
	
	@Override
	public String getField() {
		return "Text";
	}

	@Override
	public int[] getTermFrequencies() {
		return frequency.clone();
	}

	@Override
	public String[] getTerms() {
		return 	terms.clone();
	}

	@Override
	public int indexOf(String arg0) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int[] indexesOf(String[] arg0, int arg1, int arg2) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int size() {
		return terms.length;
	}
	
}