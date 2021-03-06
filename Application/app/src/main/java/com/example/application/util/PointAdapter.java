package com.example.application.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.application.R;
import com.example.application.model.Point;

import java.util.List;

public class PointAdapter extends ArrayAdapter<String> {
    private List<Point> points;
    private Context context;

    public PointAdapter(Context context, List<Point> pointList) {
        super(context, R.layout.activity_point);
        this.points = pointList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_point, parent, false);

        TextView title = view.findViewById(R.id.title);
        Button deleteButton = view.findViewById(R.id.deleteButton);
        Button checkButton = view.findViewById(R.id.checkButton);
        Point objectItem = points.get(position);

        title.setText(objectItem.getName());
        deleteButton.setId(objectItem.getId().intValue());
        checkButton.setId(objectItem.getId().intValue());

        return view;
    }

    @Override
    public int getCount() {
        return points.size();
    }

    @Override
    public String getItem(int position) {
        return points.get(position).getId().toString();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
