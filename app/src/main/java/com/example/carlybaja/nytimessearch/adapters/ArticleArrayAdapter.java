package com.example.carlybaja.nytimessearch.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.carlybaja.nytimessearch.models.Article;
import com.example.carlybaja.nytimessearch.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by carlybaja on 2/12/16.
 */
public class ArticleArrayAdapter extends ArrayAdapter<Article> {

    public static class ViewHolder{
        ImageView image;
        TextView  headline;
    }

    public ArticleArrayAdapter(Context context, List<Article> articles){
        super(context,android.R.layout.simple_list_item_1,articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // get the data items
        Article article = this.getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_article_result, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.image = (ImageView)convertView.findViewById(R.id.ivImage);
            viewHolder.headline = (TextView)convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        // set Title
        viewHolder.headline.setText(article.headline);

        // set image using Picasso
        if (!TextUtils.isEmpty(article.thumbNail)){
            Picasso.with(getContext()).load(article.thumbNail).placeholder(R.mipmap.placeholder_image).into(viewHolder.image);
        }
        return  convertView;
    }





}


