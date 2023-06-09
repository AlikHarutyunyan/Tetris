import java.awt.*;

public class Constants {
    public static final int WINDOW_WIDTH = 650;
    public static final int WINDOW_HEIGHT = 700;
    public static final int MARGIN_TOP = 10;
    public static final int NEXT_PANEL_MARGIN_TOP = 50;
    public static final int NEXT_PANEL_WIDTH = 240;
    public static final int NEXT_SHAPE_LABEL_HORIZONTAL_SIZE = 40;
    public static final int NEXT_SHAPE_LABEL_VERTICAL_SIZE = 6;
    public static final int NEXT_SHAPE_LABEL_PADDING = 10;
    public static final int NEXT_SHAPE_LABEL_HEIGHT = 45;
    public static final int FONT_SIZE = 22;
    public static final int NEXT_PANEL_HEIGHT = 80;
    public static final int GAME_PANEL_HEIGHT = 600;
    public static final int GAME_PANEL_WIDTH = 300;
    public static final int MARGIN_LEFT = 10;
    public static final int GAME_OVER_ANIMATION_DELAY = 40;
    public static final Object[] GAME_OVER_OPTIONS = {"New Game", "Quit Game"};
    public static final int QUIT_GAME_OPTION = 1;
    public static final int NEW_GAME_OPTION = 0;
    public static final int NEXT_SHAPE_PANEL_X = GAME_PANEL_WIDTH + 50;
    public static final int ROWS_COUNT = 20;
    public static final int NEXT_SHAPE_PANEL_ROWS_COUNT = 3;
    public static final int NEXT_SHAPE_PANEL_BORDER_SIZE = 6;
    public static final int COLUMNS_COUNT = 10;
    public static final int REGULAR_DROP_SPEED = 1000;
    public static final int NEW_GAME_START_DELAY = 1000;
    public static final int FAST_DROP_SPEED = 100;
    public static final int LEFT_DIRECTION = -1;
    public static final int RIGHT_DIRECTION = 1;
    public static final int ROW_INDEX = 0;
    public static final int COLUMN_INDEX = 1;
    public static final int REMOVE_ANIMATION_DELAY = 100;
    public static final int BLOCK_SIZE = 30;
    public static final int PAUSE_PANEL_SIZE = 1;
    public static final int PAUSE_TEXT_SIZE = 30;
    public static final int SCORE_TEXT_SIZE = 22;
    public static final int SCORE_PANEL_ROWS_COUNT = 4;
    public static final int SCORE_PANEL_COLUMN_COUNT = 1;
    public static final int SCORE_PANEL_BORDER_SIZE = 6;
    public static final int SCORE_PANEL_LINE_BORDER_SIZE = 1;
    public static final int SCORE_PANEL_HEIGHT = 300;

    public static final int MENU_BUTTON_WIDTH = 400;
    public static final int MENU_BUTTON_HEIGHT = 150;
    public static final int MENU_BUTTON_FONT_SIZE = 40;
    public static final int HOW_TO_PLAY_WINDOW_WIDTH = 500;
    public static final int HOW_TO_PLAY_WINDOW_HEIGHT = 360;
    public static final int FAST_SPEED_SCORE_BONUS = 1;
    public static final int LINE_BREAK_SCORE_BONUS = 100;
    public static final int GAME_OVER_BUTTON_BORDER_SIZE = 2;
    public static final int[] CLOCK_WISE_ROTATION_MATRIX = {0, 1,
                                                           -1, 0};


    public static String MOVE_SOUND_EFFECT = "soundEffects/move.wav";
    public static String DROP_SOUND_EFFECT = "soundEffects/softDrop.wav";
    public static String ROTATE_SOUND_EFFECT = "soundEffects/rotate.wav";
    public static String LINE_BREAK_SOUND_EFFECT = "soundEffects/lineBreak.wav";
    public static String GAME_OVER_SOUND_EFFECT = "soundEffects/gameOver.wav";
    public static String LAND_SOUND_EFFECT = "soundEffects/land.wav";
    public static String CLEAN_UP_SOUND_EFFECT = "soundEffects/cleanUp.wav";

    public static Color PANEL_BACKGROUND_COLOR = new Color(4, 20, 30);
    public static Color MAIN_BACKGROUND_COLOR = new Color(34, 34, 34);

    public static final Color[] BLOCK_COLORS = {
            new Color(0xBB0606),
            new Color(0x0ABB0A),
            new Color(0x0404C0),
            new Color(0xC07D02),
            new Color(0x671EA9),
            new Color(0xB9B902),
            new Color(0x01AFAF)
    };

    public static final Block[][] SHAPES = new Block[][]{
            new Block[]{
                    new Block(0, 4,  false),
                    new Block(1, 4,  false),
                    new Block(1, 5,  true),
                    new Block(1, 6,  false),
            },
            new Block[]{
                    new Block(0, 6,  false),
                    new Block(1, 4,  false),
                    new Block(1, 5,  true),
                    new Block(1, 6,  false),
            },
            new Block[]{
                    new Block(0, 5,  false),
                    new Block(0, 4,  false),
                    new Block(1, 5,  false),
                    new Block(1, 4,  false),
            },
            new Block[]{
                    new Block(0, 3,  false),
                    new Block(0, 4,  false),
                    new Block(0, 5,  true),
                    new Block(0, 6,  false),
            },
            new Block[]{
                    new Block(0, 5,  false),
                    new Block(0, 6,  false),
                    new Block(1, 5,  true),
                    new Block(1, 4,  false),
            },
            new Block[]{
                    new Block(0, 5,  false),
                    new Block(0, 4,  false),
                    new Block(1, 5,  true),
                    new Block(1, 6,  false),
            },
            new Block[]{
                    new Block(0, 5,  false),
                    new Block(1, 4,  false),
                    new Block(1, 5,  true),
                    new Block(1, 6,  false),
            },
    };
}
