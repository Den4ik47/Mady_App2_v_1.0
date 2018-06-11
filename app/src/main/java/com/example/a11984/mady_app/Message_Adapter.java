package com.example.a11984.mady_app;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 11984 on 05.05.2018.
 */

public class Message_Adapter extends ListFragment implements MenuItem.OnActionExpandListener{
    List<String> mAllValues;
    List<String> Names;
    private ArrayAdapter<String> mAdapter;
    private Context mContext;
    String Id;
    String global;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        setHasOptionsMenu(true);

        populateList();
    }
    public void onListItemClick(ListView listView, View v, int position, long id) {
        String item = (String) listView.getAdapter().getItem(position);
        GlobalClass.data2=item;
        mAllValues.clear();
        this.mContext.startActivity(new Intent(this.mContext,Message.class));
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
    private void populateList(){
        mAllValues = new LinkedList<>();
        Id= FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Message").child(Id)
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
