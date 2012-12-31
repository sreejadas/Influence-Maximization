package com.tookitaki.similarity.TokenProcessing;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.tartarus.snowball.ext.PorterStemmer;
import org.tartarus.snowball.SnowballProgram;

import com.tookitaki.similarity.Tokenize.Token;
import com.tookitaki.similarity.Tokenize.TokenType;

public class Stemmers implements IProcessor {
	private SnowballProgram stemmer;
	
	public Stemmers() {
		this.stemmer = new PorterStemmer();
	}
	
	@Override
	public void init() throws IOException { /*NO op*/}

	@Override
	public List<Token> process(List<Token> tokens) throws Exception {
				
		List<Token> processedTokens = new LinkedList<Token>();
		for( Token token : tokens) {
			if(token.getType() != TokenType.WORD) {
				processedTokens.add(token);
			}
			else {
				this.stemmer.setCurrent(token.getValue());
				this.stemmer.stem();
				processedTokens.add( new Token(this.stemmer.getCurrent(),token.getType()));
			}
		}
		return processedTokens;
	}
	
}