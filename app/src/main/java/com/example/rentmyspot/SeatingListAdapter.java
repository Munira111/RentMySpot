package com.example.rentmyspot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;
//
public class SeatingListAdapter extends ArrayAdapter<Seating> {

    private Context context;
    private List<Seating> seatings;

    public SeatingListAdapter(@NonNull Context context, List<Seating> seatings) {
        super(context, R.layout.list_item_seating, seatings);
        this.context = context;
        this.seatings = seatings;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_seating, parent, false);
        }

        Seating seating = seatings.get(position);

        ImageView seatingImage = convertView.findViewById(R.id.seatingImage);
        TextView seatingInfo = convertView.findViewById(R.id.seatingInfo);

        Bitmap bmp = BitmapFactory.decodeByteArray(seating.getImageData(), 0, seating.getImageData().length);
        seatingImage.setImageBitmap(bmp);
        seatingInfo.setText(seating.toString());

        return convertView;
    }
}