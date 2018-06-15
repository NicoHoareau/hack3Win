package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class ContributeurAdapter extends ArrayAdapter<ContributeurModel> {


    public ContributeurAdapter(Context context, ArrayList<ContributeurModel> contributeur) {
        super(context, 0, contributeur);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ContributeurModel contributeurModel = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_contributeur, parent, false);
        }

        TextView tvtitle = (TextView) convertView.findViewById(R.id.tv_nameVideo);
        TextView tvResume = convertView.findViewById(R.id.tv_descriptionVideo);

        // Populate the data into the template view using the data object
        tvtitle.setText(contributeurModel.getTitle());
        tvResume.setText(contributeurModel.getResume());


        return convertView;
    }
}
