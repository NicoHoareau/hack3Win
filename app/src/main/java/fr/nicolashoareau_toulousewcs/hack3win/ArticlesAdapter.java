package fr.nicolashoareau_toulousewcs.hack3win;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.VideoView;

import java.util.List;

public class ArticlesAdapter extends ArrayAdapter<ArticlesModel> {

    public ArticlesAdapter(Context context, List<ArticlesModel> articlesModelList) {
        super(context, 0, articlesModelList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                    inflate(R.layout.activity_zoom_articles, parent, false);
        }
        ArticlesModel articlesModel = (ArticlesModel) getItem(position);

        TextView tvArticlesTitle = convertView.findViewById(R.id.tv_articles_title);
        TextView tvArticlesResume = convertView.findViewById(R.id.tv_resume_articles);
        VideoView videoArticles = convertView.findViewById(R.id.videoView_articles);


        return convertView;

    }
}
