package com.johnogel.astrobros.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.johnogel.astrobros.AstroBros;

public class DesktopLauncher {
	public static void main (String[] arg) {
                
            //String input = Gdx.files.internal("bro_animation").path()
            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
            config.useGL30 = false;

            config.width = 1200;
            config.height = 800;
            new LwjglApplication(new AstroBros(), config);
	}
}
