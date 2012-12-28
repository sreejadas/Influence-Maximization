/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author sreeja
 */

import Jama.Matrix;
import Jama.SingularValueDecomposition;
import java.io.*;
import java.util.*;
import java.math.BigDecimal;
public class LSI_Matrix extends TDM{
    Matrix matrix;
    public LSI_Matrix(double [][]count1){
        
        matrix=new Matrix(count1);
    }
    public Matrix compute_LSI()throws Exception
    {
    // phase 1: Singular value decomposition
        
        matrix=matrix.transpose();
    SingularValueDecomposition svd = new SingularValueDecomposition(matrix);
    Matrix wordVector = svd.getU();
    Matrix sigma = svd.getS();
    Matrix documentVector = svd.getV();
    // compute the value of k (ie where to truncate)
    int k = (int) Math.floor(Math.sqrt(matrix.getColumnDimension()));
    Matrix reducedWordVector = wordVector.getMatrix(
      0, wordVector.getRowDimension() - 1, 0, k - 1);
    
     PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("word.out")));
    for(int i=0;i<wordVector.getRowDimension();++i)
    {
         for(int j=0;j<wordVector.getColumnDimension();++j)
         {
                                   out.println(wordVector.get(i, j));
          }
                              out.println("\n");
      }
    
    Matrix reducedSigma = sigma.getMatrix(0, k - 1, 0, k - 1);
    Matrix reducedDocumentVector = documentVector.getMatrix(
      0, documentVector.getRowDimension() - 1, 0, k - 1);
    Matrix weights = wordVector.times(
      sigma).times(documentVector.transpose());//
    // Phase 2: normalize the word scrores for a single document
    for (int j = 0; j < weights.getColumnDimension(); j++) {
      double sum = sum(weights.getMatrix(
        0, weights.getRowDimension() - 1, j, j));
      for (int i = 0; i < weights.getRowDimension(); i++) {
         out.println(i+" "+j+" "+sum+" "+weights.get(i, j)+ " "+ BigDecimal.valueOf((Math.abs((weights.get(i, j)) / sum)))+" "+matrix.get(i, j)+" "+tdidf[j][i]);
        weights.set(i, j, Math.abs((weights.get(i, j)) / sum));
      }
    }
    out.close();
    return weights;
  }

  private double sum(Matrix colMatrix) {
    double sum = 0.0D;
    for (int i = 0; i < colMatrix.getRowDimension(); i++) {
      sum += colMatrix.get(i, 0);
    }
    return sum;
    
  }
    
}
