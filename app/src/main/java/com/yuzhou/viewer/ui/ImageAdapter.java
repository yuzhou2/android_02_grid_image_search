package com.yuzhou.viewer.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.yuzhou.viewer.R;
import com.yuzhou.viewer.model.GoogleImage;

import java.util.List;

/**
 * Created by yuzhou on 2015/08/03.
 */
public class ImageAdapter extends ArrayAdapter<GoogleImage>
{
    public ImageAdapter(Context context, List<GoogleImage> images)
    {
        super(context, R.layout.item_image, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image, parent, false);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.ivImage);
            holder.title = (TextView) convertView.findViewById(R.id.tvTitle);
            convertView.setTag(holder);
        }

        GoogleImage image = getItem(position);
        Picasso.with(getContext()).load(image.getTbUrl()).resize(image.getTbWidth(), image.getTbHeight()).into(holder.image);
        holder.title.setText(image.getTitleNoFormatting());

        return convertView;
    }

    private static class ViewHolder
    {
        private ImageView image;
        private TextView title;
    }
}