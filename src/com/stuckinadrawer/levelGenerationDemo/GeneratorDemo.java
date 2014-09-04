package com.stuckinadrawer.levelGenerationDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GeneratorDemo extends JFrame{

    private Tile[][] level;
    private final int tileSize = 16;
    private Generator g;

    public GeneratorDemo(){
        //g = new GeneratorBSPLayout();
        g = new ScatterLayout();
        this.level = g.generate();
        initGUI();
    }

    private void initGUI() {
        setTitle("LevelGenerator: ScatterLayout");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        add(new MyPanel());
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
             //   System.out.println(e.getKeyCode());
                switch(e.getKeyCode()){
                    case 32:
                        createNewLevel();
                        break;
                    case 10:
                        step();
                        break;
                    case 83:
                        //scatter
                        setTitle("LevelGenerator: ScatterLayout");
                        System.out.println("Now using ScatterLayout to generate Level");
                        g = new ScatterLayout();
                        createNewLevel();
                        break;
                    case 66:
                        //bsp
                        setTitle("LevelGenerator: BSP-Tree");
                        System.out.println("Now using BSP-Tree to generate Level");
                        g = new BSP();
                        createNewLevel();
                        break;
                    case 82:
                        //rogue
                        setTitle("LevelGenerator: Rogue Algorithm");
                        System.out.println("Now using Rogue Algorithm to generate Level");
                        g = new RogueAlgorithm();
                        createNewLevel();
                        break;
                    case 67:
                        //cave
                        setTitle("LevelGenerator: Cave Cellular Automata");
                        System.out.println("Now using Cellular Automata to generate Cave");
                        g = new CaveCellularAutomata();
                        createNewLevel();
                        break;
                    case 86:
                        //caveWalk
                        setTitle("LevelGenerator: CaveWalk Algorithm");
                        System.out.println("Now using CaveWalk Algorithm to generate Cave");
                        g = new CaveWalk();
                        createNewLevel();
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    private void step() {
        level = g.step();
        repaint();
    }


    private class MyPanel extends JPanel {
        private void draw(Graphics g){

            //this is where the drawing happens
            Graphics2D g2d = (Graphics2D) g;

            for (int x = 0; x < level.length; x++) {
                for (int y = 0; y < level[0].length; y++) {
                    g2d.setColor(Color.black);
                    switch (level[x][y]) {
                        case EMPTY:

                            break;
                        case WALL:
                            g2d.setColor(Color.red);
                            break;
                        case ROOM:
                            g2d.setColor(Color.gray);
                            break;
                        case CORRIDOR:
                            g2d.setColor(Color.darkGray);
                            break;
                        case ENTRANCE:
                            g2d.setColor(Color.green);
                            break;
                        case EXIT:
                            g2d.setColor(Color.blue);
                            break;
                    }

                    g2d.fillRect(x*tileSize, y*tileSize, tileSize, tileSize);
                    g2d.setColor(Color.black);
                    g2d.drawRect(x*tileSize, y*tileSize, tileSize, tileSize);
                }
            }

        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponents(g);
            draw(g);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(level.length*tileSize, level[0].length*tileSize);
        }
    }

    void createNewLevel(){
        level = g.generate();
        repaint();
    }

    public static void main(String [] arg){
        new GeneratorDemo();
    }
}
