package test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import com.tookitaki.similarity.Document;
import com.tookitaki.similarity.TokenRecognition.BoundaryRecognizer;
import com.tookitaki.similarity.TokenRecognition.IRecognizer;
import com.tookitaki.similarity.TokenRecognition.RecognizerChain;
import com.tookitaki.similarity.TokenRecognition.URLRecognizer;
import com.tookitaki.similarity.Tokenize.Token;

public class TokenizerTest {

	private final Log log = LogFactory.getLog(getClass());
	  
	private String[] INPUT_TEXTS = {
//			"http://t.co/q2VHlGV6",
//			"Pra q fazer isso?",
//			"@DiakiteLala ah cette série je la connais presque par coeur quand je vais mal sa me soulage sa me fait oublié tout mes soucis",
//			"Meet two of our darling designers @shelbssnicolee @macaelaleclair. x  @ The Players' Retreat http://t.co/q2VHlGV6",
//			"@joshmang Where are you at now!?",
//			"#EMAVoteOneDirection x10," ,
//			"@JUST_RayyONEx you ordered it online?" ,
//			"@AlannHoee oh! Where were you at lunch?",
//			"#FollowBackAChilenosDeSantiago síganme la people! Los sigo a todos" ,
//			"#WhatMakesMeSmile @Monsta_beat :)" ,
//			"Talk dirty to me (koffing, muk)"
			"Mollie, since you got me in the Christmas spirit and you're making such a difference in the lives of these children, I bought the rest of your Amazon Wish List. It'll be after Christmas before you get them, but better late than never. My only request is that you share more stories about the children receiving these items.5Dan that was amazing of you!!https://www.facebook.com/276124725735800/posts/342670359081236_5115045"

	  };
	@Test
	public void test() throws Exception {
		
		RecognizerChain chain = new RecognizerChain(Arrays.asList(new IRecognizer[] {
				new BoundaryRecognizer(),
				new URLRecognizer()
			}));
		chain.init();
		List<Document> l = new LinkedList<Document>();
		for(String s : INPUT_TEXTS){
			Document d = new Document(s);
			d.applyRecognizers(chain);
			l.add(d);
		}
		for(Document d:l){
			List<Token> tokens = d.getTokenSnapshot();
			System.out.println(d.getText());
			for(Token t:tokens){
				System.out.println(t.getValue() + " : " + t.getType());
			}
		}
		
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
