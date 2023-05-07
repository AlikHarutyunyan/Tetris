import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    public static NextShapePanel nextShapePanel;
    public static ScorePanel scorePanel;
    public static PausePanel pausePanel;

    public Window(){
        this.setLayout(null);
        this.setTitle("Tetris");
        this.setSize(Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Constants.MAIN_BACKGROUND_COLOR);

        nextShapePanel = new NextShapePanel();
        JLabel nextShapeLabel = new JLabel("NEXT SHAPE:");
        Font font = nextShapeLabel.getFont();
        int newSize = Constants.FONT_SIZE;
        nextShapeLabel.setFont(new Font(font.getName(), font.getStyle(), newSize));
        nextShapeLabel.setForeground(Constants.PANEL_BACKGROUND_COLOR);
        nextShapeLabel.setOpaque(true);
        nextShapeLabel.setBackground(Color.WHITE);
        nextShapeLabel.setBorder(BorderFactory.createMatteBorder(Constants.NEXT_SHAPE_LABEL_VERTICAL_SIZE, Constants.NEXT_SHAPE_LABEL_HORIZONTAL_SIZE, Constants.NEXT_SHAPE_LABEL_VERTICAL_SIZE, Constants.NEXT_SHAPE_LABEL_HORIZONTAL_SIZE, Color.WHITE));
        nextShapeLabel.setBounds(Constants.NEXT_SHAPE_PANEL_X,Constants.MARGIN_TOP,nextShapeLabel.getPreferredSize().width+Constants.NEXT_SHAPE_LABEL_PADDING,Constants.NEXT_SHAPE_LABEL_HEIGHT);
        nextShapeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        pausePanel = new PausePanel();

        this.add(pausePanel);
        this.add(nextShapeLabel);
        this.add(nextShapePanel);

        scorePanel = new ScorePanel();
        this.add(scorePanel);

        GamePanel GAME_PANEL = new GamePanel();
        this.add(GAME_PANEL);

        JLabel backgroundLabel = new JLabel(new ImageIcon("Images/background.jpg"));

        backgroundLabel.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        this.getContentPane().add(backgroundLabel);

        this.setVisible(true);
    }
}

