package com.stuckinadrawer.levelGenerationDemo;

import com.stuckinadrawer.Utils;

public class CaveCellularAutomata extends Generator {

    private int step = 0;

    public CaveCellularAutomata(int levelWidth, int levelHeight){
        super(levelWidth, levelHeight);

    }

    public CaveCellularAutomata(){
        this(70, 50);
    }

    @Override
    public Tile[][] generate() {
        initializeCave();
        step = 0;
        doStuff();
        doStuff();
        doStuff();
        doStuff();
        doStuff();
        return level;
    }

    @Override
    public Tile[][] step(){
        doStuff();
        step++;
        return level;
    }

    private void doStuff() {
        Tile[][] newLevel = new Tile[levelWidth][levelHeight];

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {

                if(x==0 || y == 0 || x == levelWidth-1 || y == levelHeight-1){
                    newLevel[x][y] = Tile.EMPTY;
                }else{
                    int walls = getAmountOfWallsAroundTile(x, y, 1);

                    if(level[x][y] == Tile.EMPTY){
                        newLevel[x][y] = (walls  >= 4)?Tile.EMPTY:Tile.ROOM;

                    }else{
                        newLevel[x][y] = (walls  >= 5)?Tile.EMPTY:Tile.ROOM;
                    }

                    if(step < 3 && getAmountOfWallsAroundTile(x, y, 3)<1){
                        newLevel[x][y] = Tile.EMPTY;
                    }


                }



            }
        }

        level = newLevel;


    }

    private int getAmountOfWallsAroundTile(int x, int y, int range) {
        int amount = 0;
        for(int i = x-range; i<=x+range; i++){
            for(int j = y-range; j<=y+range; j++){
                if(i >= 0 && i < levelWidth && j >= 0 && j < levelHeight) {
                    if(!(x==i && y==j) && level[i][j] == Tile.EMPTY){
                        amount++;
                    }
                }
            }
        }

        return amount;
    }


    private void initializeCave() {
        level = new Tile[levelWidth][levelHeight];

        for (int x = 0; x < levelWidth; x++) {
            for (int y = 0; y < levelHeight; y++) {
                int rand = Utils.random(1, 100);
                if(rand>45){
                    level[x][y] = Tile.ROOM;
                }else{
                    level[x][y] = Tile.EMPTY;
                }

                if(x==0 || y == 0 || x == levelWidth-1 || y == levelHeight-1){
                    level[x][y] = Tile.EMPTY;
                }

            }
        }
    }
}
