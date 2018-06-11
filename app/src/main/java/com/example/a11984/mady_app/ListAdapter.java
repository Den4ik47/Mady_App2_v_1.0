package com.example.a11984.mady_app;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 11984 on 12.04.2018.
 */
public class ListAdapter extends ListFragment implements SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {
    List<String> mAllValues;
    private ArrayAdapter<String> mAdapter;
    private Context mContext;
    private int counter=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);
        populateList();
    }
    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        String item = (String) listView.getAdapter().getItem(position);
        mAllValues.clear();
        counter++;
        try{
            FirebaseDatabase.getInstance().getReference().child("Tenders").child(item)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getValue();
                                mAllValues.add(snapshot.getKey());
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }

                    });
            mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mAllValues);
            setListAdapter( mAdapter);
            if(counter==1){GlobalClass.data1=item;}
         if(counter==2){
             GlobalClass.data=item;
             this.mContext.startActivity(new Intent(this.mContext,Selecting.class));
         }
        }
        catch (Exception ex){}
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.search_fragment, container, false);
        ListView listView = layout.findViewById(android.R.id.list);
        TextView emptyTextView = layout.findViewById(android.R.id.empty);
        listView.setEmptyView(emptyTextView);
        listView.setAdapter(mAdapter);
        return layout;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search__tender, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint("Search");

        super.onCreateOptionsMenu(menu, inflater);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText == null || newText.trim().isEmpty()) {
            resetSearch();
            return false;
        }

        List<String> filteredValues = new ArrayList<String>(mAllValues);

        for (String value : mAllValues) {
            if (!value.toLowerCase().contains(newText.toLowerCase())) {
                filteredValues.remove(value);
            }
        }

        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, filteredValues);
        setListAdapter(mAdapter);

        return false;
    }

    public void resetSearch() {
        mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mAllValues);
        setListAdapter(mAdapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return true;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return true;
    }

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String item);
    }

    public void populateList(){
        mAllValues = new LinkedList<>();
        FirebaseDatabase.getInstance().getReference().child("Tenders")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getValue();
                            mAllValues.add(snapshot.getKey());
                        mAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }

                });
                mAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1, mAllValues);
        setListAdapter( mAdapter);

    }

}