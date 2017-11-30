package br.com.pyrodevir.monstruousbelly;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.GameHelper;

import br.com.pyrodevir.monstruousbelly.MainClass;

import static com.badlogic.gdx.Input.Keys.G;

public class AndroidLauncher extends AndroidApplication implements PlayServices {

	private GameHelper gameHelper;
	private final static int requestCode = 1;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
        gameHelper.enableDebugLog(true);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {

            }

            @Override
            public void onSignInSucceeded() {

            }
        };
        gameHelper.setup(gameHelperListener);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainClass(this), config);
	}

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
	public void singIn() {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.beginUserInitiatedSignIn();
                }
            });
        } catch (Exception e){
            Gdx.app.log("MainActivity", "Log in failed: " + e.getMessage());
        }
	}

	@Override
	public void singOut() {
        try{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gameHelper.signOut();
                }
            });
        } catch (Exception e){
            Gdx.app.log("MainActivity", "Log out failed: " + e.getMessage());
        }
	}

	@Override
	public void unlockAchievement(String s) {
        if (isSignedIn()){
            if(s.equals("5")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_5_points));
            } else if (s.equals("10")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_10_points));
            } else if (s.equals("20")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_20_points));
            } else if (s.equals("25")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_25_points));
            } else if (s.equals("30")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_30_points));
            } else if (s.equals("40")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_40_points));
            } else if (s.equals("50")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_50_points));
            } else if (s.equals("60")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_60_points));
            } else if (s.equals("70")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_70_points));
            } else if (s.equals("80")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_80_points));
            } else if (s.equals("90")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_90_points));
            } else if (s.equals("100")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_100_points));
            } else if (s.equals("200")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_200_points));
            } else if (s.equals("500")){
                Games.Achievements.unlock(gameHelper.getApiClient(), getString(
                        R.string.achievement_500_points));
            }
        }
	}

	@Override
	public void submitScore(int score) {
        if (isSignedIn()){
            Games.Leaderboards.submitScore(gameHelper.getApiClient(), getString(
                    R.string.leaderboard_ranking__scores), score);
        }
	}

	@Override
	public void showScore() {
        if (isSignedIn()){
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_ranking__scores)), requestCode);
        } else {
            singIn();
        }
	}

	@Override
	public boolean isSignedIn() {
		return gameHelper.isSignedIn();
	}
}
