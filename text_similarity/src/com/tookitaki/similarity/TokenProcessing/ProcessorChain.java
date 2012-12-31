package com.tookitaki.similarity.TokenProcessing;

import java.util.LinkedList;
import java.util.List;

import com.tookitaki.similarity.Tokenize.Token;

public class ProcessorChain implements IProcessor {

  private List<IProcessor> processors;
  
  public ProcessorChain(List<IProcessor> processors) {
	  super();
	  this.setProcessors(processors);
  }
  
  public void setProcessors(List<IProcessor> processors) {
    this.processors = processors;
  }

  public void init() throws Exception {
    for (IProcessor processor : processors) {
      processor.init();
    }
  }
  
  @Override
  public List<Token> process(final List<Token> tokens) throws Exception {
    List<Token> processedTokens = new LinkedList<Token>();
    processedTokens.addAll(tokens);
    for (IProcessor processor : processors) {
      processedTokens = processor.process(processedTokens);
    }
    return processedTokens;
  }
}