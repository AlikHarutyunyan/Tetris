import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Menu extends JFrame {
    private HowToPlay howToPlayWindow;
    public Menu(){
        this.setBounds(0,0,Constants.WINDOW_WIDTH,Constants.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Tetris");
        JLabel backgroundLabel = new JLabel(new ImageIcon("Images/background.jpg"));

        backgroundLabel.setBounds(0, 0, Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);

        JButton playButton = new JButton("PLAY");
        playButton.setBounds((Constants.WINDOW_WIDTH-Constants.MENU_BUTTON_WIDTH)/2,(Constants.WINDOW_HEIGHT/2-Constants.MENU_BUTTON_HEIGHT)-Constants.MARGIN_TOP,Constants.MENU_BUTTON_WIDTH,Constants.MENU_BUTTON_HEIGHT);
        this.addStyleToButton(playButton);
        this.add(playButton);

        JButton howToPlayButton = new JButton("HOW TO PLAY");
        howToPlayButton.setBounds(Constants.WINDOW_WIDTH/2-Constants.MENU_BUTTON_WIDTH/2,Constants.WINDOW_HEIGHT/2,Constants.MENU_BUTTON_WIDTH,Constants.MENU_BUTTON_HEIGHT);
        this.addStyleToButton(howToPlayButton);
        this.add(howToPlayButton);

        this.getContentPane().add(backgroundLabel);


        playButton.addActionListener((a) -> {
            new Window();
            this.dispose();
        });

        howToPlayButton.addActionListener((a) -> {
            if(howToPlayWindow == null || !howToPlayWindow.isVisible()){
                howToPlayWindow = new HowToPlay();
            }
        });

        this.setVisible(true);
    }

    private void addStyleToButton(JButton button){
        button.setBorder(null);
        button.setFocusable(false);
        button.setBackground(Constants.PANEL_BACKGROUND_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, Constants.MENU_BUTTON_FONT_SIZE));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                button.setBackground(Constants.PANEL_BACKGROUND_COLOR.brighter());
            }
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                button.setBackground(Constants.PANEL_BACKGROUND_COLOR);
            }
        });
    }

}
