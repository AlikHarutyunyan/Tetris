import javax.swing.*;
import java.awt.*;

public class NextShapePanel extends JPanel {
    private JPanel[][] allPanels;
    public NextShapePanel(){

        this.setLayout(new GridLayout(Constants.NEXT_SHAPE_PANEL_ROWS_COUNT,Constants.COLUMNS_COUNT));
        this.setBounds(Constants.NEXT_SHAPE_PANEL_X, Constants.NEXT_PANEL_MARGIN_TOP,Constants.NEXT_PANEL_WIDTH,Constants.NEXT_PANEL_HEIGHT);

        this.setBackground(Constants.PANEL_BACKGROUND_COLOR);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE,Constants.NEXT_SHAPE_PANEL_BORDER_SIZE));
        this.allPanels = new JPanel[Constants.NEXT_SHAPE_PANEL_ROWS_COUNT][Constants.COLUMNS_COUNT];

        for (int i = 0; i < allPanels.length; i++) {
            for (int j = 0; j < allPanels[i].length; j++) {
                allPanels[i][j] = new JPanel();
                allPanels[i][j].setLayout(null);
                allPanels[i][j].setOpaque(false);
                this.add(allPanels[i][j]);
            }
        }
    }

    public void addShape(Block[] shape){
        this.removeShape();
        for (Block block: shape) {
            this.allPanels[block.getIndex()[Constants.ROW_INDEX]][block.getIndex()[Constants.COLUMN_INDEX]].add(block);
        }
        this.repaint();
    }

    public void removeShape(){
        for (JPanel[] allPanel : allPanels) {
            for (JPanel jPanel : allPanel) {
                jPanel.removeAll();
            }
        }
        this.repaint();
    }

}
