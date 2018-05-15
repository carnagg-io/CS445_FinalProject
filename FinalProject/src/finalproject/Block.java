package finalproject;

public class Block {
    
    private BlockType type;
    private boolean active;
    
    private float x;
    private float y;
    private float z;
    
    public Block(BlockType type) {
        this.type = type;
    }
    
    public void ID(int ID) { type.ID(ID); }
    public void type(BlockType type) { this.type = type; }
    public void active(boolean active) { this.active = active; }
    
    public void x(float x) { this.x = x; }
    public void y(float y) { this.y = y; }
    public void z(float z) { this.z = z; }
    public void xyz(float x, float y, float z){ x(x); y(y); z(z); }
    
    public int ID() { return type.ID(); }
    public BlockType type() { return type; }
    public boolean active() { return active; }
    
    public float x() { return x; }
    public float y() { return y; }
    public float z() { return z; }
}
