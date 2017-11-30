package br.com.pyrodevir.monstruousbelly;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static br.com.pyrodevir.monstruousbelly.Constants.backgroundSidesVel;
import static br.com.pyrodevir.monstruousbelly.Constants.gravity;
import static br.com.pyrodevir.monstruousbelly.Constants.jump;
import static br.com.pyrodevir.monstruousbelly.Constants.loadTextures;
import static br.com.pyrodevir.monstruousbelly.Constants.screeny;
import static br.com.pyrodevir.monstruousbelly.Constants.wallGenerate;
import static br.com.pyrodevir.monstruousbelly.Constants.wallWidth;

public class Wall {
    private Texture[] texture;
    private float auxTexture; //aux de textura.
    private Rectangle body;
    private boolean updown;
    private boolean isGoingUp;
    private int especialTypeWall; //1 - normal, 2 - eye, 3 - gravity;

    //create a normal wall;
    public Wall(float posx, float posy, boolean updown){
        this.updown = updown;
        if(updown){ //normal part with flip;
            body = new Rectangle(posx, posy, wallWidth, screeny);
        }else{      //normal part without flip, the Y begins under the screen;
            body = new Rectangle(posx, posy-screeny, wallWidth, screeny);
        }
        texture = new Texture[2];
        texture[0] = loadTextures.get(0);
        texture[1] = loadTextures.get(1);
        especialTypeWall = 1;
    }

    //create a especial wall;
    public Wall(float posx, float posy, int especialTypeWall){
        this.especialTypeWall = especialTypeWall;
        if(especialTypeWall == 2) {
            body = new Rectangle(posx, posy, wallWidth, wallWidth);
            texture = new Texture[5];
            texture[0] = loadTextures.get(2);
            texture[1] = loadTextures.get(3);
            texture[2] = loadTextures.get(4);
            texture[3] = loadTextures.get(5);
            texture[4] = loadTextures.get(6); //background;
            isGoingUp = false;
        } else if (especialTypeWall == 3){
            body = new Rectangle(posx, posy, wallWidth*2f, wallWidth*2f);
            texture = new Texture[3];
            texture[0] = loadTextures.get(7);
            texture[1] = loadTextures.get(8);
            texture[2] = loadTextures.get(9);
        }
    }

    public Rectangle getBody() {
        return body;
    }
    public int getEspecialTypeWall() {return especialTypeWall;}

    public void draw(SpriteBatch batch){
        if(especialTypeWall == 1) {
            batch.draw(texture[(int) auxTexture % 2],
                    body.x, body.y*0.96f,
                    body.getWidth(), body.getHeight(), 0, 0,
                    texture[(int) auxTexture % 2].getWidth(), texture[(int) auxTexture % 2].getHeight(), false, updown);
        } else if(especialTypeWall == 2){
            batch.draw(texture[4], body.x + ((body.getWidth()*1.35f)/2) -(texture[4].getWidth()*0.77f), 0, wallWidth/14, screeny); //eye background
            batch.draw(texture[(int) auxTexture % 4],
                    body.x - (texture[0].getWidth()/12), body.y - (texture[0].getWidth()/12),
                    body.getWidth()*1.35f, body.getHeight()*1.35f, 0, 0,
                    texture[(int) auxTexture % 4].getWidth(), texture[(int) auxTexture % 4].getHeight(), false, false);
        } else if(especialTypeWall == 3){
            batch.draw(texture[(int) auxTexture % 3],
                    body.x - (texture[0].getWidth()/8), body.y - (texture[0].getHeight()/8),
                    body.getWidth()*1.3f, body.getHeight()*1.3f, 0, 0,
                    texture[(int) auxTexture % 3].getWidth(), texture[(int) auxTexture % 3].getHeight(), false, false);
    }
    }

    public int update(float time){
        if(especialTypeWall == 2){
            auxTexture += 4*time/2;
            if (body.y < screeny/7){
                body.y = screeny/7;
                isGoingUp = true;
            } else if( body.y + body.getHeight() > 7*(screeny/8)) {
                isGoingUp = false;
                body.y = 7*(screeny/8) - body.getHeight();
            }

            if (isGoingUp){
                body.y += -backgroundSidesVel*time*1.5f;
            } else {
                body.y += backgroundSidesVel*time*1.5f;
            }

        } else if( especialTypeWall == 3){
            auxTexture += 3*time;
        } else {
            auxTexture += 2*time;
        }
        body.x += backgroundSidesVel*time;

        if(body.x + body.getWidth()*1.3f < 0){
            return 1;
        } return 0;
    }

    public void dispose(){
        for(Texture t:texture){
            t.dispose();
        }
    }
}