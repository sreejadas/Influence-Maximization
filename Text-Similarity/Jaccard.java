/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author sreeja
*/
import Jama.Matrix;
class Jaccard {
    public double computeSimilarity(Matrix source, Matrix target) {
    double intersection = 0.0D;
   for (int i = 0; i < source.getRowDimension();i++) {
         /*System.out.println(source.get(i, 0)+" "+target.get(i, 0));*/
      intersection += Math.min(source.get(i, 0), target.get(i, 0));
    }
    
    if (intersection > 0.0D) {
      double union = source.norm1() + target.norm1() - intersection;
      //System.out.println("intersection"+ intersection+" "+source.norm1()+target.norm1()+union);
      return intersection / union;
    } else {
      return 0.0D;
    }
  }
}
  
    
    
    
    

