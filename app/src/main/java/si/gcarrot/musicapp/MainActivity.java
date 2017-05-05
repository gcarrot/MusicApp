package si.gcarrot.musicapp;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
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
    TextView currentTitle;

    ImageButton imgBtnPlay;
    ImageButton imgBtnPause;
    ImageButton imgBtnReplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        currentTitle = (TextView) findViewById(R.id.twCurrentTitle);

        imgBtnPlay = (ImageButton) findViewById(R.id.imgBtnPlay);
        imgBtnPause = (ImageButton) findViewById(R.id.imgBtnPause);
        imgBtnReplay = (ImageButton) findViewById(R.id.imgBtnReplay);

        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor == null) {
            // query failed, handle error.
        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Log.e("Urban", "No media on the device");
        } else {
            int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int ArtistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            do {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisArtist = cursor.getString(ArtistColumn);

                musicID = 37;
                CurrentPlay = thisTitle;
                // ...process entry...
                Log.e("Urban", "thisTitle " + thisTitle + " - " + thisArtist + " -- " + thisId);
            } while (cursor.moveToNext());
        }


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

       /* MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.sound_file_1);
        mediaPlayer.start(); */
    }
}
