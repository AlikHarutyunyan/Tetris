import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final GamePanel GAME_PANEL;
    public static NextShapePanel nextShapePanel;

    public Window(){
        this.setLayout(null);
        this.setTitle("Tetris");
        this.setSize(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Constants.MAIN_BACKGROUND_COLOR);
        nextShapePanel = new NextShapePanel();
        GAME_PANEL = new GamePanel();
        this.add(nextShapePanel);
        this.add(GAME_PANEL);
        this.setVisible(true);
    }
}
