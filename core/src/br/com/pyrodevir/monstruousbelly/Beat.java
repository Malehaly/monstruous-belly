package br.com.pyrodevir.monstruousbelly;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Beat {

    private Music backSound;
    private Sound death;

    public Beat(){
        backSound = Gdx.audio.newMusic(Gdx.files.internal("sound/backsound.mp3"));
        backSound.setLooping(true);
        death = Gdx.audio.newSound(Gdx.files.internal("sound/lose.mp3"));
    }

    public void play(String som){
        if(som.equals("backsound")){
            backSound.play();
        } else if(som.equals("death")){
            death.play();
        }
    }

    public void dispose(){
        backSound.dispose();
        death.dispose();
    }
}
