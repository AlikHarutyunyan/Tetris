import javax.swing.*;
import java.awt.*;

public class Block extends JPanel {


    private int[] index;
    private Color color;

    private boolean isCenter;

    private int[] distanceFromCenter;
    public Block(int i, int j,  boolean isCenter){
        this.index = new int[]{i,j};
        this.isCenter = isCenter;
    }
    public Block(Block other, Color color) {

        this.setBounds(0,0,Constants.BLOCK_SIZE,Constants.BLOCK_SIZE);
        this.color = color;
        this.setOpaque(false);
        this.isCenter = other.isCenter;

        this.index = new int[other.index.length];
        for (int i = 0; i < other.index.length; i++) {
            this.index[i] = other.index[i];
        }
        this.distanceFromCenter = new int[2];

    }


    public int[] getIndex() {
        return index;
    }

    public boolean isCenter() {
        return isCenter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.clearRect(0,0,getWidth(),getHeight());
        g.setColor(this.color);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        g.setColor(this.color.brighter());
        g.drawRect(0,0,getWidth(),getHeight());
    }

    public void calculateDistanceFromCenter(int centerRowIndex, int centerColumnIndex){
        this.distanceFromCenter = new int[]{this.index[Constants.ROW_INDEX] - centerRowIndex, this.index[Constants.COLUMN_INDEX] - centerColumnIndex };
    }

    public int[] multiplyRotationMatrix(int[] rotationMatrix) {
        int newRowDistance = rotationMatrix[0] * this.distanceFromCenter[Constants.ROW_INDEX] + rotationMatrix[1] * this.distanceFromCenter[Constants.COLUMN_INDEX];
        int newColumnDistance = rotationMatrix[2] * this.distanceFromCenter[Constants.ROW_INDEX] + rotationMatrix[3] * this.distanceFromCenter[Constants.COLUMN_INDEX];
        return new int[]{newRowDistance,newColumnDistance};
    }

    public void setDistanceFromCenter(int newRowDistance, int newColumnDistance) {
        this.distanceFromCenter[Constants.ROW_INDEX] = newRowDistance;
        this.distanceFromCenter[Constants.COLUMN_INDEX] = newColumnDistance;
    }

    public void setRow(int row){
        this.index[Constants.ROW_INDEX] = row;
    }


}
