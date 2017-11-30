package br.com.pyrodevir.monstruousbelly;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Intersector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static br.com.pyrodevir.monstruousbelly.Constants.btnCentralSize;
import static br.com.pyrodevir.monstruousbelly.Constants.btnCentralx1;
import static br.com.pyrodevir.monstruousbelly.Constants.btnCentralx2;
import static br.com.pyrodevir.monstruousbelly.Constants.btnCentraly;
import static br.com.pyrodevir.monstruousbelly.Constants.charradio;
import static br.com.pyrodevir.monstruousbelly.Constants.gap;
import static br.com.pyrodevir.monstruousbelly.Constants.gravity;
import static br.com.pyrodevir.monstruousbelly.Constants.jump;
import static br.com.pyrodevir.monstruousbelly.Constants.loadTextures;
import static br.com.pyrodevir.monstruousbelly.Constants.randomMax;
import static br.com.pyrodevir.monstruousbelly.Constants.screenx;
import static br.com.pyrodevir.monstruousbelly.Constants.screeny;
import static br.com.pyrodevir.monstruousbelly.Constants.wallGenerate;
import static br.com.pyrodevir.monstruousbelly.Constants.wallWidth;

public class MainClass extends ApplicationAdapter {

    private SpriteBatch batch;
    private BackgroundCenter backCenter;
    private BackgroundSides backSides;
    private Character character;
    private List<Wall> walls;
    private List<Points> points; //score invisible rects for increase score;
    private int score = 0;
    private boolean scoreBoolean = false; //trigger when character passes invisible score rect;
    private float timeWalls; //trigger with wallGenerator constant;
    private int mode = 0; //0-stand 1-normal 2-lose 3-restart;
    private BitmapFont font;
    private GlyphLayout layout = new GlyphLayout();
    private Button buttonPlay;
    private Button buttonRestart;
    private Button buttonRank;
    private Beat beat;
    private PlayServices playServices;

    public MainClass(PlayServices playServices){
        this.playServices = playServices;
    }

    @Override
    public void create () {
        batch = new SpriteBatch();

        loadTextures = new ArrayList<Texture>();
        loadTextures.add(new Texture("walls/wall1.png"));//pos. 0
        loadTextures.add(new Texture("walls/wall2.png"));
        loadTextures.add(new Texture("walls/eyewall1.png"));//2
        loadTextures.add(new Texture("walls/eyewall2.png"));
        loadTextures.add(new Texture("walls/eyewall3.png"));
        loadTextures.add(new Texture("walls/eyewall4.png"));
        loadTextures.add(new Texture("walls/eyewallbg.png"));//6
        loadTextures.add(new Texture("walls/gravitywall1.png"));//7
        loadTextures.add(new Texture("walls/gravitywall2.png"));
        loadTextures.add(new Texture("walls/gravitywall3.png"));
        loadTextures.add(new Texture("backgroundwind1.png"));//10
        loadTextures.add(new Texture("backgroundwind2.png"));
        loadTextures.add(new Texture("backgroundwind3.png"));

        backCenter = new BackgroundCenter();
        backSides = new BackgroundSides();
        character = new Character();
        walls = new ArrayList<Wall>();
        points = new ArrayList<Points>();
        timeWalls = wallGenerate;

        FreeTypeFontGenerator.setMaxTextureSize(2048);
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int)(0.1f*screenx);
        parameter.color = new Color(1,1,1,1);
        font = generator.generateFont(parameter);
        generator.dispose();

        buttonPlay = new Button(new Texture("button/play.png"), btnCentralx1, btnCentraly, btnCentralSize);
        buttonRestart = new Button(new Texture("button/replay.png"), btnCentralx1, btnCentraly, btnCentralSize);
        buttonRank = new Button(new Texture("button/rank.png"), btnCentralx2, btnCentraly, btnCentralSize);

        beat = new Beat();
    }

    @Override
    public void render () {
        input();
        update(Gdx.graphics.getDeltaTime());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        draw();
        batch.end();
    }

    //aux of render;
    public void draw(){
        backCenter.draw(batch);
        for(Wall w:walls){
            w.draw(batch);
        }
        backSides.draw(batch);
        character.draw(batch);
        font.draw(batch, "score: "+String.valueOf(score), (screenx - layoutWidth(font, "score: "+String.valueOf(score)))/2, (0.98f*screeny));

        if(mode == 0){
            buttonPlay.draw(batch);
            buttonRank.draw(batch);
        }
        if(mode == 3){
            buttonRestart.draw(batch);
            buttonRank.draw(batch);
            font.draw(batch, "Try again!", (screenx - layoutWidth(font, "Try again!"))/2, (screeny/2)- btnCentralSize/2);
        }
    }

    //aux of render;
    public void update(float time){
        beat.play("backsound");
        if(mode == 1){
            backCenter.update(time);
            backSides.update(time);

            //if wall/score rect passes X it is removed.
            for(int i=0;i<walls.size();i++){
                if(walls.get(i).update(time) ==1){
                    walls.remove(i);
                    i--;
                }
            }
            for(int i=0;i<points.size();i++){
                if(points.get(i).update(time) ==1){
                    points.remove(i);
                    i--;
                }
            }

            //after timewalls tick, a new wall and score rect is created;
            timeWalls -= time;
            if(timeWalls <= 0){
                Random random = new Random();
                int pos = random.nextInt(randomMax);
                int wallType = random.nextInt(6);
                pos -= randomMax/2;
                if(wallType <= 2){
                    //wall normal;
                    walls.add(new Wall(screenx, screeny/2 + pos + gap/2, true));
                    walls.add(new Wall(screenx, screeny/2 + pos - gap/2, false));
                } else if(wallType == 3 || wallType == 4){
                    //eye wall
                    walls.add(new Wall(screenx,screeny/2 + pos, 2));
                } else {
                    //gravity wall
                    walls.add(new Wall(screenx,screeny/2 + (pos/3), 3));
                    gravity += -(gravity*2);
                    jump += -(jump*2);
                }
                points.add(new Points(screenx + wallWidth + (charradio*2), 0));
                //points.add(new Points(screenx + wallWidth + (charradio*2), screeny/2 + pos - gap/2));
                timeWalls = wallGenerate;
            }

            //detects the collision of character on wall;
            for(Wall w:walls){
                if(Intersector.overlaps(character.getCircle(), w.getBody())){
                    character.crash();
                    beat.play("death");
                    mode = 2;
                    playServices.submitScore(score);
                    if (score >= 500){
                        playServices.unlockAchievement("500");
                    }
                    if (score >= 200){
                        playServices.unlockAchievement("200");
                    }
                    if (score >= 100){
                        playServices.unlockAchievement("100");
                    }
                    if (score >= 90){
                        playServices.unlockAchievement("90");
                    }
                    if (score >= 80){
                        playServices.unlockAchievement("80");
                    }
                    if (score >= 70){
                        playServices.unlockAchievement("70");
                    }
                    if (score >= 60){
                        playServices.unlockAchievement("60");
                    }
                    if (score >= 50){
                        playServices.unlockAchievement("50");
                    }
                    if (score >= 40){
                        playServices.unlockAchievement("40");
                    }
                    if (score >= 30){
                        playServices.unlockAchievement("30");
                    }
                    if (score >= 25){
                        playServices.unlockAchievement("25");
                    }
                    if (score >= 20){
                        playServices.unlockAchievement("20");
                    }
                    if (score >= 10){
                        playServices.unlockAchievement("10");
                    }
                    if (score >= 5){
                        playServices.unlockAchievement("5");
                    }
                }
            }
            //detects when character passes invisible score rect;
            boolean intersection = false;
            for(Points p:points){
                if(Intersector.overlaps(character.getCircle(), p.getBody())){
                    if(!scoreBoolean){
                        score++;
                        scoreBoolean = true;
                    }
                    intersection = true;
                }
            }if(!intersection){
                scoreBoolean = false;
            }
        }

        //on normal game os lose, the character move;
        if(mode == 1 || mode == 2){
            if(character.update(time) == 1){
                mode = 3;
            }
        }
    }

    //aux of render;
    public void input(){
        if(Gdx.input.justTouched()){
            int x = Gdx.input.getX();
            int y = Gdx.input.getY();
            if(mode == 0){
                buttonPlay.isPressed(x,y);
                buttonRank.isPressed(x,y);
            }else if(mode ==1){
                character.jump();
            }else if(mode == 3){
                buttonRestart.isPressed(x,y);
                buttonRank.isPressed(x,y);
            }
        } else if (!Gdx.input.isTouched() && buttonPlay.getPressed()){
            mode = 1;
            buttonPlay.setPressed(false);
        } else if (!Gdx.input.isTouched() && buttonRestart.getPressed()){
            mode = 1;
            character.restart();
            walls.clear();
            timeWalls = wallGenerate;
            gravity = 0.2f*screeny;
            jump = 0.2f*screeny;
            score = 0;
            scoreBoolean = false;
            points.clear();
            buttonRestart.setPressed(false);
        } else if (!Gdx.input.isTouched() && buttonRank.getPressed()){
            playServices.showScore();
            buttonRank.setPressed(false);
        }
    }

    //aux of score font;
    private float layoutWidth(BitmapFont font, String text){
        layout.reset();
        layout.setText(font, text);
        return layout.width;
    }

    @Override
    public void dispose () {
        //dont need to dispose score rects, its only rects without texture;
        backCenter.dispose();
        backSides.dispose();
        for (Wall w:walls){
            w.dispose();
        }
        for (Texture t:loadTextures){
            t.dispose();
        }
        character.dispose();
        font.dispose();
        buttonPlay.dispose();
        buttonRestart.dispose();
        buttonRank.dispose();
        beat.dispose();
        batch.dispose();
    }
}
