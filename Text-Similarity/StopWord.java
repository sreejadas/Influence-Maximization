/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author sreeja
 */
import java.util.Arrays;
import java.util.HashSet;
//import org.apache.commons.lang.StringUtils;
import java.util.Set;
import java.util.StringTokenizer;
public class StopWord {
    
     private static final String DEFAULT_STOPWORDS = 
    "a about add ago after all also an and another any are as at be " +
    "because been before being between big both but by came can come " +
    "could did do does due each else end far few for from get got had " +
    "has have he her here him himself his how if in into is it its " +
    "just let lie like low make many me might more most much must " +
    "my never no nor not now of off old on only or other our out over " +
    "per pre put re said same see she should since so some still such " +
    "take than that the their them then there these they this those " +
    "through to too under up use very via want was way we well were " +
    "what when where which while who will with would yes yet you your";

     
   Set<String> stopwords = new HashSet<String>();
    
  public StopWord(){
      StringTokenizer st ; 
      String[] stopwordArray=new String[500];
      st=new StringTokenizer(DEFAULT_STOPWORDS, " ", false);
      for(int i=0;st.hasMoreTokens();++i)
          stopwordArray[i]=st.nextToken();
   stopwords.addAll(Arrays.asList(stopwordArray));
  }
  
}
