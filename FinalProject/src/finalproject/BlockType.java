package finalproject;

public enum BlockType {
        
        GRASS(0),
        SAND(1),
        WATER(2),
        DIRT(3),
        STONE(4),
        BEDROCK(5);
        
        private int ID;
        
        BlockType(int ID) {
            this.ID = ID;
        }
        
        public void ID(int ID) { this.ID = ID; }
        
        public int ID(){ return ID; }
        
    }
