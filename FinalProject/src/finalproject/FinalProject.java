/*******************************************************************************
 * File: FinalProject.java
 * Author: Team 6
 * Class: CS 445 - Computer Graphics
 * 
 * Assignment: Final Project
 * Date Last Modified: 5/6/2018
 * Purpose: Create a basic Minecraft scene with random creation between each
 *          run of the program.
 ******************************************************************************/

package finalproject;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class FinalProject {
    
    private static void drawCube(float scale) {
        float location = 0.5f * scale;
        
        try {
            // TOP (RED)
            glColor3f(1, 0, 0);
            glBegin(GL_QUADS);
            glVertex3f(location, location, -location);
            glVertex3f(-location, location, -location);
            glVertex3f(-location, location, location);
            glVertex3f(location, location, location);
            glEnd();
            
            // BOTTOM (ORANGE)
            glColor3f(1, 0.5f, 0);
            glBegin(GL_QUADS);
            glVertex3f(location, -location, location);
            glVertex3f(-location, -location, location);
            glVertex3f(-location, -location, -location);
            glVertex3f(location, -location, -location);
            glEnd();
            
            // FRONT (YELLOW)
            glColor3f(1, 1, 0);
            glBegin(GL_QUADS);
            glVertex3f(location, location, location);
            glVertex3f(-location, location, location);
            glVertex3f(-location, -location, location);
            glVertex3f(location, -location, location);
            glEnd();
            
            // BACK (GREEN)
            glColor3f(0, 1, 0);
            glBegin(GL_QUADS);
            glVertex3f(location, -location, -location);
            glVertex3f(-location, -location, -location);
            glVertex3f(-location, location, -location);
            glVertex3f(location, location, -location);
            glEnd();
            
            // LEFT (BLUE)
            glColor3f(0, 0, 1);
            glBegin(GL_QUADS);
            glVertex3f(-location, location, location);
            glVertex3f(-location, location, -location);
            glVertex3f(-location, -location, -location);
            glVertex3f(-location, -location, location);
            glEnd();
            
            // RIGHT (PURPLE)
            glColor3f(0.5f, 0, 1);
            glBegin(GL_QUADS);
            glVertex3f(location, location, -location);
            glVertex3f(location, location, location);
            glVertex3f(location, -location, location);
            glVertex3f(location, -location, -location);
            glEnd();
        } catch(Exception failure) {
            failure.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        try {
            Mouse.setGrabbed(true);
            
            Window window = new Window("Final Project", 640, 480);
            FirstPersonCameraController controller = new FirstPersonCameraController(0, 0, 0, 0, 0);
            
            float mouseSensitivity = 0.09f;
            float movementSpeed = 0.35f;
            
            Chunk chunk = new Chunk(-5, -5, -5);
            
            while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                controller.yaw(Mouse.getDX() * mouseSensitivity);
                controller.pitch(-Mouse.getDY() * mouseSensitivity);
                
                if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
                    controller.up(movementSpeed);
                if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
                    controller.down(movementSpeed);
                if(Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP))
                    controller.forward(movementSpeed);
                if(Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN))
                    controller.backward(movementSpeed);
                if(Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
                    controller.right(movementSpeed);
                if(Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT))
                    controller.left(movementSpeed);
                
                glLoadIdentity();
                controller.look();
                glEnableClientState(GL_VERTEX_ARRAY);
                glEnableClientState(GL_COLOR_ARRAY);
                glEnable(GL_TEXTURE_2D);
                glEnableClientState(GL_TEXTURE_COORD_ARRAY);
                glEnable(GL_DEPTH_TEST);
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
<<<<<<< HEAD
                // drawCube(50);
=======
                //drawCube(50);
>>>>>>> cbb7fb04350ce7ac8d259bf7311ed297cfae92da
                //chunk.rebuildMesh(0, 0, 0);
                chunk.render();
                
                Display.update();
                Display.sync(60);
            }
            
            Display.destroy();
        } catch(Exception failure) {
            failure.printStackTrace();
        }
    }
    
}
