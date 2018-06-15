package fr.nicolashoareau_toulousewcs.hack3win;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class ZoomArticlesActivity extends YouTubeBaseActivity {

    private final String API_KEY = "AIzaSyBt5qNEPgi0aIJrgMzE_JkH9uVbgh8Vy7U";
    private final String VIDEO_CODE = "nc7Z3EBMevg";
    YouTubePlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_articles);
        setTitle("Voir un article");

        playerView = (YouTubePlayerView) findViewById(R.id.youTubePlayerView);
        playerView.initialize(API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    youTubePlayer.loadVideo(VIDEO_CODE);
                    youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(ZoomArticlesActivity.this, youTubeInitializationResult.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        TextView tvArticleTitle = findViewById(R.id.tv_articles_title);
        TextView tvArticleResume = findViewById(R.id.tv_resume_articles);
        Button link = findViewById(R.id.btn_link);


        //click bouton lien vers le site
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }


}
