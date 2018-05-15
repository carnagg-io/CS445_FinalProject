/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalproject;

import java.nio.FloatBuffer;
import java.util.Random;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Chunk {
    
    static final int CHUNK_SIZE = 30;
    static final int CUBE_LENGTH = 2;
    private Random r;
    private Block[][][] Blocks;
    private int VBOVertexHandle;
    private int VBOColorHandle;
    private int VBOTextureHandle;
    private Texture texture;
    private float xInit;
    private float yInit;
    private float zInit;
    
    public Chunk(float xInit, float yInit, float zInit) {
        try {
            texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("C:/Users/CL4P-TP/Documents/NetBeansProjects/FinalProject/terrain.png"));
        } catch (Exception e) {
            System.out.println("Texture Error");
        }
        r = new Random();
        Blocks = new Block[CHUNK_SIZE][CHUNK_SIZE][CHUNK_SIZE];
        for(int x = 0; x < CHUNK_SIZE; ++x){
            for(int y = 0; y < CHUNK_SIZE; ++y){
                for(int z = 0; z < CHUNK_SIZE; ++z){
                    if(r.nextFloat()>0.7f){
                        Blocks[x][y][z] = new Block(BlockType.GRASS);
                    } else if (r.nextFloat()>0.6f) {
                        Blocks[x][y][z] = new Block(BlockType.DIRT);
                    } else if (r.nextFloat()>0.5f) {
                        Blocks[x][y][z] = new Block(BlockType.WATER);
                    } else if (r.nextFloat()>0.4f) {
                        Blocks[x][y][z] = new Block(BlockType.STONE);
                    } else if (r.nextFloat()>0.3f) {
                        Blocks[x][y][z] = new Block(BlockType.BEDROCK);
                    } else {
                        Blocks[x][y][z] = new Block(BlockType.SAND);
                    }
                }
            }
        }
        
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        this.xInit = xInit;
        this.yInit = yInit;
        this.zInit = zInit;
        rebuildMesh(xInit, yInit, zInit);
    }
    
    public void render() {
        glPushMatrix();
        glPushMatrix();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glVertexPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOColorHandle);
        glColorPointer(3, GL_FLOAT, 0, 0L);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBindTexture(GL_TEXTURE_2D, 1);
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);
        glDrawArrays(GL_QUADS, 0, CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE*24);
        glPopMatrix();
    }
    
    public void rebuildMesh(float xInit, float yInit, float zInit) {
        VBOColorHandle = glGenBuffers();
        VBOVertexHandle = glGenBuffers();
        VBOTextureHandle = glGenBuffers();
        FloatBuffer VertexPositionData = BufferUtils.createFloatBuffer
                (CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE*6*12);
        FloatBuffer VertexColorData = BufferUtils.createFloatBuffer
                (CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE*6*12);
        FloatBuffer VertexTextureData = BufferUtils.createFloatBuffer
                (CHUNK_SIZE*CHUNK_SIZE*CHUNK_SIZE*6*12);
        for(float x = 0; x < CHUNK_SIZE; ++x){
            for(float z = 0; z < CHUNK_SIZE; ++z) {
                for(float y = 0; y < CHUNK_SIZE; ++y) {
                    VertexPositionData.put(createCube((float)(xInit+x*CUBE_LENGTH),
                        (float)(y*CUBE_LENGTH+(int)(CHUNK_SIZE*.8)),
                        (float) (zInit + z *CUBE_LENGTH)));
                    VertexColorData.put(createCubeVertexCol(getCubeColor(Blocks[(int)x][(int)y][(int)z])));
                    VertexTextureData.put(createTexCube((float)0,(float)0,Blocks[(int)x][(int)y][(int)z]));
                }
            }
        }
        
        VertexColorData.flip();
        VertexPositionData.flip();
        VertexTextureData.flip();
        glBindBuffer(GL_ARRAY_BUFFER, VBOVertexHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexPositionData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER,VBOColorHandle);
        glBufferData(GL_ARRAY_BUFFER,VertexColorData,GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, VBOTextureHandle);
        glBufferData(GL_ARRAY_BUFFER, VertexTextureData, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
    
    public float[] createTexCube(float x, float y, Block block) {
        float offset = (1024f/16)/1024f;
        
        switch (block.ID()){
            case 0:
                return textureGrass(x, y, offset);
            case 1:
                return textureSand(x, y, offset);
            case 2:
                return textureWater(x, y, offset);
            case 3:
                return textureDirt(x, y, offset);
            case 4:
                return textureStone(x, y, offset);
            case 5:
                return textureBedrock(x, y, offset);
        }
        return textureDirt(x, y, offset);
    }
    
    private float[] textureGrass(float x, float y, float offset) {
        return new float[] {
            // BOTTOM QUAD(DOWN=+Y)
            x + offset*3, y + offset*10,
            x + offset*2, y + offset*10,
            x + offset*2, y + offset*9,
            x + offset*3, y + offset*9,
            // TOP!
            x + offset*3, y + offset*1,
            x + offset*2, y + offset*1,
            x + offset*2, y + offset*0,
            x + offset*3, y + offset*0,
            // FRONT QUAD
            x + offset*3, y + offset*0,
            x + offset*4, y + offset*0,
            x + offset*4, y + offset*1,
            x + offset*3, y + offset*1,
            // BACK QUAD
            x + offset*4, y + offset*1,
            x + offset*3, y + offset*1,
            x + offset*3, y + offset*0,
            x + offset*4, y + offset*0,
            // LEFT QUAD
            x + offset*3, y + offset*0,
            x + offset*4, y + offset*0,
            x + offset*4, y + offset*1,
            x + offset*3, y + offset*1,
            // RIGHT QUAD
            x + offset*3, y + offset*0,
            x + offset*4, y + offset*0,
            x + offset*4, y + offset*1,
            x + offset*3, y + offset*1
        };
    }
    
    private float[] textureSand(float x, float y, float offset) {
        return new float[] {
            // BOTTOM QUAD(DOWN=+Y)
            x + offset*3, y + offset*2,
            x + offset*2, y + offset*2,
            x + offset*2, y + offset*1,
            x + offset*3, y + offset*1,
            // TOP!
            x + offset*3, y + offset*2,
            x + offset*2, y + offset*2,
            x + offset*2, y + offset*1,
            x + offset*3, y + offset*1,
            // FRONT QUAD
            x + offset*2, y + offset*1,
            x + offset*3, y + offset*1,
            x + offset*3, y + offset*2,
            x + offset*2, y + offset*2,
            // BACK QUAD
            x + offset*3, y + offset*2,
            x + offset*2, y + offset*2,
            x + offset*2, y + offset*1,
            x + offset*3, y + offset*1,
            // LEFT QUAD
            x + offset*2, y + offset*1,
            x + offset*3, y + offset*1,
            x + offset*3, y + offset*2,
            x + offset*2, y + offset*2,
            // RIGHT QUAD
            x + offset*2, y + offset*1,
            x + offset*3, y + offset*1,
            x + offset*3, y + offset*2,
            x + offset*2, y + offset*2
        };
    }
    
    private float[] textureWater(float x, float y, float offset) {
        return new float[] {
            // BOTTOM QUAD(DOWN=+Y)
            x + offset*16, y + offset*13,
            x + offset*15, y + offset*13,
            x + offset*15, y + offset*12,
            x + offset*16, y + offset*12,
            // TOP!
            x + offset*16, y + offset*13,
            x + offset*15, y + offset*13,
            x + offset*15, y + offset*12,
            x + offset*16, y + offset*12,
            // FRONT QUAD
            x + offset*15, y + offset*12,
            x + offset*16, y + offset*12,
            x + offset*16, y + offset*13,
            x + offset*15, y + offset*13,
            // BACK QUAD
            x + offset*16, y + offset*13,
            x + offset*15, y + offset*13,
            x + offset*15, y + offset*12,
            x + offset*16, y + offset*12,
            // LEFT QUAD
            x + offset*15, y + offset*12,
            x + offset*16, y + offset*12,
            x + offset*16, y + offset*13,
            x + offset*15, y + offset*13,
            // RIGHT QUAD
            x + offset*15, y + offset*12,
            x + offset*16, y + offset*12,
            x + offset*16, y + offset*13,
            x + offset*15, y + offset*13
        };
    }
    
    private float[] textureDirt(float x, float y, float offset) {
        return new float[] {
            // BOTTOM QUAD(DOWN=+Y)
            x + offset*3, y + offset*1,
            x + offset*2, y + offset*1,
            x + offset*2, y + offset*0,
            x + offset*3, y + offset*0,
            // TOP!
            x + offset*3, y + offset*1,
            x + offset*2, y + offset*1,
            x + offset*2, y + offset*0,
            x + offset*3, y + offset*0,
            // FRONT QUAD
            x + offset*2, y + offset*0,
            x + offset*3, y + offset*0,
            x + offset*3, y + offset*1,
            x + offset*2, y + offset*1,
            // BACK QUAD
            x + offset*3, y + offset*1,
            x + offset*2, y + offset*1,
            x + offset*2, y + offset*0,
            x + offset*3, y + offset*0,
            // LEFT QUAD
            x + offset*2, y + offset*0,
            x + offset*3, y + offset*0,
            x + offset*3, y + offset*1,
            x + offset*2, y + offset*1,
            // RIGHT QUAD
            x + offset*2, y + offset*0,
            x + offset*3, y + offset*0,
            x + offset*3, y + offset*1,
            x + offset*2, y + offset*1
        };
    }
    
    private float[] textureStone(float x, float y, float offset) {
        return new float[] {
            // BOTTOM QUAD(DOWN=+Y)
            x + offset*2, y + offset*1,
            x + offset*1, y + offset*1,
            x + offset*1, y + offset*0,
            x + offset*2, y + offset*0,
            // TOP!
            x + offset*2, y + offset*1,
            x + offset*1, y + offset*1,
            x + offset*1, y + offset*0,
            x + offset*2, y + offset*0,
            // FRONT QUAD
            x + offset*1, y + offset*0,
            x + offset*2, y + offset*0,
            x + offset*2, y + offset*1,
            x + offset*1, y + offset*1,
            // BACK QUAD
            x + offset*2, y + offset*1,
            x + offset*1, y + offset*1,
            x + offset*1, y + offset*0,
            x + offset*2, y + offset*0,
            // LEFT QUAD
            x + offset*1, y + offset*0,
            x + offset*2, y + offset*0,
            x + offset*2, y + offset*1,
            x + offset*1, y + offset*1,
            // RIGHT QUAD
            x + offset*1, y + offset*0,
            x + offset*2, y + offset*0,
            x + offset*2, y + offset*1,
            x + offset*1, y + offset*1
        };
    }
    
    private float[] textureBedrock(float x, float y, float offset) {
        return new float[] {
            // BOTTOM QUAD(DOWN=+Y)
            x + offset*2, y + offset*2,
            x + offset*1, y + offset*2,
            x + offset*1, y + offset*1,
            x + offset*2, y + offset*1,
            // TOP!
            x + offset*2, y + offset*2,
            x + offset*1, y + offset*2,
            x + offset*1, y + offset*1,
            x + offset*2, y + offset*1,
            // FRONT QUAD
            x + offset*1, y + offset*1,
            x + offset*2, y + offset*1,
            x + offset*2, y + offset*2,
            x + offset*1, y + offset*2,
            // BACK QUAD
            x + offset*2, y + offset*2,
            x + offset*1, y + offset*2,
            x + offset*1, y + offset*1,
            x + offset*2, y + offset*1,
            // LEFT QUAD
            x + offset*1, y + offset*1,
            x + offset*2, y + offset*1,
            x + offset*2, y + offset*2,
            x + offset*1, y + offset*2,
            // RIGHT QUAD
            x + offset*1, y + offset*1,
            x + offset*2, y + offset*1,
            x + offset*2, y + offset*2,
            x + offset*1, y + offset*2
        };
    }
    
    private float[] createCubeVertexCol(float[] CubeColorArray) {
        float[] cubeColors = new float[CubeColorArray.length * 4 * 6];
        for(int i = 0; i < cubeColors.length; ++i) 
            cubeColors[i] = CubeColorArray[i%CubeColorArray.length];
        return cubeColors;
    }
    
    public static float[] createCube(float x, float y, float z) {
        int offset = CUBE_LENGTH / 2;
        return new float[]{
            // TOP QUAD
            x + offset, y + offset, z,
            x - offset, y + offset, z,
            x - offset, y + offset, z - CUBE_LENGTH,
            x + offset, y + offset, z - CUBE_LENGTH,
            // BOTTOM QUAD
            x + offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x - offset, y - offset, z,
            x + offset, y - offset, z,
            // FRONT QUAD
            x + offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            // BACK QUAD
            x + offset, y - offset, z,
            x - offset, y - offset, z,
            x - offset, y + offset, z,
            x + offset, y + offset, z,
            // LEFT QUAD
            x - offset, y + offset, z - CUBE_LENGTH,
            x - offset, y + offset, z,
            x - offset, y - offset, z,
            x - offset, y - offset, z - CUBE_LENGTH,
            // RIGHT QUAD
            x + offset, y + offset, z,
            x + offset, y + offset, z - CUBE_LENGTH,
            x + offset, y - offset, z - CUBE_LENGTH,
            x + offset, y - offset, z};
    }
    
    private float[] getCubeColor(Block block) {
        return new float[] {1, 1, 1};
    }
}