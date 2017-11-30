package br.com.pyrodevir.monstruousbelly;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import static br.com.pyrodevir.monstruousbelly.Constants.*;

public class Character {

    private Circle circle;
    private Texture[] frames;
    private float framesCount;
    private Vector2 vel;

    public Character (){
        circle = new Circle(charposx, screeny/2, charradio);

        frames = new Texture[4];
        for(int i=1; i<=4; i++){
            frames[i-1] = new Texture("character/char"+i+".png");
        }
        vel = new Vector2(0,0);
    }

    public Circle getCircle() {
        return circle;
    }

    public void draw (SpriteBatch batch){
        batch.draw(frames[(int)framesCount%4], circle.x - circle.radius*2.2f, circle.y - circle.radius*2f, circle.radius*4, circle.radius*4);
    }

    public int update(float time){
        framesCount += 4*time;

        circle.x += vel.x * time;
        circle.y += vel.y * time;
        vel.y -= gravity * time;

        if(circle.y + circle.radius >= (7)*(screeny/8)){
            circle.y = (7)*(screeny/8) - circle.radius;
            vel.y = -0.2f*screeny;
        }
        if(circle.y - circle.radius <= screeny/9){
            circle.y = circle.radius + screeny/9;
            vel.y = 0.2f*screeny;
        }

        if(circle.x + circle.radius*3 <= 0){
            return 1;
        }

        return 0;
    }

    public void jump(){
        vel.y += jump;
    }

    public void crash(){
        vel.x = backgroundSidesVel;
        vel.y = 0;
    }

    public void restart(){
        circle = new Circle(charposx, screeny/2, charradio);
        vel = new Vector2(0,0);
    }

    public void dispose(){
        for(int i=0; i <=3; i++){
            frames[i].dispose();
        }
    }
}
