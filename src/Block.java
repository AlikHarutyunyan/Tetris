import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class Block extends JPanel {


    private int[] index;
    private Color color;

    private boolean isCenter;

    private int[] distanceFromCenter;
    public Block(int i, int j, Color color, boolean isCenter){
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setBounds(0,0,29,29);
        this.color = color;
        this.index = new int[]{i,j};
        this.isCenter = isCenter;
    }

    public int[] getIndex() {
        return index;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
            g.setColor(this.color);
            g.fillRect(0, 0, 29, 29);

    }

    public void calculateDistanceFromCenter(int centerRowIndex, int centerColumnIndex){
        this.distanceFromCenter = new int[]{this.index[0] - centerRowIndex, this.index[1] - centerColumnIndex };
        System.out.println(Arrays.toString(this.distanceFromCenter));
    }

    public int[] multiplyRotationMatrix(int[] rotationMatrix) {
        int newRowDistance = rotationMatrix[0] * this.distanceFromCenter[0] + rotationMatrix[1] * this.distanceFromCenter[1];
        int newColumnDistance = rotationMatrix[2] * this.distanceFromCenter[0] + rotationMatrix[3] * this.distanceFromCenter[1];
        this.distanceFromCenter[0] = newRowDistance;
        this.distanceFromCenter[1] = newColumnDistance;
        return new int[]{newRowDistance,newColumnDistance};
    }
}
