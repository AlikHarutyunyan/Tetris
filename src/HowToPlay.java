import javax.swing.*;
public class HowToPlay extends JFrame {
    public HowToPlay(){
        this.setBounds(0,0,Constants.HOW_TO_PLAY_WINDOW_WIDTH,Constants.HOW_TO_PLAY_WINDOW_HEIGHT);
        this.setTitle("How to play");
        this.setResizable(false);
        this.getContentPane().setBackground(Constants.PANEL_BACKGROUND_COLOR);

        String longText = "<html> " +
                "<div style=\" color:white; text-align:center \"> " +
               "<p style=\"font-size:23px\"> HOW TO PLAY </p>" +
                "<p> UP ARROW : ROTATE THE SHAPE </p>" +
                "<p> LEFT/RIGHT ARROWS : MOVE THE SHAPE ACCORDINGLY </p>" +
                "<p> DOWN ARROW : HOLD IT TO MOVE FASTER</p>" +
                "<p> ESC : PAUSE/RESUME THE GAME </p>" +
                "<p style=\"font-size:23px\"> YOUR GOAL </p>" +
                "<p> TRY BREAKING THE HIGH-SCORE BY GETTING POINTS FOR FILLING THE LINES AND MOVING FAST </p>" +
                "</html>";

        JEditorPane myEditorPane = new JEditorPane();
        myEditorPane.setEditable(false);
        myEditorPane.setBackground(Constants.PANEL_BACKGROUND_COLOR);
        myEditorPane.setContentType("text/html");
        myEditorPane.setText(longText);
        this.getContentPane().add(myEditorPane);
        this.setVisible(true);
    }
}
