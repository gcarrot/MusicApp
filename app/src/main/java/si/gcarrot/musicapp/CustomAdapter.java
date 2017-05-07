package si.gcarrot.musicapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Urban on 5/5/17.
 */
public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener {

    private ArrayList<DataModel> dataSet;
    Context mContext;


    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtAutor;
        TextView txtVersion;
        ImageButton info;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.activity_list_of_musics, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Override
    public void onClick(View v) {

        int position = (Integer) v.getTag();
        Object object = getItem(position);
        DataModel dataModel = (DataModel) object;
    }

    private int astPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;
        viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.listview, parent, false);
        viewHolder.txtName = (TextView) convertView.findViewById(R.id.label);
        viewHolder.txtAutor = (TextView) convertView.findViewById(R.id.autor);
        String duration = String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes(dataModel.getDuration()),
                TimeUnit.MILLISECONDS.toSeconds(dataModel.getDuration()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(dataModel.getDuration()))
        );

        viewHolder.txtName.setText(dataModel.getTitle());
        viewHolder.txtAutor.setText(dataModel.getAutor() + " -- " + duration);
        // Return the completed view to render on screen
        return convertView;
    }
}