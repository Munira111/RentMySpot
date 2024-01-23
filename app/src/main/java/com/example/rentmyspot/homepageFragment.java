package com.example.rentmyspot;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
//
public class homepageFragment extends Fragment {
    SeatingListAdapter seatArrayAdapter;
    ListView list;
    DBHelper DB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = view.findViewById(R.id.list);
        DB = new DBHelper(getActivity().getApplicationContext());

        String currentUser = getArguments().getString("username");

        ShowSeatsOnListView(DB, currentUser);
    }

    private void ShowSeatsOnListView(DBHelper dataBaseHelper, String currentUser) {
        seatArrayAdapter = new SeatingListAdapter(getActivity().getApplicationContext(), dataBaseHelper.ListALLseatings());
        list.setAdapter(seatArrayAdapter);
    }
}