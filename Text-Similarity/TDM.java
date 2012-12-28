/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import Jama.Matrix;

/**
*
* @author shakthydoss
*/
public class TDM {
    public static  ArrayList<String> keywordList = new  ArrayList<String>();
    public static  ArrayList<String> keywordDoc = new  ArrayList<String>();
    public static  ArrayList<String> key_back = new  ArrayList<String>();
    
    public static  double[][] count1;
    public static  int lines=0;
    public static double[][] tdidf;
    public static double jc_tdidf;
    public static double jc_lsi;
    
    public static void main(String[] args) throws Exception {
        
        String s ,temp,s1;
        StringTokenizer st ;
        int countKeyword =0;
        
        Mystemmer stem = new Mystemmer();
        
        
        StopWord swl = new StopWord();
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("keywords.txt"));
        PrintWriter o1 = new PrintWriter(new BufferedWriter(new FileWriter("out.tsv")));
        o1.println("Name\t"+"Link\t"+"Similarity with tdidf\t"+"Similarity with lsi\t"+"Description");


                   BufferedReader br = new BufferedReader(new FileReader("new_t.tsv"));

                   
        
        while((s=br.readLine())!=null)
        {
            ++lines;
            
        }
        //System.out.println("nUmber of Lines = "+lines);
        BufferedReader br1 = new BufferedReader(new FileReader("i.txt"));
        while((s1=br1.readLine())!=null){
            StringTokenizer st1=new StringTokenizer(s1, " ;,.'", false);
            while(st1.hasMoreTokens()){
                String  t=st1.nextToken();
                // System.out.println(t);
                if(swl.stopwords.contains(t.toLowerCase()))
                {
                }
                else
                {
                    t = stem.DoSuffixStremmer(t);
                    // put the stemmer here
                    if(keywordList.contains(t.toLowerCase())==false) //checking in keyword_array

                    {
                        keywordList.add(t.toLowerCase()); // adding keyword to keyword_array
                        bw.write(t);
                        countKeyword++;
                        bw.newLine();
                        // System.out.println("count  "+countKeyword);
                    }
                }
            }
        } // while ends
        // while ends
        
        key_back.addAll(keywordList);
        bw.close();       
        int   i=0;
        br = new BufferedReader(new FileReader("new_t.tsv"));
        while((s=br.readLine())!=null)
        {
            keywordList.clear();
            keywordDoc.clear();
            keywordList.addAll(key_back);
            st = new StringTokenizer(s, "\t", false);
            
            temp = st.nextToken();
            //System.out.println(temp);
            o1.print(temp+"\t");
            temp = st.nextToken();
            //System.out.println(temp);
            o1.print(temp+"\t");
            temp = st.nextToken();
            // System.out.println(temp);
            StringTokenizer st1=new StringTokenizer(temp, " ,;.:'", false);
            while(st1.hasMoreTokens()){
                String  t=st1.nextToken();
                
                if(swl.stopwords.contains(t.toLowerCase())||t.length()<3)
                {
                    
                    //System.out.println(temp
                }
                else
                {                    
                    t = stem.DoSuffixStremmer(t);
                    // put the stemmer hereif(keywordList.contains(t)==false) //checking in
                    //keyword_array
                    if(keywordDoc.contains(t.toLowerCase())==false && keywordList.contains(t.toLowerCase())==false)
                    {
                        keywordDoc.add(t.toLowerCase()); // adding keyword to keyword_array                        
                    }
            }}
            keywordList.addAll(keywordDoc);
            
            count1=new double[2][keywordList.size()];
            for(i=0;i<2;++i)
            {
                for(int j=0;j<keywordList.size();++j)
                count1[i][j]=0;
            }
            
            StringTokenizer st2=new StringTokenizer(temp, " ,;.:'", false);
            while(st2.hasMoreTokens()){
                String  t1=st2.nextToken();
                
                if(swl.stopwords.contains(t1.toLowerCase())||t1.length()<3)
                {
                    
                    //System.out.println(temp
                }
                else
                {
                    t1 = stem.DoSuffixStremmer(t1);
                    // put the stemmer hereif(keywordList.contains(t)==false) //checking in
                    //keyword_array
                    if(keywordList.contains(t1.toLowerCase())==true)
                    {
                        count1[1][keywordList.indexOf(t1.toLowerCase())] = count1[1][keywordList.indexOf(t1.toLowerCase())] + 1;
                    }
                }
            }
            br1 = new BufferedReader(new FileReader("i.txt"));
            while((s1=br1.readLine())!=null){
                st1=new StringTokenizer(s1, " ;,.", false);
                while(st1.hasMoreTokens()){
                    String  t=st1.nextToken();
                    // System.out.println(t);
                    if(swl.stopwords.contains(t.toLowerCase()))
                    {
                    }
                    else
                    {
                        t = stem.DoSuffixStremmer(t);
                        // put the stemmer here
                        if(keywordList.contains(t.toLowerCase())==true) //checking in keyword_array
                        {
                            count1[0][keywordList.indexOf(t.toLowerCase())] = count1[0][keywordList.indexOf(t.toLowerCase())] + 1;
                            // System.out.println("count  "+countKeyword);
                        }
                    }
                }
            }
            TDIDF_Matrix tM = new TDIDF_Matrix();
            tM.compute_tottal_no_words_in_doc();
            tM.compute_num_of_doc_in_which_word_i_appears();
            tM.compute_TDIDF(2, keywordList.size());
            LSI_Matrix lsi=new LSI_Matrix(tdidf);
            Matrix lsi_m= lsi.compute_LSI();
            //System.out.println("here"+ lsi_m.getColumnDimension()+" "+lsi_m.getRowDimension());
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("lsi.out")));
            for(i=0;i<lsi_m.getRowDimension();++i)
            {
                for(int j=0;j<lsi_m.getColumnDimension();++j)
                {
                    out.println(lsi_m.get(i, j));
                }
                out.println("\n\n");
            }
            
            Jaccard jc=new  Jaccard();
            
            jc_tdidf=jc.computeSimilarity(new Matrix(tdidf[0],1).transpose(),new Matrix(tdidf[1],1).transpose());
            jc_lsi=jc.computeSimilarity(lsi_m.getMatrix(0,0,0,lsi_m.getColumnDimension()-1),lsi_m.getMatrix(1,1,0,lsi_m.getColumnDimension()-1));
            out.close();
            
            // for each document
            o1.println(jc_tdidf+"\t"+jc_lsi+"\t"+temp);
        }
        
        bw.close();
        o1.close();
    }// main closin
} // class closing
