package br.com.pyrodevir.monstruousbelly;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static br.com.pyrodevir.monstruousbelly.Constants.backgroundCenterVel;
import static br.com.pyrodevir.monstruousbelly.Constants.backgroundSidesVel;
import static br.com.pyrodevir.monstruousbelly.Constants.gravity;
import static br.com.pyrodevir.monstruousbelly.Constants.loadTextures;
import static br.com.pyrodevir.monstruousbelly.Constants.screenx;
import static br.com.pyrodevir.monstruousbelly.Constants.screeny;

public class BackgroundSides {

    private Texture textureDown;
    private Texture textureTop;
    private Texture[] windDir;
    private float auxWindDir;
    private float downposx1;
    private float downposx2;
    private float topposx1;
    private float topposx2;

    public BackgroundSides() {
        textureDown = new Texture("backgroundsides.png");
        downposx1 = 0;
        downposx2 = screenx;

        textureTop = new Texture("backgroundsides.png");
        topposx1 = 0;
        topposx2 = screenx;

        windDir = new Texture[3];
        windDir[0] = loadTextures.get(10);
        windDir[1] = loadTextures.get(11);
        windDir[2] = loadTextures.get(12);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(textureDown, downposx1, (screeny/8)*7, screenx, screeny/8);
        batch.draw(textureDown, downposx2, (screeny/8)*7, screenx, screeny/8);

        //SpriteBatch.draw(textureRegion, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
        batch.draw(textureTop, topposx1, 0, screenx, screeny/8, 0, 0, textureTop.getWidth(), textureTop.getHeight(), false, true);
        batch.draw(textureTop, topposx2, 0, screenx, screeny/8, 0, 0, textureTop.getWidth(), textureTop.getHeight(), false, true);

        if (gravity >= 0) {
            //sprite wind from bottom to top
            batch.draw(windDir[(int) auxWindDir % 3], topposx1, 0, screenx, screeny / 8, 0, 0, windDir[0].getWidth(), windDir[0].getHeight(), false, true);
            batch.draw(windDir[(int) auxWindDir % 3], topposx2, 0, screenx, screeny / 8, 0, 0, windDir[0].getWidth(), windDir[0].getHeight(), false, true);
        } else{
            //sprite wind from top to bottom
            batch.draw(windDir[(int)auxWindDir % 3], downposx1, (screeny/8)*7, screenx, screeny/8, 0, 0, windDir[0].getWidth(), windDir[0].getHeight(), false, false);
            batch.draw(windDir[(int)auxWindDir % 3], downposx2, (screeny/8)*7, screenx, screeny/8, 0, 0, windDir[0].getWidth(), windDir[0].getHeight(), false, false);
        }
    }

    public void update(float time) {
        auxWindDir += 3*time;
        downposx1 += time * backgroundSidesVel;
        downposx2 += time * backgroundSidesVel;

        if (downposx1 + screenx <= 0) {
            downposx1 = screenx;
            downposx2 = 0;
        }
        if (downposx2 + screenx <= 0) {
            downposx2 = screenx;
            downposx1 = 0;
        }

        topposx1 += time * backgroundSidesVel;
        topposx2 += time * backgroundSidesVel;

        if (topposx1 + screenx <= 0) {
            topposx1 = screenx;
            topposx2 = 0;
        }
        if (topposx2 + screenx <= 0) {
            topposx2 = screenx;
            topposx1 = 0;
        }
    }

    public void dispose() {
        textureDown.dispose();
        textureTop.dispose();
        for (Texture t:windDir){
            t.dispose();
        }
    }
}
