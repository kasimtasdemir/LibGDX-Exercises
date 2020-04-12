package com.kasim.bookgametemplate.util;

public class Constants {
    // Visible game world is 5 meters wide
    public static final float VIEWPORT_WIDTH = 5.0f;
    // Visible game world is 5 meters tall
    public static final float VIEWPORT_HEIGHT = 5.0f;
    public static final String TEXTURE_ATLAS_OBJECTS =
            "images/0x72_Dungeon_TexturePackerAtlas.atlas";
    public static final String TEXTURE_ATLAS_SKIN_LIBGDX_UI =
            "skin/uiskin.atlas";
    public static final boolean RENDER_DEBUG_ENABLED = false;
    public static final boolean RENDER_BOX2D_DEBUG_ENABLED = false;
    public static final String SKIN_LIBGDX_UI =
            "skin/uiskin.json";
    // Tilemap unit scale, pixel per meter
    public static final float TILEMAP_PPM = 32.0f;
    public static final String TILEMAP_TMX_PATH = "maps/Level_1.tmx";

    public static enum UserRequest {
        RESET_GAME,
        PLAYER_JUMP,
        BACK_TO_MENU
    }
    public static final float MAX_PLAYER_HORIZONTAL_VELOCITY = 3.0f;
    public static final float MAX_PLAYER_VERTICAL_DOWN_VELOCITY = 3.0f;
    public static final float MAX_PLAYER_VERTICAL_UP_VELOCITY = 3.0f;
    public static final float PLAYER_RUNNING_FORCE_X = 4f;
    public static final float PLAYER_JUMPING_FORCE = 5f;
    public static final float PLAYER_JUMPING_VELOCITY = 5f;
}
