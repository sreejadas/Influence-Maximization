package com.tookitaki.similarity.TokenProcessing;

import java.util.List;

import com.tookitaki.similarity.Tokenize.Token;


public interface IProcessor {
	
	/*initialize the Processor*/
	public void init() throws Exception;
	
	public List<Token> process(List<Token> tokens) throws Exception;
	
}