/*******************************************************************************
 * File: FinalProject.java
 * Author: Team 6
 * Class: CS 445 - Computer Graphics
 * 
 * Assignment: Final Project
 * Date Last Modified: 5/6/2018
 * Purpose: Creates the window for the Minecraft scene to be displayed.
 ******************************************************************************/

package finalproject;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.GLU;

public class Window {
    private final String TITLE;
    private final int WIDTH;
    private final int HEIGHT;
    private DisplayMode mode;
    
    Window(String title, int width, int height) throws Exception {
        TITLE = title;
        WIDTH = width;
        HEIGHT = height;
        DisplayMode d[] = Display.getAvailableDisplayModes();
        for(int i = 0; i < d.length; i++) {
            if(d[i].getWidth() == WIDTH && d[i].getHeight() == HEIGHT && d[i].getBitsPerPixel() == 32) {
                mode = d[i];
                break;
            }
        }
        createWindow();
        initializeGL();
    }
    
    private void createWindow() throws Exception {
        Display.setTitle(TITLE);
        Display.setDisplayMode(mode);
        Display.setFullscreen(false);
        Display.create();
    }
    
    private void initializeGL() {
        glClearColor(0.5f, 0.8f, 1f, 0);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective(100, (float)mode.getWidth()/(float)mode.getHeight(), 0.1f, 300);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);
        glEnable(GL_DEPTH_TEST);
    }
    
    private DisplayMode mode() {
        return mode;
    }
}
