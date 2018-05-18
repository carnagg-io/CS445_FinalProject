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
    
    public int[][] findLakeLocation(){
        int[] startLocation = new int[2];
        boolean flat = false;
        Random rand = new Random();
        int x, z, height;
        
        while(!flat){
            x = rand.nextInt(24)+1;
            z = rand.nextInt(24)+1;
            height = (int)maxHeights[x][z];
            flat = true;
            
            for(int i = x; i < x+5; ++i){
                for(int j = z; j < z+5; ++j){
                    if((int)maxHeights[i][j] != height)
                        flat = false;
                }
            }
            
            if(flat){
                startLocation[0] = x;
                startLocation[1] = z;
            }
        }
        
        return placeLake(startLocation);
    }
    
    private int[][] placeLake(int[] startLocation){
        int[][] coordinates = new int[16][2];
        int counter = 0;
        
        for(int x = startLocation[0]; x < startLocation[0]+5; ++x){
            for(int z = startLocation[1]; z < startLocation[1]+5; ++z){
                if(x == startLocation[0] && z >= startLocation[1]+3){
                    coordinates[counter][0] = x;
                    coordinates[counter++][1] = z;
                }else if(x == startLocation[0]+1 && z >= startLocation[1]+2) {
                    coordinates[counter][0] = x;
                    coordinates[counter++][1] = z;
                }else if(x == startLocation[0]+2 && z >= startLocation[1]+1) {
                    coordinates[counter][0] = x;
                    coordinates[counter++][1] = z;
                }else if(x == startLocation[0]+3 && z < startLocation[1]+4) {
                    coordinates[counter][0] = x;
                    coordinates[counter++][1] = z;
                }else if(x == startLocation[0]+4 && z < startLocation[1]+3) {
                    coordinates[counter][0] = x;
                    coordinates[counter++][1] = z;
                }
            }
        }
        
        return coordinates;
    }
    
    public float getHeightAt(int x, int z){
        return maxHeights[x][z];
    }
    
    public float[][] getHeights(){
        return maxHeights;
    }
}
