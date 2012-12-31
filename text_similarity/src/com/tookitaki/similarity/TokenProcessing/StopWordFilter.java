package com.tookitaki.similarity.TokenProcessing;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.tookitaki.similarity.Tokenize.Token;
import com.tookitaki.similarity.Tokenize.TokenType;

public class StopWordFilter implements IProcessor {

	Set<String> stopwords;	
	
	public StopWordFilter() {
		stopwords = new HashSet<String>();		
	}
	
	@Override
	public void init() throws IOException {
		BufferedReader br  = new BufferedReader(new FileReader("src/resources/stopwords/english"));
		String line;
		while ( (line = br.readLine()) != null ) {
			StringTokenizer st = new StringTokenizer(line);
			while( st.hasMoreTokens() ) {
				this.stopwords.add(st.nextToken());
			}
		}		
	}

	@Override
	public List<Token> process(List<Token> tokens) throws Exception {
		List<Token> processedTokens = new LinkedList<Token>();
		for( Token token : tokens) {
			if(token.getType() == TokenType.WORD && this.stopwords.contains(token.getValue()))
				continue;
			else {
				processedTokens.add(token);
			}
		}
		return processedTokens;
	}
	
}