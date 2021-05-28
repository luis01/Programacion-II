package com.ugb.miprimerproyecto;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class chatsArrayAdapter extends ArrayAdapter {
    private Context context;
    private List<chatMessage> chatMessageList = new ArrayList<>();
    private TextView chatText;

    public chatsArrayAdapter(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }
    public void add(chatMessage object){
        chatMessageList.add(object);
        super.add(object);
    }
    public int getCount(){
        return chatMessageList.size();
    }
}
