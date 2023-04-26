import javax.swing.*;
import java.awt.*;

public class CellPanel extends JPanel {

    private Block currentBlock;
    public CellPanel(){
        this.setLayout(null);
        this.setBackground(new Color(4, 20, 30));
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock = currentBlock;
    }

    public void removeBlock(){

    }


//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        if(!this.isOccupied) {
//            g.setColor(new Color(73, 22, 11));
//            g.fillRect(0, 0, 50, 50);
//        }
//    }
}
