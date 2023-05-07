import javax.swing.*;

public class CellPanel extends JPanel {

    private Block currentBlock;
    public CellPanel(){
        this.setLayout(null);
        this.setBackground(Constants.PANEL_BACKGROUND_COLOR);
    }

    public Block getCurrentBlock() {
        return currentBlock;
    }

    public void setCurrentBlock(Block currentBlock) {
        this.currentBlock = currentBlock;
    }


}
