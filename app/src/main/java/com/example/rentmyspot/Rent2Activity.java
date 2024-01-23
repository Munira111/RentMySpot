package com.example.rentmyspot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class Rent2Activity extends AppCompatActivity {
    ListView list;
    SeatingListAdapter seatArrayAdapter;
    String username;
    ArrayList<Seating> seatings;
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent2);
        list = findViewById(R.id.rentlist);
        username = (String) getIntent().getSerializableExtra("username");
        db = new DBHelper(this);

        seatings = (ArrayList<Seating>) db.ListAllSeatings(username);

        // Log the size of the seatings list
        Log.d("Rent2Activity", "onCreate: seatings size = " + seatings.size());

        seatArrayAdapter = new SeatingListAdapter(this, seatings);
        list.setAdapter(seatArrayAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Seating selectedSeating = seatings.get(position);
                showRentConfirmationDialog(selectedSeating);
            }
        });
    }

    private void showRentConfirmationDialog(final Seating seating) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Rent2Activity.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to rent this seating?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                rentSeating(seating);
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

    private void rentSeating(Seating seating) {
        boolean isRented = db.rentSeating(seating, username);

        if (isRented) {
            seatings.remove(seating);
            seatArrayAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Seating rented successfully.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Rent2Activity.this, HomepageActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Could not rent the seating. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }
}