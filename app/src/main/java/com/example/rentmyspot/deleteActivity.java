package com.example.rentmyspot;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
public class deleteActivity extends SigninActivity {
    ListView list;
    SeatingListAdapter seatArrayAdapter;
    String username;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        list = findViewById(R.id.seatlist);
        username = (String) getIntent().getSerializableExtra("username");
        DBHelper db = new DBHelper(this);
        ShowSeatsOnListView(db);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Seating clickedSeat = (Seating) seatArrayAdapter.getItem(i);
                showConfirmationDialog(clickedSeat, db);
            }
        });
    }

    private void ShowSeatsOnListView(DBHelper dataBaseHelper) {
        seatArrayAdapter = new SeatingListAdapter(deleteActivity.this, dataBaseHelper.SeatingList(username));
        list.setAdapter(seatArrayAdapter);
    }

    private void showConfirmationDialog(final Seating seating, final DBHelper db) {
        AlertDialog.Builder builder = new AlertDialog.Builder(deleteActivity.this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this seating?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               boolean check = db.DeleteOne(seating);
                seatArrayAdapter.remove(seating);
                seatArrayAdapter.notifyDataSetChanged();
                Toast.makeText(deleteActivity.this, "Seating Deleted: " + seating.toString(), Toast.LENGTH_SHORT).show();
                if (check) {
                    Intent intent = new Intent(deleteActivity.this, HomepageActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                }
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
}
