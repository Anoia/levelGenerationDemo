package com.stuckinadrawer.levelGenerationDemo;

import com.stuckinadrawer.Utils;

import java.util.ArrayList;

public class BSP extends Generator{

    private final static int MAX_LEAF_SIZE = 30;

    public BSP(int levelWidth, int levelHeight){
        super(levelWidth, levelHeight);
    }

    public BSP(){
        this(70, 50);
    }

    @Override
    public Tile[][] generate(){
        initializeEmptyLevel();
        ArrayList<Leaf> leaves = new ArrayList<Leaf>();

        // Create new leaf that is root of all other leaves
        Leaf root = new Leaf(0, 0, levelWidth, levelHeight);
        leaves.add(root);
        boolean didSplit = true;
        // loop through every Leaf in List over and over again, until no more Leaves can be split.
        while(didSplit){
            didSplit = false;
            for(int i = 0; i < leaves.size(); i++){
                Leaf leaf = leaves.get(i);
                System.out.println("iterating");
                //check if this leaf is already split
                if(leaf.rightChild == null && leaf.rightChild == null){
                    // if this leaf is too big or  75% chance
                    System.out.println("ready to split");
                    if (leaf.width > MAX_LEAF_SIZE || leaf.height > MAX_LEAF_SIZE || Utils.random() > 0.25){
                        //Split the leaf
                        if(leaf.split()){
                            //if split was successful
                            leaves.add(leaf.leftChild);
                            leaves.add(leaf.rightChild);
                            didSplit = true;
                        }
                    }

                }
            }
        }

        root.createRooms();

        //fill level with tiles
        for(Leaf leaf: leaves){
            if(leaf.room != null){
                Room r = leaf.room;
                for(int x = 0; x < r.width; x++){
                    for(int y = 0; y < r.height; y++){
                        level[r.x + x][r.y + y] = Tile.ROOM;
                    }
                }
            }
        }

        buildWalls();

        return level;
    }
}
