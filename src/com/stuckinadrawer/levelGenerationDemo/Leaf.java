package com.stuckinadrawer.levelGenerationDemo;

import com.stuckinadrawer.Point;
import com.stuckinadrawer.Utils;

public class Leaf {

    private final static int MIN_LEAF_SIZE = 10;

    public int x;
    public int y;
    public int width;
    public int height;

    public Leaf leftChild, rightChild = null;

    public Room room = null;

    public Leaf(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * split the leaf into two children
     * @return boolean if true the splitting was successful
     */
    public boolean split(){
        if(leftChild!= null || rightChild != null){
            return false;
        }

        // determine direction of split
        // if the width is >25% larger than height, split vertically
        // if the height is >25% larger than the width, split horizontally
        // otherwise split randomly

        boolean splitH = Utils.randomBoolean();
        if (width > height && height / width >= 0.05)
            splitH = false;
        else if (height > width && width / height >= 0.05)
            splitH = true;

        int max = (splitH ? height : width) - MIN_LEAF_SIZE;

        if(max < MIN_LEAF_SIZE)
            return false;

        int split = Utils.random(MIN_LEAF_SIZE, max);

        // create left and right children based on the direction of the split
        if(splitH){
            leftChild = new Leaf(x, y, width, split);
            rightChild = new Leaf(x, y+split, width, height-split);
        }else{
            leftChild = new Leaf(x, y, split, height);
            rightChild = new Leaf(x + split, y, width - split, height);
        }

        return true;
    }

    public void createRooms(){
        if (leftChild != null || rightChild != null)
        {
            // this leaf has been split, so go into the children leafs
            if (leftChild != null)
            {
                leftChild.createRooms();
            }
            if (rightChild != null)
            {
                rightChild.createRooms();
            }

            // if there are both left and right children in this Leaf, create a hallway between them
            if (leftChild != null && rightChild != null)
            {
                createHallway(leftChild.getRoom(), rightChild.getRoom());
            }

        }
        else
        {
            // this Leaf is the ready to make a room
            Point roomSize;
            Point roomPos;

            roomSize = new Point(Utils.random(5, width - 2), Utils.random(5, height - 2));

            roomPos = new Point(Utils.random(1, width - roomSize.getX() - 1), Utils.random(1, height - roomSize.getY() - 1));
            room = new Room(x + roomPos.getX(), y + roomPos.getY(), roomSize.getX(), roomSize.getY());


            for(int x = 0; x < room.width; x++){
                for(int y = 0; y < room.height; y++){
                    BSP.level[room.x + x][room.y + y] = Tile.ROOM;
                }
            }


        }
    }

    public void createHallway(Room roomA, Room roomB){
        int pointAX = Utils.random(roomA.x + 1, roomA.x + roomA.width - 1);
        int pointAY = Utils.random(roomA.y + 1, roomA.y + roomA.height - 1);

        int pointBX = Utils.random(roomB.x + 1, roomB.x + roomB.width - 1);
        int pointBY = Utils.random(roomB.y + 1, roomB.y + roomB.height - 1);

        while ((pointBX != pointAX) || (pointBY != pointAY)) {
            if (pointBX != pointAX) {
                if (pointBX > pointAX) pointBX--;
                else pointBX++;
            } else {
                if (pointBY > pointAY) pointBY--;
                else pointBY++;
            }

            BSP.level[pointBX][pointBY] = Tile.CORRIDOR;
        }
    }

    public Room getRoom()
    {
        // iterate all the way through these leafs to find a room, if one exists.
        if (room != null)
            return room;
        else
        {
            Room lRoom = null;
            Room rRoom = null;
            if (leftChild != null)
            {
                lRoom = leftChild.getRoom();
            }
            if (rightChild != null)
            {
                rRoom = rightChild.getRoom();
            }
            if (lRoom == null && rRoom == null)
                return null;
            else if (rRoom == null)
                return lRoom;
            else if (lRoom == null)
                return rRoom;
            else if (Utils.random() > .5)
                return lRoom;
            else
                return rRoom;
        }
    }


}
