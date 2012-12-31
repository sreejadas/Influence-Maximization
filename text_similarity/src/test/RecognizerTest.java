package test;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tookitaki.similarity.Document;
import com.tookitaki.similarity.TermFreqVectorBuilder;
import com.tookitaki.similarity.TokenProcessing.IProcessor;
import com.tookitaki.similarity.TokenProcessing.ProcessorChain;
import com.tookitaki.similarity.TokenProcessing.Stemmers;
import com.tookitaki.similarity.TokenProcessing.StopWordFilter;
import com.tookitaki.similarity.TokenProcessing.TokenRemover;
import com.tookitaki.similarity.TokenRecognition.BoundaryRecognizer;
import com.tookitaki.similarity.TokenRecognition.IRecognizer;
import com.tookitaki.similarity.TokenRecognition.RecognizerChain;
import com.tookitaki.similarity.TokenRecognition.URLRecognizer;
import com.tookitaki.similarity.Tokenize.Token;
import com.tookitaki.similarity.Tokenize.TokenType;

public class RecognizerTest {

	private final Log log = LogFactory.getLog(getClass());
	private static RecognizerChain chain;
	private static ProcessorChain pchain;
	  
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
//			"Talk dirty to me (koffing, muk)",
			"ordered me something some thoughts somethings"
	  };
	
	@BeforeClass
	public static void setupBeforeClass() throws Exception {
		
		chain = new RecognizerChain(Arrays.asList(new IRecognizer[] {
				new BoundaryRecognizer(),
				new URLRecognizer()
			}));
//		chain.init();
		
		List<TokenType> typesToFilter = Arrays.asList(new TokenType[] {
				TokenType.WHITESPACE,
				TokenType.PUNCTUATION		
		});
				
		pchain = new ProcessorChain(Arrays.asList(new IProcessor[] {
				new TokenRemover(typesToFilter),
				new StopWordFilter(),
				new Stemmers()
		}));
//		pchain.init();
		
	}
	
	@Test
	public void test() throws Exception {
		for (String inputText : INPUT_TEXTS) {
			Document d = new Document(inputText);
			d.applyRecognizers(chain);
			d.applyProcessors(pchain);
			List<Token> tokens = d.getTokenSnapshot();
			for (Token token : tokens) {
				System.out.println("token=" + token.toString());
			}
			TermFreqVectorBuilder tvf = new TermFreqVectorBuilder(tokens);
			
			String[] terms = tvf.getTerms();
			int[] freqs = tvf.getTermFrequencies();
			for (int i=0;i<terms.length;++i) {
				System.out.println("Term: "+terms[i] + " Freq: " + freqs[i]);
			}			
		}
	}

}