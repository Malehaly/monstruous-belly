package br.com.pyrodevir.monstruousbelly;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static br.com.pyrodevir.monstruousbelly.Constants.*;

public class BackgroundCenter {

    private Texture texture;
    private float posx1;
    private float posx2;

    public BackgroundCenter(){
        texture = new Texture("backgroundcenter.png");

        posx1 = 0;
        posx2 = screenx;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture, posx1, 0, screenx, screeny);
        batch.draw(texture, posx2, 0, screenx, screeny);
    }

    public void update(float time){
     posx1 += time * backgroundCenterVel;
     posx2 += time * backgroundCenterVel;

     if(posx1 + screenx <= 0){
         posx1 = screenx;
         posx2 = 0;
     }
     if(posx2 + screenx <= 0){
         posx2 = screenx;
         posx1 = 0;
     }
    }

    public void dispose(){
        texture.dispose();
    }
}
