import java.awt.*;

public class Constants {
    public static final int WINDOW_WIDTH = 680;
    public static final int WINDOW_HEIGHT = 750;
    public static final int MARGIN_TOP = 10;
    public static final int GAME_PANEL_HEIGHT = 600;

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
//            new Block[]{
//                    new Block(0, 4,  false),
//                    new Block(1, 4,  false),
//                    new Block(1, 5,  true),
//                    new Block(1, 6,  false),
//            },
//            new Block[]{
//                    new Block(0, 6,  false),
//                    new Block(1, 4,  false),
//                    new Block(1, 5,  true),
//                    new Block(1, 6,  false),
//            },
//            new Block[]{
//                    new Block(0, 5,  false),
//                    new Block(0, 4,  false),
//                    new Block(1, 5,  false),
//                    new Block(1, 4,  false),
//            },
//            new Block[]{
//                    new Block(0, 3,  false),
//                    new Block(0, 4,  false),
//                    new Block(0, 5,  true),
//                    new Block(0, 6,  false),
//            },
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
