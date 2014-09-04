package com.stuckinadrawer.levelGenerationDemo;

import com.stuckinadrawer.Point;
import com.stuckinadrawer.Utils;

import java.util.ArrayList;

public class RogueAlgorithm extends Generator {

    private final int numRoomsHorizontal = 3;
    private final int numRoomsVertical = 3;

    private final int roomWidth;
    private final int roomHeight;

    private Point entrance;
    private Point exit;

    private GridRoom[][] gridRooms;

    public RogueAlgorithm(int levelWidth, int levelHeight){
        super(levelWidth, levelHeight);
        roomWidth = levelWidth/numRoomsHorizontal;
        roomHeight = levelHeight/ numRoomsVertical;
    }

    public RogueAlgorithm(){
        this(70, 50);
    }

    @Override
    public Tile[][] generate() {
        initializeEmptyLevel();
        initializeRoomGrid();

        //pick random room to start in
        GridRoom startRoom = gridRooms[Utils.random(numRoomsHorizontal - 1)][numRoomsVertical-1];
        startRoom.connected = true;
        entrance = getCenterOfGridRoomInLevel(startRoom);
        ArrayList<GridRoom> unconnectedNeighbours = getUnconnectedNeighbours(startRoom);
        while(!unconnectedNeighbours.isEmpty()){
            GridRoom neighbour = unconnectedNeighbours.get(Utils.random(unconnectedNeighbours.size() - 1));
            startRoom.connectedGridRooms.add(neighbour);
            neighbour.connected = true;
            exit = getCenterOfGridRoomInLevel(neighbour);
            neighbour.connectedGridRooms.add(startRoom);
            startRoom = neighbour;
            unconnectedNeighbours = getUnconnectedNeighbours(startRoom);
        }

        ArrayList<GridRoom> unconnectedRooms = getUnconnectedRooms();
        while(!unconnectedRooms.isEmpty()){
            GridRoom room = unconnectedRooms.get(0);
            ArrayList<GridRoom> connectedNeighbours = getConnectedNeighbours(room);
            if(connectedNeighbours.isEmpty()){
                unconnectedRooms.remove(room);
                continue;
            }
            GridRoom neighbour = connectedNeighbours.get(Utils.random(connectedNeighbours.size() - 1));
            room.connected = true;
            room.connectedGridRooms.add(neighbour);
            neighbour.connectedGridRooms.add(room);
            unconnectedRooms = getUnconnectedRooms();
        }


        connectRooms();
        buildRooms();

        setStairs(entrance, Tile.ENTRANCE);
        setStairs(exit, Tile.EXIT);

        buildWalls();
        return level;
    }

    private void setStairs(Point point, Tile tile){
        for(int x = point.getX()-1; x <= point.getX()+1; x++) {
            for (int y = point.getY()-1; y <= point.getY()+1; y++) {
                level[x][y] = Tile.ROOM;
            }
        }

        level[point.getX()][point.getY()] = tile;
    }

    private void buildRooms() {
        for(int x = 0; x < numRoomsHorizontal; x++){
            for(int y = 0; y < numRoomsVertical; y++){
                GridRoom room = gridRooms[x][y];
                int rand = Utils.random(8);
                if(rand == 0){
                    Point center = getCenterOfGridRoomInLevel(room);
                    level[center.getX()][center.getY()] = Tile.CORRIDOR;
                }else{
                    int width = Utils.random(roomWidth / 2, roomWidth - 2);
                    int height = Utils.random(roomHeight / 2, roomHeight - 2);
                    int xPos = Utils.random(x * roomWidth + 1, x * roomWidth + (roomWidth - width));
                    int yPos = Utils.random(y * roomHeight + 1, y * roomHeight + (roomHeight - height));
                    for (int i = xPos; i < width + xPos; i++) {
                        for (int j = yPos; j < height + yPos; j++) {

                            level[i][j] = Tile.ROOM;

                        }
                    }
                }
            }
        }

    }

    private void connectRooms() {
        for(int x = 0; x < numRoomsHorizontal; x++){
            for(int y = 0; y < numRoomsVertical; y++){
                GridRoom room = gridRooms[x][y];
                for(GridRoom connectedRoom: room.connectedGridRooms){
                    buildCorridor(getCenterOfGridRoomInLevel(room), getCenterOfGridRoomInLevel(connectedRoom));
                    connectedRoom.connectedGridRooms.remove(room); //optional? makes it not do corridors twice
                }
            }
        }

    }

    private void buildCorridor(Point start, Point end){
        int pointAX = start.getX();
        int pointAY = start.getY();
        int pointBX = end.getX();
        int pointBY = end.getY();

        while ((pointBX != pointAX) || (pointBY != pointAY)) {
            if (pointBX != pointAX) {
                if (pointBX > pointAX) pointBX--;
                else pointBX++;
            } else {
                if (pointBY > pointAY) pointBY--;
                else pointBY++;
            }

            level[pointBX][pointBY] = Tile.CORRIDOR;
        }
    }

    private ArrayList<GridRoom> getUnconnectedRooms() {
        ArrayList<GridRoom> unconnectedRooms = new ArrayList<GridRoom>();
        for (int x = 0; x < numRoomsHorizontal; x++) {
            for (int y = 0; y < numRoomsVertical; y++) {
                GridRoom r = gridRooms[x][y];
                if(!r.connected){
                    unconnectedRooms.add(r);
                }
            }
        }
        return unconnectedRooms;
    }

    private ArrayList<GridRoom> getUnconnectedNeighbours(GridRoom r){
        ArrayList<GridRoom> unconnectedNeighbours = new ArrayList<GridRoom>();
        for(int x = r.x-1; x <=r.x+1; x++){
            for(int y = r.y-1; y <= r.y+1; y++){
                if(isValidGridRoomIndex(x, y)){
                    GridRoom neighbour = gridRooms[x][y];
                    if(!neighbour.connected){
                        unconnectedNeighbours.add(neighbour);
                    }

                }
            }
        }

        return unconnectedNeighbours;
    }

    private ArrayList<GridRoom> getConnectedNeighbours(GridRoom r){
        ArrayList<GridRoom> connectedNeighbours = new ArrayList<GridRoom>();
        for(int x = r.x-1; x <=r.x+1; x++){
            for(int y = r.y-1; y <= r.y+1; y++){
                if(isValidGridRoomIndex(x, y)){
                    GridRoom neighbour = gridRooms[x][y];
                    if(neighbour.connected){
                        connectedNeighbours.add(neighbour);
                    }

                }
            }
        }

        return connectedNeighbours;
    }

    private Point getCenterOfGridRoomInLevel(GridRoom r){
        return new Point(r.x*roomWidth+roomWidth/2, r.y*roomHeight+roomHeight/2);
    }

    private boolean isValidGridRoomIndex(int x, int y) {
        return !(x < 0 || y < 0 || x >= numRoomsHorizontal || y >= numRoomsVertical );
    }

    private void initializeRoomGrid() {
        gridRooms = new GridRoom[numRoomsHorizontal][numRoomsVertical];
        for (int x = 0; x < numRoomsHorizontal; x++) {
            for (int y = 0; y < numRoomsVertical; y++) {
                gridRooms[x][y] = new GridRoom(x, y);
            }
        }
    }

    private class GridRoom {
        final int x;
        final int y;
        boolean connected = false;
        ArrayList<GridRoom> connectedGridRooms = new ArrayList<GridRoom>();

        public GridRoom(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
}
