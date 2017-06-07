package si.gcarrot.musicapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListOfMusics extends AppCompatActivity {
    ListView listView;
    ArrayList<DataModel> dataModels;
    private static CustomAdapter adapter;

    TextView tvTitleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_musics);

        ArrayList<String> songArtist = new ArrayList<String>();
        ArrayList<String> songTitles = new ArrayList<String>();
        final ArrayList<String> songAlbum = new ArrayList<String>();
        final ArrayList<String> songYear = new ArrayList<String>();
        ArrayList<Long> songID = new ArrayList<>();
        final ArrayList<Long> songDuration = new ArrayList<>();

        tvTitleList = (TextView) findViewById(R.id.tvTitleList);


        dataModels = new ArrayList<>();

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://music")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/music")));
                }
            }
        });


        ContentResolver contentResolver = getContentResolver();
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            tvTitleList.setText(getResources().getString(R.string.error_nopermission));
            Toast.makeText(ListOfMusics.this, getResources().getString(R.string.error_nopermission), Toast.LENGTH_SHORT).show();
            return;
        } else {
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor == null) {
                // query failed, handle error.
            } else if (!cursor.moveToFirst()) {
                // no media on the device
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://music")));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/music")));
                }

                Log.e("Urban", "No media on the device");
            } else {
                int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
                int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
                int ArtistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
                int AlbumColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
                int YearColumn = cursor.getColumnIndex(MediaStore.Audio.Media.YEAR);


                int DurationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
                do {
                    long thisId = cursor.getLong(idColumn);
                    String thisTitle = cursor.getString(titleColumn);
                    String thisArtist = cursor.getString(ArtistColumn);
                    String thisAlbum = cursor.getString(AlbumColumn);
                    String thisYear = cursor.getString(YearColumn);
                    long thisDuration = cursor.getLong(DurationColumn);
                    songArtist.add(thisArtist);
                    songTitles.add(thisTitle);
                    songAlbum.add(thisAlbum);
                    songYear.add(thisYear);
                    songDuration.add(thisDuration);
                    songID.add(thisId);

                    dataModels.add(new DataModel(thisTitle, thisArtist, thisDuration, thisId));

                    // ...process entry...
                    Log.e("Urban", "thisTitle " + thisTitle + " - " + thisArtist + " -- " + thisId);
                } while (cursor.moveToNext());
            }
            adapter = new CustomAdapter(dataModels, getApplicationContext());
            //ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.listview, songTitles);

            listView = (ListView) findViewById(R.id.lvList);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DataModel dataModel = dataModels.get(position);
                    Intent i = new Intent(ListOfMusics.this, MainActivity.class);
                    i.putExtra("musicID", dataModel.getID());
                    i.putExtra("CurrentTitle", dataModel.getTitle());

                    i.putExtra("CurrentAlbum", songAlbum.get(position));
                    i.putExtra("CurrentYear", songYear.get(position));
                    i.putExtra("CurrentDuration", songDuration.get(position));
                    startActivity(i);
                    Log.i("Urban", "test 1 " + dataModel.getAutor());
                }
            });
        }
    }
}
