package com.google.firebase.codelab.letschat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends ArrayAdapter<Chat> {
    public ChatAdapter(@NonNull  Context mContext, int resource, @NonNull ArrayList<Chat> chatList) {
        super(mContext, resource, chatList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_item_layout, parent, false);
        }

        TextView txtUsername, txtLastMessage, txtChatTime;
        CircleImageView profilePic;
        ImageView tick;
        ImageView msgReceived;

        txtUsername = (TextView) convertView.findViewById(R.id.item_username);
        txtLastMessage = (TextView) convertView.findViewById(R.id.last_message);
        txtChatTime = (TextView) convertView.findViewById(R.id.time_last_message);
        profilePic = (CircleImageView) convertView.findViewById(R.id.item_profile_photo);
        tick = (ImageView) convertView.findViewById(R.id.tick_message);
        msgReceived = (ImageView) convertView.findViewById(R.id.received_message);

        final Chat chat = getItem(position);

        Calendar currentDate = Calendar.getInstance();
        Calendar chatDate = Calendar.getInstance();
        Date date1 = new Date();
        currentDate.setTime(date1);
        chatDate.setTime(chat.getTimestamp().toDate());

        SharedPreferences sp = getContext().getSharedPreferences("com.google.firebase.codelab.letschat", Context.MODE_PRIVATE);

        profilePic.setImageResource(0);
        profilePic.setBackgroundResource(0);

        if(chat.getProfilePic().equals("")){
            profilePic.setImageResource(R.drawable.ic_baseline_account_circle_24);
        }else{
            Glide.with(getContext()).load(chat.getProfilePic()).into(profilePic);
        }

        if(!chat.getSenderMessage().equals(sp.getString("mobile", ""))){
            tick.setVisibility(View.GONE);
            msgReceived.setVisibility(View.VISIBLE);
        }else {
            tick.setVisibility(View.VISIBLE);
            msgReceived.setVisibility(View.GONE);
        }
        txtUsername.setText(chat.getUser());
        txtLastMessage.setText(chat.getLastMessage());
        if(currentDate.get(Calendar.DAY_OF_YEAR) == chatDate.get(Calendar.DAY_OF_YEAR) && currentDate.get(Calendar.YEAR) == chatDate.get(Calendar.YEAR)){
            txtChatTime.setText(chat.getChatTime());
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            txtChatTime.setText(formatter.format(chat.getTimestamp().toDate()));
        }

        return convertView;
    }
}
