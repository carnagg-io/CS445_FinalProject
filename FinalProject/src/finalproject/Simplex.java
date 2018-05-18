/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.util.Random;

public class Simplex {
    
    private float[][] maxHeights;
    
    public Simplex(int largestFeature, double persistence, int chunkSize) {
        Random rand = new Random();
        SimplexNoise noise = new SimplexNoise(largestFeature, persistence, rand.nextInt());
        maxHeights = new float[chunkSize][chunkSize];
        
        for(int x = 0; x < chunkSize; ++x){
            for(int z = 0; z < chunkSize; ++z){
                maxHeights[x][z] = (chunkSize * (float)noise.getNoise((int)x, (int)z)) + 27;
            }
        }
    }
    
    public float getHeightAt(int x, int z){
        return maxHeights[x][z];
    }
    
    public float[][] getHeights(){
        return maxHeights;
    }
}
