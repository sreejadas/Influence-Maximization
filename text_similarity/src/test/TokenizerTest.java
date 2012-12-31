package test;

import static org.junit.Assert.*;
import com.tookitaki.similarity.Tokenize.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class TokenizerTest {

	private final Log log = LogFactory.getLog(getClass());
	  
	private String[] INPUT_TEXTS = {
			"http://t.co/q2VHlGV6",
			"Pra q fazer isso?",
			"@DiakiteLala ah cette série je la connais presque par coeur quand je vais mal sa me soulage sa me fait oublié tout mes soucis",
			"Meet two of our darling designers @shelbssnicolee @macaelaleclair. x  @ The Players' Retreat http://t.co/q2VHlGV6",
			"@joshmang Where are you at now!?",
			"#EMAVoteOneDirection x10," ,
			"@JUST_RayyONEx you ordered it online?" ,
			"@AlannHoee oh! Where were you at lunch?",
			"#FollowBackAChilenosDeSantiago síganme la people! Los sigo a todos" ,
			"#WhatMakesMeSmile @Monsta_beat :)" ,
			"Talk dirty to me (koffing, muk)"
	  };
	@Test
	public void test() throws Exception {
//		WordTokenizer wordTokenizer = new WordTokenizer();
//	    for (String inputText : INPUT_TEXTS) {
//	        System.out.println("sentence=" + inputText);
//	        wordTokenizer.setText(inputText);
//	        Token token;
//	        while ((token = wordTokenizer.nextToken()) != null) {
//	          System.out.println("token=" + token.toString());
//	        }	      
//	    }
	}

}
