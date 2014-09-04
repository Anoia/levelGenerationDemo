package com.stuckinadrawer.levelGenerationDemo;

public abstract class Generator {

    static Tile[][] level;

    int levelWidth;
    int levelHeight;

    public Generator(int levelWidth, int levelHeight){
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
    }

    public abstract Tile[][] generate();

    public void initializeEmptyLevel() {
        level = new Tile[levelWidth][levelHeight];

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                level[x][y] = Tile.EMPTY;
            }
        }
    }

    void buildWalls() {
        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                if (level[x][y] == Tile.ROOM || level[x][y] == Tile.CORRIDOR) {
                    for (int xx = x - 1; xx <= x + 1; xx++) {
                        for (int yy = y - 1; yy <= y + 1; yy++) {
                            if (isInLevelBounds(xx, yy) && level[xx][yy] == Tile.EMPTY) {
                                level[xx][yy] = Tile.WALL;
                            }
                        }
                    }
                }
            }
        }
    }

    boolean isInLevelBounds(int x, int y){
        return !(x < 0 || y < 0 || x >= levelWidth || y >= levelHeight );

    }

    public Tile[][] step(){
        return level;
    }

}
