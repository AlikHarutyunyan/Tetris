import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class GamePanel extends JPanel {


    private static final Object lock = new Object();
    private CellPanel[][] allPanels;
    private Block[] shape;


    public GamePanel() {
        this.allPanels = new CellPanel[20][10];
        this.setBounds(0, Constants.MARGIN_TOP, 300, Constants.GAME_PANEL_HEIGHT);
        this.setBackground(new Color(98, 3, 3));
        this.setLayout(new GridLayout(20, 10));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.paintPanels();



        new Thread( () -> {
            this.createShape();
            this.spawn();
            this.dropDown(this.shape);
            synchronized (lock){
            while(true) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                this.createShape();
                this.spawn();
                this.dropDown(this.shape);
            }
            }
        }).start();


        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    boolean success = false;
                    int direction;
                    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                        direction = -1;
                    } else {
                        direction = 1;
                    }
                    success = checkIfClearToMoveHorizontally(shape, direction);

                    if (success) {
                        updatePosition(shape, true);
                        makeMove(direction);
                        updatePosition(shape, false);

                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    rotate();
                }
            }
        });


//       this.dropDown(new Block[]{new Block(0,4, Color.RED), new Block(1,4,Color.RED),new Block(2,4,Color.RED)});
    }

    private void rotate() {
        int[] clockWiseRotationMatrix =
                {0, 1,
                -1, 0};
        Block center = null;
        for (Block block : this.shape) {
            if (block.isCenter()) {
                center = block;
                break;
            }
        }
        this.updatePosition(this.shape,true);
        for (Block block:this.shape) {
            int[] indexes = block.multiplyRotationMatrix(clockWiseRotationMatrix);
            block.getIndex()[0] = center.getIndex()[0] + indexes[0];
            block.getIndex()[1] = center.getIndex()[1] + indexes[1];
        }
        this.updatePosition(this.shape,false);
    }

    private boolean checkIfClearToMoveHorizontally(Block[] shape, int direction) {
        boolean result = false;
        int[] newColumns = new int[shape.length];
        int i = 0;
        for (Block block : shape) {
            newColumns[i] = block.getIndex()[1] + direction;
            if (newColumns[i] >= 0 && newColumns[i] < 10) {
                result = true;
            } else {
                result = false;
                break;
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
        this.updatePosition(this.shape, false);
    }

    private void createShape() {
        Random randomColor = new Random();
        int colorIndex = randomColor.nextInt(0, Constants.BLOCK_COLORS.length);
        this.shape = new Block[]{
                new Block(0, 5, Constants.BLOCK_COLORS[colorIndex], false),
                new Block(0, 6, Constants.BLOCK_COLORS[colorIndex], false),
                new Block(1, 5, Constants.BLOCK_COLORS[colorIndex], true),
                new Block(2, 5, Constants.BLOCK_COLORS[colorIndex], false),
        };

        for (Block block : this.shape) {
            if (block.isCenter()) {
                this.generateDistanceFromCenter(block.getIndex());
                break;
            }
        }

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

    private void dropDown(Block[] shape) {
        new Thread(() -> {
            synchronized (lock) {
                while (true) {
                    try {
                        if (this.checkIfClearToMoveDown(shape)) {

                            this.updatePosition(shape, true);
                            this.moveDown(shape);
                            this.updatePosition(shape, false);

                            Thread.sleep(1000);
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                lock.notify();
            }
        }).start();
    }

    private boolean checkIfClearToMoveDown(Block[] shape){
        boolean res = true;
        for (Block block : shape){
            if(block.getIndex()[0] >= 19){
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
        if (toRemove) {
            for (Block block : shape) {
                this.allPanels[block.getIndex()[0]][block.getIndex()[1]].remove(block);
                this.allPanels[block.getIndex()[0]][block.getIndex()[1]].repaint();
                this.allPanels[block.getIndex()[0]][block.getIndex()[1]].setOccupied(false);
            }
        } else {
            for (Block block : shape) {
                this.allPanels[block.getIndex()[0]][block.getIndex()[1]].add(block);
                this.allPanels[block.getIndex()[0]][block.getIndex()[1]].repaint();
                this.allPanels[block.getIndex()[0]][block.getIndex()[1]].setOccupied(true);
            }
        }

        //block.getIndex()[0]++;
    }

}
