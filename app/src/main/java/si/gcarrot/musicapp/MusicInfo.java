package si.gcarrot.musicapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MusicInfo extends AppCompatActivity {

    private String CurrentPlay;
    private String CurrentAlbum;
    private String CurrentYear;
    private long CurrentDurration;

    TextView currentTitle;
    TextView currentAlbum;
    TextView currentYear;
    TextView currentDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_info);

        currentTitle = (TextView) findViewById(R.id.tvTitle);
        currentAlbum = (TextView) findViewById(R.id.tvAlbum);
        currentYear = (TextView) findViewById(R.id.tvYear);
        currentDuration = (TextView) findViewById(R.id.tvDuration);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            CurrentPlay = intent.getExtras().getString("CurrentTitle");

            CurrentAlbum = intent.getExtras().getString("CurrentAlbum");
            CurrentYear = intent.getExtras().getString("CurrentYear");
            CurrentDurration = intent.getExtras().getLong("CurrentDuration");

            String duration = String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes(CurrentDurration),
                    TimeUnit.MILLISECONDS.toSeconds(CurrentDurration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(CurrentDurration))
            );

            currentTitle.setText(getResources().getString(R.string.info_title) + " " + CurrentPlay);
            currentAlbum.setText(getResources().getString(R.string.info_album) + " " + CurrentAlbum);
            currentYear.setText(getResources().getString(R.string.info_year) + " " + CurrentYear);
            currentDuration.setText(getResources().getString(R.string.info_duration) + " " + duration);
        } else {
            currentTitle.setText(getResources().getString(R.string.select_music));
        }

    }
}
