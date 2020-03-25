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
    public static final boolean RENDER_DEBUG_ENABLED = true;
    public static final String SKIN_LIBGDX_UI =
            "skin/uiskin.json";
    // Tilemap unit scale, pixel per meter
    public static final float TILEMAP_PPM = 32.0f;
    public static final String TILEMAP_TMX_PATH = "maps/Level_1.tmx";
}
