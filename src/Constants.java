import java.awt.*;

public class Constants {
    public static final int WINDOW_WIDTH = 680;
    public static final int WINDOW_HEIGHT = 750;
    public static final int MARGIN_TOP = 10;
    public static final int GAME_PANEL_HEIGHT = 600;
    public static final int GAME_PANEL_WIDTH = 300;
    public static final int ROWS_COUNT = 20;
    public static final int NEXT_SHAPE_PANEL_ROWS_COUNT = 3;
    public static final int COLUMNS_COUNT = 10;
    public static final int REGULAR_DROP_SPEED = 1000;
    public static final int FAST_DROP_SPEED = 100;
    public static final int LEFT_DIRECTION = -1;
    public static final int RIGHT_DIRECTION = 1;
    public static final int ROW_INDEX = 0;
    public static final int COLUMN_INDEX = 1;
    public static final int REMOVE_ANIMATION_DELAY = 100;
    public static final int BLOCK_SIZE = 30;
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
                    new Block(0, 5,  true),
                    new Block(0, 6,  false),
                    new Block(1, 5,  true),
                    new Block(1, 4,  false),
            },
            new Block[]{
                    new Block(0, 5,  true),
                    new Block(0, 4,  false),
                    new Block(1, 5,  true),
                    new Block(1, 6,  false),
            },
            new Block[]{
                    new Block(0, 5,  true),
                    new Block(1, 4,  false),
                    new Block(1, 5,  true),
                    new Block(1, 6,  false),
            },
    };
}
