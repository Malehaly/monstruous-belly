package br.com.pyrodevir.monstruousbelly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.List;

public class Constants {
    public static int screenx = Gdx.graphics.getWidth();
    public static int screeny = Gdx.graphics.getHeight();

    public static List<Texture> loadTextures;

    public static float backgroundCenterVel = -0.1f*screenx;
    public static float backgroundSidesVel = -0.3f*screenx;

    public static int charposx = (int)(0.2*screenx);
    public static int charradio = (int)(0.04*screeny);

    public static float wallWidth = 0.2f*screenx;
    public static float gravity = 0.2f*screeny;
    public static float jump = 0.2f*screeny;

    public static float wallGenerate = 3.5f;
    public static int randomMax = (int)(0.6f*screeny);
    public static int gap = (int)(0.22f*screeny);

    public static int btnCentralSize = (int)(0.4f*screenx);
    public static int btnCentralx1 = screenx/2 - btnCentralSize;
    public static int btnCentralx2 = screenx/2;
    public static int btnCentraly = (screeny - btnCentralSize)/2;
}

