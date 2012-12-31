package com.tookitaki.similarity.Tokenize;

/**
 * List of all possible token types. Default is UNKNOWN.
 * @author Sujit Pal, Nilanjan Basu
 * @version $Revision$
 */
public enum TokenType {
  ABBREVIATION, 
  COMBINED, 
  PHRASE, 
  EMOTICON, 
  INTERNET, 
  URL,
  WORD,
  STOP_WORD,
  CONTENT_WORD,
  NUMBER, 
  WHITESPACE,
  PUNCTUATION, 
  PLACE, 
  ORGANIZATION,
  MARKUP,
  USERNAME,
  UNKNOWN, 
  HASHTAG
}
