package com.stuckinadrawer.levelGenerationDemo;

import com.stuckinadrawer.Utils;

public class CaveWalk extends Generator {

    public CaveWalk(int levelWidth, int levelHeight){
        super(levelWidth, levelHeight);

    }

    private int currentX;
    private int currentY;

    int floorTiles;

    public CaveWalk(){
        this(70, 50);
    }

    @Override
    public Tile[][] generate() {
        initializeEmptyLevel();
        currentX = levelWidth/2;
        currentY = levelHeight/2;
        level[currentX][currentY] = Tile.ROOM;
        floorTiles = levelWidth*levelHeight/3;
        doStuff();
        return level;
    }

    @Override
    public Tile[][] step(){
        doStuff();
        return level;
    }

    private void doStuff() {
      //  for(int i = 0; i < 100; i++){
        while (floorTiles > 0){
            int rand = Utils.random(1, 4);
            switch(rand){
                case 1:
                    if(currentX+1 < levelWidth-1){
                        currentX++;
                    }
                    break;
                case 2:
                    if(currentX-1 > 0){
                        currentX--;
                    }
                    break;
                case 3:
                    if(currentY+1 < levelHeight-1){
                        currentY++;
                    }
                    break;
                case 4:
                    if(currentY-1 > 0){
                        currentY--;
                    }
                    break;
            }

            if(level[currentX][currentY] == Tile.EMPTY){
                level[currentX][currentY] = Tile.ROOM;
                floorTiles--;
            }


        }


    }

}
