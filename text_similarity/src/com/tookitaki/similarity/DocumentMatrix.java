package com.tookitaki.similarity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.math.linear.OpenMapRealVector;
import org.apache.commons.math.linear.SparseRealVector;


public class DocumentMatrix {
	
//	private OpenMapRealMatrix matrix;	
	private Map<String,Integer> word_map;
	private List<Document> doc_list;
	
	public DocumentMatrix(List<Document> list){
		
		this.doc_list = list;
		this.word_map = new HashMap<String,Integer>();
		for(Document doc : list) {
			for(String term : doc.getTerms()){
				if(word_map.containsKey(term)) {
					int freq = word_map.get(term);
					word_map.put(term, freq+1);
				} else {
					word_map.put(term, 1);
				}
			}
		}		
	}
	
	public List<Double> getSimilarity(Document query) {
		
		List<Double> result = new LinkedList<Double>();
		for(Document d: this.doc_list){
			result.add(getSimilarity(d,query));
		}
		return result;
	}
	
	
	private double getSimilarity(Document base, Document query){
		Set<String> set = new HashSet<String>();
		set.addAll(Arrays.asList(base.getTerms()));
		set.addAll(Arrays.asList(query.getTerms()));
		
		Iterator<String> iter = set.iterator();
		Map<String,Integer> indexMap = new HashMap<String,Integer>();
		int index = 0;
		while(iter.hasNext()){
			indexMap.put(iter.next(), index++);
		}
		
		SparseRealVector vectorBase = new OpenMapRealVector(set.size());
		SparseRealVector vectorQuery = new OpenMapRealVector(set.size());
		
		setEntry(indexMap, vectorBase, base.getTerms(), base.getFrequency());
		setEntry(indexMap, vectorQuery, query.getTerms(), query.getFrequency());
		
		vectorBase = idf(tf(vectorBase), indexMap);
		vectorQuery = idf(tf(vectorQuery), indexMap);
		
		return getCosineSimilarity(vectorBase, vectorQuery);			
	}

	private void setEntry(Map<String,Integer> map, SparseRealVector vector,String[] terms,int[] frequencies){
		for(int i=0;i<terms.length;++i){
			if(map.containsKey(terms[i])){
				int index = map.get(terms[i]);
				vector.setEntry(index, (double)frequencies[i]);				
			}
		}
	}
	
	private SparseRealVector tf(SparseRealVector vector){
		double sum = vector.getL1Norm();
		SparseRealVector result = new OpenMapRealVector(vector);
		return (SparseRealVector)result.mapDivide(sum);
	}
	
	private SparseRealVector idf(SparseRealVector vector, Map<String,Integer> map){
		int no_of_docs = this.doc_list.size();
		SparseRealVector result = new OpenMapRealVector(vector.getDimension());
		for(Map.Entry<String, Integer> entry : map.entrySet()){
			int index = entry.getValue();
			String term = entry.getKey();
			if( this.word_map.containsKey(term)){
				int occurance = this.word_map.get(term);
				result.setEntry(index, vector.getEntry(index) * Math.log(no_of_docs /1+occurance));
			} else {
				result.setEntry(index,vector.getEntry(index) * Math.log(no_of_docs)); //This will execute only for query string
			}
		}
		return result;
	}
	
	private double getCosineSimilarity(SparseRealVector d1, SparseRealVector d2) {
	    return (d1.dotProduct(d2)) / (d1.getNorm() * d2.getNorm());
	}
		
}
	
	
