package finalproject;

import java.util.Random;

public class Simplex {
    final private int MAX;
    final private int WIDTH;
    final private int LENGTH;
    private float[][] noise;
    
    Simplex(int max, int width, int length) {
        MAX = max;
        WIDTH = width;
        LENGTH = length;
        noise = new float[WIDTH][LENGTH];
        Random rand = new Random();
        for(int x = 0; x < WIDTH; ++x)
            for(int y = 0; y < LENGTH; ++y)
                noise[x][y] = (float)(rand.nextInt(MAX - 1) + 1) / (float)MAX;
    }
    
    public float[][] noise() { return noise; }
    
    public void smooth() {
        for(int x = 0; x < WIDTH; ++x)
            for(int y = 0; y < LENGTH; ++y)
                noise[x][y] = smooth(x, y);
    }
    
    private float smooth(float x, float y) {
        float xFrac = x - (int)x;
        float yFrac = y - (int)y;
        
        int x1 = ((int)x + WIDTH) % WIDTH;
        int y1 = ((int)y + LENGTH) % LENGTH;
        
        int x2 = ((int)x1 + WIDTH - 1) % WIDTH;
        int y2 = ((int)y1 + LENGTH - 1) % LENGTH;
        
        float value = 0f;
        
        value += xFrac * yFrac * noise[x1][y1];
        value += xFrac * (1 - yFrac) * noise[x1][y2];
        value += (1 - xFrac) * yFrac * noise[x2][y1];
        value += (1 - xFrac) * (1 - yFrac) * noise[x2][y2];
        
        return value;
    }
}
