package com.tookitaki.similarity;

import java.util.LinkedList;
import java.util.List;

import com.tookitaki.similarity.TokenProcessing.IProcessor;
import com.tookitaki.similarity.TokenProcessing.ProcessorChain;
import com.tookitaki.similarity.TokenRecognition.IRecognizer;
import com.tookitaki.similarity.TokenRecognition.RecognizerChain;
import com.tookitaki.similarity.Tokenize.Token;
import com.tookitaki.similarity.Tokenize.WordTokenizer;


public class Document { 
	
	private String document;
	private List<Token> tokens;
	private TermFreqVectorBuilder tvfs;
	
	public Document(String d) throws Exception{
		this.document = d;
		WordTokenizer wordTokenizer = new WordTokenizer();
		wordTokenizer.setText(document);
		tokens = new LinkedList<Token>();
		Token token;
		while ((token = wordTokenizer.nextToken()) != null) {
			tokens.add(token);
	    }
		this.tvfs = null;
	}
	
	public void applyRecognizers(List<IRecognizer> recognizers) throws Exception{
		RecognizerChain rchain = new RecognizerChain(recognizers);
		applyRecognizers(rchain);
	}
	
	public void applyRecognizers(RecognizerChain chain) throws Exception{
		chain.init();
		tokens = chain.recognize(tokens);
	}
	
	public void applyProcessors(List<IProcessor> processors) throws Exception{
		ProcessorChain pchain = new ProcessorChain(processors);
		applyProcessors(pchain);
	}
	
	public void applyProcessors(ProcessorChain pchain) throws Exception {
		pchain.init();
		tokens = pchain.process(tokens);
	}
	
	public void buildTermFrequency(){
		this.tvfs = new TermFreqVectorBuilder(this.tokens);
	}
	public List<Token> getTokenSnapshot() {
		return new LinkedList<Token>(this.tokens); //Do not expose internal member
	}
	
	public String[] getTerms() {
		return this.tvfs.getTerms();
	}
	
	public int[] getFrequency() {
		return this.tvfs.getTermFrequencies();
	}
	
	public String getText(){
		return this.document;
	}
	 
}
