package com.tookitaki.similarity.TokenRecognition;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.tookitaki.similarity.Tokenize.Token;
import com.tookitaki.similarity.Tokenize.TokenType;


public class URLRecognizer implements IRecognizer {
	
	private Pattern urlPattern;
	@Override
	public void init() throws Exception {
		this.urlPattern = Pattern.compile("^https?://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
		
	}

	@Override
	public List<Token> recognize(List<Token> tokens) {
		
		List<Token> extractedToken = new LinkedList<Token>();
		for (Token t : tokens) {
			String value = t.getValue();
			TokenType type = t.getType();
			if (type == TokenType.WORD || type == TokenType.INTERNET) {
				Matcher match = this.urlPattern.matcher(value);
				if (match.matches()) {
					extractedToken.add(new Token(value, TokenType.URL));
				} else {
					extractedToken.add(t);
				}
			} else {
				extractedToken.add(t);
			}
		}
		
		return extractedToken;
	}	
}