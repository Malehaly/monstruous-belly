package br.com.pyrodevir.monstruousbelly;

/**
 * Created by devir on 09/11/2017.
 */

public interface PlayServices {

    void singIn();
    void singOut();

    void unlockAchievement(String s);
    void submitScore(int score);
    void showScore();

    boolean isSignedIn();

}
