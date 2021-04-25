package com.example.application.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.application.R;
import com.example.application.model.DaysHistory;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<String> {
    private List<DaysHistory> data;
    private Context context;

    public HistoryAdapter(Context context, List<DaysHistory> data) {
        super(context, R.layout.activity_history);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_history, parent, false);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView time = (TextView) view.findViewById(R.id.count);
        DaysHistory objectItem = data.get(position);

        title.setText(objectItem.getDate());
        time.setText("Число точек: " + objectItem.getCount());

        return view;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getDate();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
