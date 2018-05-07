package finalproject;

public class Camera {
    
    protected float x;
    protected float y;
    protected float z;
    
    Camera(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public void x(float x) { this.x = x; }
    public void y(float y) { this.y = y; }
    public void z(float z) { this.z = z; }
    
    public float x() { return x; }
    public float y() { return y; }
    public float z() { return z; }
}
