import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Arrays;
import java.util.Random;

public class GamePanel extends JPanel {

    private static final Object lock = new Object();
    private CellPanel[][] allPanels;
    private Block[] shape;

    private int shapeColor;
    private Block[] nextShape;

    private int dropSpeed;
    private boolean isGameOver;
    private Clip clip;



    public GamePanel() {
        this.allPanels = new CellPanel[20][10];
        this.setBounds(0, 0, Constants.GAME_PANEL_WIDTH, Constants.GAME_PANEL_HEIGHT);
        this.setBackground(new Color(98, 3, 3));
        this.setLayout(new GridLayout(20, 10));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.paintPanels();
        this.dropSpeed = 1000;
        this.startGame();
        this.addControls();
    }

    private void startGame(){
        Random random = new Random();
        this.shapeColor = random.nextInt(0, Constants.BLOCK_COLORS.length);
        this.nextShape = deepCopy(Constants.SHAPES[random.nextInt(Constants.SHAPES.length)]);
        new Thread( () -> {
            this.startTurn();
            this.dropDown();
            synchronized (lock){
                while(!isGameOver) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    this.startTurn();
                    lock.notify();
                }
                System.out.println("Got here thread");
            }
        }).start();
    }
    private void startTurn(){
        this.createShape();
        this.spawn();
    }
    private void addControls(){
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    dropSpeed = 100;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    dropSpeed = 1000;
                }else{
                    if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT ) {

                        playSound(Constants.MOVE_SOUND_EFFECT);
                        boolean success;
                        int direction;
                        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                            direction = -1;
                        } else {
                            direction = 1;
                        }
                        updatePosition(shape, true);
                        success = checkIfClearToMoveHorizontally(shape, direction);

                        if (success) {
                            makeMove(direction);
                        }
                        updatePosition(shape, false);
                    }

                    else if (e.getKeyCode() == KeyEvent.VK_UP) {
                        rotate();
                    }
                }

            }
        });
    }


    private void playSound(String filePath){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void rotate() {
        int[][] distancesFromCenter = new int[this.shape.length][2];
        int[][] indexesInGrid = new int[this.shape.length][2];
        int i = 0;

        this.updatePosition(this.shape, true);
        if(this.canRotate(distancesFromCenter,indexesInGrid)) {
            this.playSound(Constants.ROTATE_SOUND_EFFECT);
            for (Block block : this.shape){
                block.setDistanceFromCenter(distancesFromCenter[i][0], distancesFromCenter[i][1]);
                block.getIndex()[0] = indexesInGrid[i][0];
                block.getIndex()[1] = indexesInGrid[i][1];
                i++;
            }
        }
        this.updatePosition(this.shape, false);
    }

    private boolean canRotate(int[][] distancesFromCenter, int[][] indexesInGrid){
        int[] clockWiseRotationMatrix = {0, 1,
                                        -1, 0};
        Block center = null;
        for (Block block : this.shape) {
            if (block.isCenter()) {
                center = block;
                break;
            }
        }
        int i = 0;
        boolean canRotate = false;
        if(center != null) {
            for (Block block : this.shape) {
                distancesFromCenter[i] = block.multiplyRotationMatrix(clockWiseRotationMatrix);
                indexesInGrid[i][0] = center.getIndex()[0] + distancesFromCenter[i][0];
                indexesInGrid[i][1] = center.getIndex()[1] + distancesFromCenter[i][1];
                if (indexesInGrid[i][0] >= 20 || indexesInGrid[i][1] < 0
                     || indexesInGrid[i][1] >= 10 || indexesInGrid[i][0] < 0 ||
                        this.allPanels[indexesInGrid[i][0]][indexesInGrid[i][1]].getCurrentBlock() != null) {
                    canRotate = false;
                    break;
                }
                i++;
                if (i == this.shape.length) {
                    canRotate = true;

                }
            }
        }

        return canRotate;
    }

    private boolean checkIfClearToMoveHorizontally(Block[] shape, int direction) {
        boolean result = false;
        int[] newColumns = new int[shape.length];
        int i = 0;
        for (Block block : shape) {
            newColumns[i] = block.getIndex()[1] + direction;
            if (newColumns[i] < 0 || newColumns[i] >= 10 ||
                    this.allPanels[block.getIndex()[0]][block.getIndex()[1]+direction].getCurrentBlock() != null) {
                result = false;
                break;
            } else {
                result = true;
            }
            i++;
        }
        return result;
    }

    private void makeMove(int direction) {
        for (int j = 0; j < shape.length; j++) {
            shape[j].getIndex()[1] = shape[j].getIndex()[1] + direction;
        }
    }

    private void spawn() {
        for(Block block : this.shape) {
            if (allPanels[block.getIndex()[0]][block.getIndex()[1]].getCurrentBlock() != null) {
                this.isGameOver = true;
                //this.removeAll();
                //this.setBackground(Color.lightGray);
                // this.repaint();
                this.playSound(Constants.GAME_OVER_SOUND_EFFECT);
                System.out.println("Game over");
                break;
            }
        }
            if(isGameOver){ // Start new game, later I will write a separate function for this with
                this.playSound(Constants.CLEAN_UP_SOUND_EFFECT);
                for (int i = 0; i < allPanels.length; i++) {
                    for (int j = 0; j < allPanels[i].length; j++) {
                        allPanels[i][j].setCurrentBlock(null);
                        allPanels[i][j].setBackground(Color.WHITE);
                        allPanels[i][j].removeAll();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        allPanels[i][j].setBackground(new Color(4, 20, 30));
                    }
                }
                //this.repaint();
                try {
                    Thread.sleep(1000);
                    System.out.println("Starting...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                this.isGameOver = false;
            }
        this.updatePosition(this.shape, false);


        }


    private void createShape() {
        Random random = new Random();
            this.shape = deepCopy(this.nextShape);
            this.shapeColor = random.nextInt(0, Constants.BLOCK_COLORS.length);
            this.nextShape = deepCopy(Constants.SHAPES[random.nextInt(Constants.SHAPES.length)]);


        for (Block block : this.shape) {
            if (block.isCenter()) {
                this.generateDistanceFromCenter(block.getIndex());
                break;
            }
        }
        Window.nextShapePanel.addShape(this.nextShape);
        //Window.nextShapePanel.;

    }

    private Block[] deepCopy(Block[] arr){
        Block[] shape = new Block[arr.length];
        for (int i = 0; i < arr.length; i++) {
            shape[i] = new Block(arr[i],Constants.BLOCK_COLORS[this.shapeColor]);
        }
        return shape;
    }

    private void generateDistanceFromCenter(int[] index) {
        for (Block block : this.shape) {
            block.calculateDistanceFromCenter(index[0], index[1]);
        }
    }

    private void paintPanels() {
        for (int i = 0; i < allPanels.length; i++) {
            for (int j = 0; j < allPanels[i].length; j++) {
                allPanels[i][j] = new CellPanel();
                this.add(allPanels[i][j]);
//                if((i+j) % 2 == 0){
//                    allPanels[i][j].setBackground(new Color(0,0,99));
//                }else{
//                    allPanels[i][j].setBackground(new Color(0,99,0));
//                }
            }
        }
    }

    private void dropDown() {
        new Thread(() -> {
            synchronized (lock) {
                while (!isGameOver) {
                        try {
                            this.playSound(Constants.DROP_SOUND_EFFECT);
                            Thread.sleep(this.dropSpeed);
                            this.updatePosition(this.shape, true);
                            if (this.checkIfClearToMoveDown(this.shape)) {
                                this.moveDown(this.shape);
                                this.updatePosition(this.shape, false);
                            } else {
                                this.playSound(Constants.LAND_SOUND_EFFECT);
                                this.updatePosition(this.shape, false);
                                this.breakLines();
                                lock.notify();
                                try {
                                    lock.wait();
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
                lock.notify();
            }
        }).start();
    }

    private void breakLines() {
        boolean isFull;
        for (int i = 0; i < 20; i++) {
            isFull = false;
            for (int j = 0; j < 10; j++) {
                if (allPanels[i][j].getCurrentBlock() != null) {
                    if (j + 1 == 10) {
                        isFull = true;
                    }
                }else {
                    break;
                }
            }

            if (isFull) {
                this.playSound(Constants.LINE_BREAK_SOUND_EFFECT);
                for (int k = 0; k < 10; k++) {  //This is removing the full line
                    this.allPanels[i][k].removeAll();
                    this.allPanels[i][k].setCurrentBlock(null);
                    allPanels[i][k].setBackground(Color.WHITE);
                }
                try {
                    Thread.sleep(100);
                }catch (Exception e){
                    e.printStackTrace();
                }
                //this.repaint();
                for (int l = i; l >= 0; l--) { //this is updating blocks that are above the full line
                    for (int k = 0; k < 10; k++) {
                        if(l == i){
                            allPanels[i][k].setBackground(new Color(4, 20, 30));
                        }else {
                            allPanels[l][k].removeAll();
                            if (allPanels[l][k].getCurrentBlock() != null) {
                                allPanels[l][k].getCurrentBlock().setRow(this.allPanels[l][k].getCurrentBlock().getIndex()[0] + 1);
                                allPanels[l + 1][k].add(allPanels[l][k].getCurrentBlock());
                                allPanels[l + 1][k].setCurrentBlock(allPanels[l][k].getCurrentBlock());
                                allPanels[l][k].setCurrentBlock(null);
                            }
                        }
                    }
                }
                this.repaint();
            }
        }
    }


    private boolean checkIfClearToMoveDown(Block[] shape){
        boolean res = true;

        for (Block block : shape){
            if(block.getIndex()[0] >= 19 || allPanels[block.getIndex()[0] + 1][block.getIndex()[1]].getCurrentBlock() != null){
                res = false;
                break;
            }
        }
        return res;
    }

    private void moveDown(Block[] shape) {
        for (Block block : shape) {
            block.getIndex()[0]++;
        }
    }

    private void updatePosition(Block[] shape, boolean toRemove) {
            for (Block block : shape) {
                if(toRemove) {
                    this.allPanels[block.getIndex()[0]][block.getIndex()[1]].remove(block);
                    this.allPanels[block.getIndex()[0]][block.getIndex()[1]].setCurrentBlock(null);
                }else{
                    //this.allPanels[block.getIndex()[0]][block.getIndex()[1]].removeAll();
                    this.allPanels[block.getIndex()[0]][block.getIndex()[1]].add(block);
                    this.allPanels[block.getIndex()[0]][block.getIndex()[1]].setCurrentBlock(block);
                }
            }
            this.repaint();
    }

}