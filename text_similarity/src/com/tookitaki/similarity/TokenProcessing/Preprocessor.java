package com.tookitaki.similarity.TokenProcessing;

import java.util.LinkedList;
import java.util.List;

import com.tookitaki.similarity.Tokenize.Token;

public class Preprocessor implements IProcessor {

	public static final int MAKE_LOWERCASE = 1; 
	
	private int option;
	
	public Preprocessor(int option) {
		this.option = option;
	}
	
	@Override
	public void init() throws Exception {
		//NO OP
	}

	@Override
	public List<Token> process(List<Token> tokens) throws Exception {
		
		List<Token> processed = new LinkedList<Token>();
		for(Token token : tokens){
			Token t = new Token(token.getValue(), token.getType());
			if(option == Preprocessor.MAKE_LOWERCASE){
				String val = t.getValue();
				val = val.toLowerCase();
				t.setValue(val);
			}
			processed.add(t);
		}	
		return processed;
	}
	
}