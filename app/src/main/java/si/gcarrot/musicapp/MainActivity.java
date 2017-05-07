package si.gcarrot.musicapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.StringReader;

public class MainActivity extends AppCompatActivity {
    Context context;
    MediaPlayer mMediaPlayer;

    private long musicID;
    private String CurrentPlay;
    private String CurrentAlbum;
    private String CurrentYear;
    private long CurrentDurration;
    TextView currentTitle;

    ImageButton imgBtnPlay;
    ImageButton imgBtnPause;
    ImageButton imgBtnReplay;
    ImageButton imgBtnList;
    ImageButton imgBtnInfo;
    ImageButton imgBtnDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        final Intent intent = getIntent();
        currentTitle = (TextView) findViewById(R.id.twCurrentTitle);

        imgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        imgBtnPause = (ImageButton) findViewById(R.id.imgBtnPause);
        imgBtnReplay = (ImageButton) findViewById(R.id.imgBtnReplay);
        imgBtnList = (ImageButton) findViewById(R.id.imgBtnList);

        imgBtnInfo = (ImageButton) findViewById(R.id.imgBtnInfo);
        imgBtnDownload = (ImageButton) findViewById(R.id.imgBtnDownload);


        imgBtnList.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.reset();
                }

                Intent i = new Intent(MainActivity.this, ListOfMusics.class);

                startActivity(i);
            }
        });

        imgBtnInfo.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.reset();
                }

                Intent i = new Intent(MainActivity.this, MusicInfo.class);
                if (intent.getExtras() != null) {
                    i.putExtra("CurrentTitle", CurrentPlay);

                    i.putExtra("CurrentAlbum", CurrentAlbum);
                    i.putExtra("CurrentYear", CurrentYear);
                    i.putExtra("CurrentDuration", CurrentDurration);
                }
                startActivity(i);
            }
        });

        imgBtnDownload.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                if (mMediaPlayer != null) {
                    mMediaPlayer.reset();
                }

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://music")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/music")));
                }
            }
        });

        if (intent.getExtras() != null) {
            musicID = intent.getExtras().getLong("musicID");
            CurrentPlay = intent.getExtras().getString("CurrentTitle");

            CurrentAlbum = intent.getExtras().getString("CurrentAlbum");
            CurrentYear = intent.getExtras().getString("CurrentYear");
            CurrentDurration = intent.getExtras().getLong("CurrentDuration");

            //long id = thisId;
            Uri contentUri = ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, musicID);

            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mMediaPlayer.setDataSource(getApplicationContext(), contentUri);
                mMediaPlayer.prepare();


            } catch (IOException e) {
                e.printStackTrace();
            }

            imgBtnPlay.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the numbers View is clicked on.
                @Override
                public void onClick(View view) {
                    mMediaPlayer.start();
                    currentTitle.setText(CurrentPlay);
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.now_playing) + " " + CurrentPlay, Toast.LENGTH_SHORT).show();
                }
            });

            imgBtnPause.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the numbers View is clicked on.
                @Override
                public void onClick(View view) {
                    mMediaPlayer.pause();
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.pause), Toast.LENGTH_SHORT).show();
                }
            });
            imgBtnReplay.setOnClickListener(new View.OnClickListener() {
                // The code in this method will be executed when the numbers View is clicked on.
                @Override
                public void onClick(View view) {
                    mMediaPlayer.seekTo(0);
                    mMediaPlayer.start();
                    Toast.makeText(MainActivity.this, getResources().getString(R.string.replay), Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            Toast.makeText(MainActivity.this, getResources().getString(R.string.select_music), Toast.LENGTH_SHORT).show();
            /* Intent numbersIntent = new Intent(MainActivity.this, ListOfMusics.class);
            startActivity(numbersIntent);*/
        }
    }
}
