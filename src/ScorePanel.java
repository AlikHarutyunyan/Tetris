import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ScorePanel extends JPanel {
    private JLabel scoreCountLabel;
    private JLabel highScoreCountLabel;

    public ScorePanel(){
        this.setBounds(Constants.NEXT_SHAPE_PANEL_X,Constants.NEXT_PANEL_MARGIN_TOP+Constants.NEXT_PANEL_HEIGHT+Constants.MARGIN_TOP,Constants.NEXT_PANEL_WIDTH,Constants.SCORE_PANEL_HEIGHT);
        this.setBorder(BorderFactory.createLineBorder(Color.WHITE,Constants.SCORE_PANEL_BORDER_SIZE));
        this.setBackground(Constants.PANEL_BACKGROUND_COLOR);
        this.setLayout(new GridLayout(Constants.SCORE_PANEL_ROWS_COUNT,Constants.SCORE_PANEL_COLUMN_COUNT));

        JLabel scoreLabel = new JLabel("SCORE");

        scoreCountLabel = new JLabel("0");
        JLabel highScoreLabel = new JLabel("HIGH-SCORE");

        File highScoreFile = new File("files/highScore.txt");
        if(!highScoreFile.exists()) {
            highScoreCountLabel = new JLabel("0");
        }else{
            try {
                BufferedReader reader = new BufferedReader(new FileReader(highScoreFile));
                String score = reader.readLine().trim();
                highScoreCountLabel = new JLabel(score);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        addJLabel(scoreLabel);
        addJLabel(scoreCountLabel);
        scoreCountLabel.setBorder(BorderFactory.createLineBorder(Color.white,Constants.SCORE_PANEL_LINE_BORDER_SIZE));
        addJLabel(highScoreLabel);
        addJLabel(highScoreCountLabel);
        highScoreCountLabel.setBorder(BorderFactory.createLineBorder(Color.white,Constants.SCORE_PANEL_LINE_BORDER_SIZE));
    }

    public void updateScore(int quantity){
        try {
            int currentScore = Integer.parseInt(this.scoreCountLabel.getText().trim());
            int newScore = currentScore + quantity;
            this.scoreCountLabel.setText(newScore+"");
        }catch (Exception e){
            System.out.println("Unexpected Error!");
            e.printStackTrace();
        }
    }

    public void updateHighScore(int score){
        this.highScoreCountLabel.setText(score+"");
    }

    public void clearScore(){
        this.scoreCountLabel.setText("0");
    }

    public int getScore(){
        return Integer.parseInt(this.scoreCountLabel.getText().trim());
    }

    private void addJLabel(JLabel label){
        Font font = this.getFont();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setForeground(Color.white);
        label.setFont(new Font(font.getName(),font.getStyle(),Constants.SCORE_TEXT_SIZE));
        this.add(label);
    }

}
