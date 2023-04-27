import javax.swing.*;
import java.awt.*;

public class NextShapePanel extends JPanel {
    private JPanel[][] allPanels;
    public NextShapePanel(){
        this.setLayout(new GridLayout(3,10));
        this.setBounds(Constants.GAME_PANEL_WIDTH + 50, Constants.MARGIN_TOP,240,80);
        this.setOpaque(false);

       // this.setBackground(new Color(22,33,222));
        this.allPanels = new JPanel[3][10];

        for (int i = 0; i < allPanels.length; i++) {
            for (int j = 0; j < allPanels[i].length; j++) {
                allPanels[i][j] = new JPanel();
                allPanels[i][j].setLayout(null);
                allPanels[i][j].setOpaque(false);
//                if((i+j) % 2 == 0){
//                    allPanels[i][j].setBackground(Color.GREEN);
//                }else{
//                    allPanels[i][j].setBackground(Color.RED);
//                }
                //allPanels[i][j].setBackground(Color.BLUE);
                this.add(allPanels[i][j]);
            }
        }
    }

    public void addShape(Block[] shape){
        this.removeShape();
        for (Block block: shape) {
            this.allPanels[block.getIndex()[0]][block.getIndex()[1]].add(block);
        }
        this.repaint();
    }

    public void removeShape(){
        for (int i = 0; i < allPanels.length; i++) {
            for (int j = 0; j < allPanels[i].length; j++) {
                allPanels[i][j].removeAll();
            }
        }
        this.repaint();
    }

}
