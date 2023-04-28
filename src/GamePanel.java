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
    private volatile boolean isPaused;
    private volatile boolean keyIsPressed;
    private volatile boolean keyCanBePressed;



    public GamePanel() {
        this.allPanels = new CellPanel[Constants.ROWS_COUNT][Constants.COLUMNS_COUNT];
        this.setBounds(0, 0, Constants.GAME_PANEL_WIDTH, Constants.GAME_PANEL_HEIGHT);
        this.setLayout(new GridLayout(Constants.ROWS_COUNT, Constants.COLUMNS_COUNT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.paintPanels();
        this.dropSpeed = Constants.REGULAR_DROP_SPEED;
        this.startGame();
        this.addControls();

    }

    private void startGame(){
        Random random = new Random();
        this.shapeColor = random.nextInt(Constants.BLOCK_COLORS.length);
        this.nextShape = deepCopy(Constants.SHAPES[random.nextInt(Constants.SHAPES.length)]);
        new Thread( () -> {
            this.startTurn();
            this.dropDown();
            synchronized (lock){
                while(!isGameOver) {
                    if (!isPaused) {
                        try {
                            lock.wait();
                            this.startTurn();
                            lock.notify();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }).start();
    }

    private void dropDown() {
        new Thread(() -> {
            synchronized (lock) {
                while (!isGameOver) {
                    this.sleepFor(this.dropSpeed);
                    if (!isPaused) {
                        try {
                            this.playSound(Constants.DROP_SOUND_EFFECT);
                            while (keyIsPressed) {
                                Thread.onSpinWait();
                            }
                            this.keyCanBePressed = false;
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
                            this.keyCanBePressed = true;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
                lock.notify();
            }
        }).start();
    }

    private void sleepFor(int num){
        try {
            Thread.sleep(num);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
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
                    dropSpeed = Constants.FAST_DROP_SPEED;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!isGameOver) {
                    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                        dropSpeed = Constants.REGULAR_DROP_SPEED;
                    }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
                        isPaused = !isPaused;
                    }else {
                        if (!isPaused) {
                            while (!keyCanBePressed) {
                                Thread.onSpinWait();
                            }
                            if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                keyIsPressed = true;

                                playSound(Constants.MOVE_SOUND_EFFECT);
                                boolean success;
                                int direction;
                                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                                    direction = Constants.LEFT_DIRECTION;
                                } else {
                                    direction = Constants.RIGHT_DIRECTION;
                                }
                                updatePosition(shape, true);
                                success = checkIfClearToMoveHorizontally(shape, direction);

                                if (success) {
                                    makeMove(direction);
                                }
                                updatePosition(shape, false);
                                keyIsPressed = false;
                            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                                keyIsPressed = true;
                                rotate();
                                keyIsPressed = false;
                            }
                        }
                    }
                }

            }
        });
    }


    private void playSound(String filePath){
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            Clip clip = AudioSystem.getClip();
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
                block.setDistanceFromCenter(distancesFromCenter[i][Constants.ROW_INDEX], distancesFromCenter[i][Constants.COLUMN_INDEX]);
                block.getIndex()[Constants.ROW_INDEX] = indexesInGrid[i][Constants.ROW_INDEX];
                block.getIndex()[Constants.COLUMN_INDEX] = indexesInGrid[i][Constants.COLUMN_INDEX];
                System.out.println(Arrays.toString(block.getIndex()));
                i++;
            }
        }
        this.updatePosition(this.shape, false);
    }

    private boolean canRotate(int[][] distancesFromCenter, int[][] indexesInGrid){
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
                distancesFromCenter[i] = block.multiplyRotationMatrix(Constants.CLOCK_WISE_ROTATION_MATRIX);
                indexesInGrid[i][Constants.ROW_INDEX] = center.getIndex()[Constants.ROW_INDEX] + distancesFromCenter[i][Constants.ROW_INDEX];
                indexesInGrid[i][Constants.COLUMN_INDEX] = center.getIndex()[Constants.COLUMN_INDEX] + distancesFromCenter[i][Constants.COLUMN_INDEX];
                if (indexesInGrid[i][Constants.ROW_INDEX] >= Constants.ROWS_COUNT || indexesInGrid[i][Constants.COLUMN_INDEX] < 0
                     || indexesInGrid[i][Constants.COLUMN_INDEX] >= Constants.COLUMNS_COUNT || indexesInGrid[i][Constants.ROW_INDEX] < 0 ||
                        this.allPanels[indexesInGrid[i][Constants.ROW_INDEX]][indexesInGrid[i][Constants.COLUMN_INDEX]].getCurrentBlock() != null) {
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
            newColumns[i] = block.getIndex()[Constants.COLUMN_INDEX] + direction;
            if (newColumns[i] < 0 || newColumns[i] >= Constants.COLUMNS_COUNT ||
                    this.allPanels[block.getIndex()[Constants.ROW_INDEX]][block.getIndex()[Constants.COLUMN_INDEX]+direction].getCurrentBlock() != null) {
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
            shape[j].getIndex()[Constants.COLUMN_INDEX] += direction;
        }
    }

    private void spawn() {
        for(Block block : this.shape) {
            if (allPanels[block.getIndex()[Constants.ROW_INDEX]][block.getIndex()[Constants.COLUMN_INDEX]].getCurrentBlock() != null) {
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
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        allPanels[i][j].setBackground(Constants.PANEL_BACKGROUND_COLOR);
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
//                }
            }
        }
    }


    private void breakLines() {
        boolean isFull;
        for (int i = 0; i < Constants.ROWS_COUNT; i++) {
            isFull = false;
            for (int j = 0; j < Constants.COLUMNS_COUNT; j++) {
                if (allPanels[i][j].getCurrentBlock() != null) {
                    if (j + 1 == Constants.COLUMNS_COUNT) {
                        isFull = true;
                    }
                }else {
                    break;
                }
            }

            if (isFull) {
                this.playSound(Constants.LINE_BREAK_SOUND_EFFECT);
                for (int k = 0; k < Constants.COLUMNS_COUNT; k++) {  //This is removing the full line
                    this.allPanels[i][k].removeAll();
                    this.allPanels[i][k].setCurrentBlock(null);
                    allPanels[i][k].setBackground(Color.WHITE);
                }
                this.sleepFor(Constants.REMOVE_ANIMATION_DELAY);
                //this.repaint();
                for (int l = i; l >= 0; l--) { //this is updating blocks that are above the full line
                    for (int k = 0; k < Constants.COLUMNS_COUNT; k++) {
                        if(l == i){
                            allPanels[i][k].setBackground(Constants.PANEL_BACKGROUND_COLOR);
                        }else {
                            allPanels[l][k].removeAll();
                            if (allPanels[l][k].getCurrentBlock() != null) {
                                allPanels[l][k].getCurrentBlock().setRow(this.allPanels[l][k].getCurrentBlock().getIndex()[Constants.ROW_INDEX] + 1);
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
            if(block.getIndex()[Constants.ROW_INDEX] >= Constants.ROWS_COUNT-1 || allPanels[block.getIndex()[Constants.ROW_INDEX] + 1][block.getIndex()[Constants.COLUMN_INDEX]].getCurrentBlock() != null){
                res = false;
                break;
            }
        }
        return res;
    }

    private void moveDown(Block[] shape) {
        for (Block block : shape) {
            block.getIndex()[Constants.ROW_INDEX]++;
        }
    }

    private void updatePosition(Block[] shape, boolean toRemove) {
            for (Block block : shape) {
                if(toRemove) {
                    this.allPanels[block.getIndex()[Constants.ROW_INDEX]][block.getIndex()[Constants.COLUMN_INDEX]].remove(block);
                    this.allPanels[block.getIndex()[Constants.ROW_INDEX]][block.getIndex()[Constants.COLUMN_INDEX]].setCurrentBlock(null);
                }else{
                    this.allPanels[block.getIndex()[Constants.ROW_INDEX]][block.getIndex()[Constants.COLUMN_INDEX]].add(block);
                    this.allPanels[block.getIndex()[Constants.ROW_INDEX]][block.getIndex()[Constants.COLUMN_INDEX]].setCurrentBlock(block);
                }
            }
            this.repaint();
    }

}