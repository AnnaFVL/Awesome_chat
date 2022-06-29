package com.example.awesomechat;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.zip.Inflater;

public class AwesomeMessageAdapter extends ArrayAdapter<AwesomeMessage> {

    private List<AwesomeMessage> messages;
    private Activity activity;


    public AwesomeMessageAdapter(Activity context, int resource, List<AwesomeMessage> messages) {
        super(context, resource, messages);
        this.messages = messages;
        this.activity = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        AwesomeMessage awesomeMessage = getItem(position);
        int layoutResource = 0;
        int viewType = getItemViewType(position);

        if (viewType == 0) {
            layoutResource = R.layout.my_message_item;
        } else {
            layoutResource = R.layout.your_message_item;
        }

        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = layoutInflater.inflate(layoutResource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        boolean isText = true;
        if (awesomeMessage.getImageUrl() != null) isText = false;

        if (isText) {
            viewHolder.bubbleTextView.setVisibility(View.VISIBLE);
            viewHolder.bubbleImageView.setVisibility(View.GONE);
            viewHolder.bubbleTextView.setText(awesomeMessage.getText());
        }
        else {
            viewHolder.bubbleTextView.setVisibility(View.GONE);
            viewHolder.bubbleImageView.setVisibility(View.VISIBLE);
            Glide.with(viewHolder.bubbleImageView.getContext()).load(awesomeMessage.getImageUrl()).into(viewHolder.bubbleImageView);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int flag;
        AwesomeMessage awesomeMessage = messages.get(position);
        if (awesomeMessage.isMine()) {
            flag = 0;
        }
        else {
            flag = 1;
        }
        return flag;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    private class ViewHolder {
        private TextView bubbleTextView;
        private  ImageView bubbleImageView;

        public ViewHolder (View view) {
            bubbleImageView = view.findViewById(R.id.bubbleImageView);
            bubbleTextView = view.findViewById(R.id.bubbleTextView);
        }

        public TextView getBubbleTextView() {
            return bubbleTextView;
        }

        public void setBubbleTextView(TextView bubbleTextView) {
            this.bubbleTextView = bubbleTextView;
        }

        public ImageView getBubbleImageView() {
            return bubbleImageView;
        }

        public void setBubbleImageView(ImageView bubbleImageView) {
            this.bubbleImageView = bubbleImageView;
        }
    }
}
