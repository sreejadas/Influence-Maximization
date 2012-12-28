/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author sreeja
 */
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;

public class TDIDF_Matrix extends TDM { 

int[] tottal_no_words_in_doc; 
int[] num_of_doc_in_which_word_i_appears; 
  DecimalFormat twoDForm = new DecimalFormat("0.00000"); 
    public TDIDF_Matrix() { 
        super();
         tdidf = new double[2][keywordList.size()]; 
         tottal_no_words_in_doc = new int[2]; 
         num_of_doc_in_which_word_i_appears = new int[keywordList.size()]; 
    }

    public void compute_tottal_no_words_in_doc() 
    { 
        int sum = 0;
        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < keywordList.size(); j++) 
            { 
                if((count1[i][j])>0) 
                 { 
                     sum = sum+1; 
                 } 
            } 
            tottal_no_words_in_doc[i]=sum; 
            sum =0; 
        }

       // for (int i = 0; i < 2; i++) { 
            //System.out.println("Total no of words in document : "+i+" –> "+tottal_no_words_in_doc[i]); 
        
    }

    public void compute_num_of_doc_in_which_word_i_appears() 
    { 
        int sum = 0; 
        for (int i = 0; i < keywordList.size(); i++) { 
            for (int j = 0; j < 2; j++) 
            { 
                if((count1[j][i])>0) 
                 { 
                     sum = sum+1; 
                 } 
            } 
            num_of_doc_in_which_word_i_appears[i] = sum; 
            sum = 0; 
        }

        //for (int i = 0; i < keywordList.size(); i++) { 
            //System.out.println("word : "+i +" occured in "+num_of_doc_in_which_word_i_appears[i]+" documents "); 
        } 
    

    public void compute_TDIDF(int x , int y) throws Exception
    {

        //initializing the tdidf 
        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < keywordList.size() ; j++) { 
                tdidf[i][j]=0.00000; 
            }

        }

       // ReadingMultipleFile re = new ReadingMultipleFile(); 
        for (int i = 0; i <2; i++) 
        { 
            for (int j = 0; j < keywordList.size(); j++) 
            { 
                //System.out.println(count1[i][j]);
               // System.out.print( twoDForm.format((Double.valueOf(twoDForm.format((count1[i][j]*10000)/tottal_no_words_in_doc[i]))/10000.0)));
               tdidf[i][j] = Double.valueOf( twoDForm.format((Double.valueOf(twoDForm.format((count1[i][j]*10000)/tottal_no_words_in_doc[i]))/10000.0) * (Math.log( 50 / num_of_doc_in_which_word_i_appears[j])))).doubleValue();
               //System.out.print(tdidf[i][j]+"  ,  "); 
               //System.exit(1);
            }////for closing 
        } // for closing
/*System.out.println("ok");
        System.out.println(""); 
        System.out.println(" ************** TFIDF Matrix **************"); */
         PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("test.out")));
        for (int i = 0; i < 2; i++) { 
            for (int j = 0; j < keywordList.size(); j++) { 
                out.print(String.valueOf(tdidf[i][j]+"  ,  ")); 
            } 
            out.println("\n\n\n"); 
        }
        out.close();

        //computeSVD(); 
    }

    public void computeSVD() throws Exception 
    { 
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("tfidf.txt"));
        System.out.println(""); 
        System.out.println(" ************** TFIDF Matrix **************"); 
        System.out.println(""); 
        for (int i = 0; i <2; i++) { 
            for (int j = 0; j < keywordList.size(); j++) { 
               System.out.print(String.valueOf(tdidf[i][j]));
              System.out.println("  ,  "); 
                //System.out.println("ok");
            } 
            System.out.println(""); 
        } 
    }

}
