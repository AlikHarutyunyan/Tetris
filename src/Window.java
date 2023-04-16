import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window(){
        this.setLayout(null);
        this.setTitle("Tetris");
        this.setSize(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(new Color(34, 34, 34));
        this.add(new GamePanel());
        this.setVisible(true);
    }
}
