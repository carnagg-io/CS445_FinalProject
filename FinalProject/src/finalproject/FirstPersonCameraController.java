package finalproject;

import static org.lwjgl.opengl.GL11.*;

public class FirstPersonCameraController extends Camera {
    
    private float pitch;
    private float yaw;
    
    FirstPersonCameraController(float x, float y, float z, float pitch, float yaw) {
        super(x, y, z);
        this.pitch = pitch;
        this.yaw = yaw;
    }
    
    public void pitch(float change) {
        pitch = pitch + change;
    }
    public void yaw(float change) {
        yaw = yaw + change;
    }
    
    public void up(float distance) {
        y = y - distance;
    }
    
    public void down(float distance) {
        y = y + distance;
    }
    
    public void forward(float distance) {
        double radians = Math.toRadians(yaw);
        x = x - distance * (float)Math.sin(radians);
        z = z + distance * (float)Math.cos(radians);
    }
    
    public void backward(float distance) {
        double radians = Math.toRadians(yaw);
        x = x + distance * (float)Math.sin(radians);
        z = z - distance * (float)Math.cos(radians);
    }
    
    public void right(float distance) {
        double radians = Math.toRadians(yaw + 90);
        x = x - distance * (float)Math.sin(radians);
        z = z + distance * (float)Math.cos(radians);
    }
    
    public void left(float distance) {
        double radians = Math.toRadians(yaw - 90);
        x = x - distance * (float)Math.sin(radians);
        z = z + distance * (float)Math.cos(radians);
    }
    
    public void look() {
        glRotatef(pitch, 1, 0, 0);
        glRotatef(yaw, 0, 1, 0);
        glTranslatef(x, y, z);
    }
}
