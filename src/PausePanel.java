import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {

    public PausePanel(){
        this.setBounds(0,0,Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
        this.setBackground(new Color(0xDB0C0C17, true));
        this.setLayout(new GridLayout(Constants.PAUSE_PANEL_SIZE,Constants.PAUSE_PANEL_SIZE));

        JLabel label = new JLabel("PAUSED");
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, Constants.PAUSE_TEXT_SIZE));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.WHITE);
        this.add(label);
        this.setVisible(false);
    }
}
