package com.example.rentmyspot;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class Return2Activity extends AppCompatActivity {
    ListView list;
    SeatingListAdapter seatArrayAdapter;
    String username;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return2);

        username = getIntent().getStringExtra("username");

        list = findViewById(R.id.returnlist);
        db = new DBHelper(Return2Activity.this);
        ShowSeatsOnListView(db);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Seating selectedSeating = (Seating) parent.getItemAtPosition(position);
                showReturnConfirmationDialog(selectedSeating);
            }
        });
    }

    private void ShowSeatsOnListView(DBHelper dataBaseHelper) {
        seatArrayAdapter = new SeatingListAdapter(Return2Activity.this, dataBaseHelper.returnpagelist(username));
        list.setAdapter(seatArrayAdapter);
    }

    private void showReturnConfirmationDialog(final Seating seating) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Return2Activity.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to return this seating?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                returnSeating(seating);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void returnSeating(Seating seating) {
        boolean isReturned = db.returnSeating2(seating);

        if (isReturned) {
            seatArrayAdapter.remove(seating);
            seatArrayAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Seating returned successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Return2Activity.this, HomepageActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Could not return the seating. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}