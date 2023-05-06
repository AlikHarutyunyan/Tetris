import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {

    public PausePanel(){
        this.setBounds(0,0,Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        JLabel label = new JLabel("PAUSED");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds(Constants.GAME_PANEL_WIDTH/2,Constants.GAME_PANEL_HEIGHT/2,100,100);
        label.setForeground(Color.WHITE);
        this.add(label);
        this.setVisible(false);
    }
}
