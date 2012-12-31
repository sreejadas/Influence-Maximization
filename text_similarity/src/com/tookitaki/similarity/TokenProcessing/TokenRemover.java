package com.tookitaki.similarity.TokenProcessing;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.tookitaki.similarity.Tokenize.Token;
import com.tookitaki.similarity.Tokenize.TokenType;

public class TokenRemover implements IProcessor {
	
	List<TokenType> filter_types;
	
	public TokenRemover(List<TokenType> types){
		filter_types = types;
	}
	
	@Override
	public void init() throws IOException { /*No op */}

	@Override
	public List<Token> process(List<Token> tokens) throws Exception {
		List<Token> processedTokens = new LinkedList<Token>();
		for( Token token : tokens) {
			Boolean toAdd = true;
			for (TokenType type : this.filter_types) {
				if(token.getType() == type) {
					toAdd = false;
					break;
				}									
			}
			if(toAdd)
				processedTokens.add(token);
		}
		return processedTokens;
	}
	
}