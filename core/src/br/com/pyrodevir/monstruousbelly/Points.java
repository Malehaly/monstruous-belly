package br.com.pyrodevir.monstruousbelly;

import com.badlogic.gdx.math.Rectangle;

import static br.com.pyrodevir.monstruousbelly.Constants.backgroundSidesVel;
import static br.com.pyrodevir.monstruousbelly.Constants.gap;
import static br.com.pyrodevir.monstruousbelly.Constants.screeny;

public class Points {

    public Rectangle body;

    public Points(float posx, float posy){
        body = new Rectangle(posx, posy, 10, screeny);
    }

    public int update(float time){
        body.x += backgroundSidesVel*time;
        if(body.x + body.getWidth() < 0){
            return 1;
        } return 0;
    }

    public Rectangle getBody() {
        return body;
    }
}
