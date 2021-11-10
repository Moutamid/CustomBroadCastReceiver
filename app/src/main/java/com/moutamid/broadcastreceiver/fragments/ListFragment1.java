package com.moutamid.broadcastreceiver.fragments;

import static android.view.LayoutInflater.from;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.moutamid.broadcastreceiver.R;
import com.moutamid.broadcastreceiver.activities.AttractionsActivity;
import com.moutamid.broadcastreceiver.activities.RestaurantsActivity;
import com.moutamid.broadcastreceiver.databinding.FragmentListBinding;
import com.moutamid.broadcastreceiver.models.ListItemModel;

import java.util.ArrayList;
import java.util.List;

public class ListFragment1 extends Fragment {
    private static final String TAG = "ListFragment";

    public ListFragment1() {
        // Required empty public constructor
    }

    public ListFragment1(String currentListType) {
        Log.d(TAG, "ListFragment1: ");
        this.currentListType = currentListType;
    }

    String currentListType = "attraction";

    final String RESTAURANT = "restaurant";
    final String ATTRACTION = "attraction";

    ArrayList<ListItemModel> currentList = new ArrayList<>();

    ArrayList<ListItemModel> restaurantsList = new ArrayList<>();
    ArrayList<ListItemModel> attractionsList = new ArrayList<>();

    private RecyclerView conversationRecyclerView;
    private RecyclerViewAdapterMessages adapter;

    private FragmentListBinding b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        b = FragmentListBinding.inflate(inflater, container, false);
        Log.d(TAG, "onCreateView: ");
        setValueToLists();

        initRecyclerView();

        currentList.clear();

        if (currentListType.equals(ATTRACTION))
            currentList = attractionsList;

        else currentList = restaurantsList;

        return b.getRoot();
    }

    public void setSelectedItemOnRecyc(int position) {
        Log.d(TAG, "setSelectedItemOnRecyc: ");
//        if (adapter.currentTextView != null)
//            adapter.currentTextView.setBackgroundResource(0);

        for (int i = 0; i <= adapter.getItemCount() - 1; i++) {
            TextView textView = (TextView) linearLayoutManager.findViewByPosition(i);
            if (textView != null)
                textView.setBackgroundResource(0);
            else Log.d(TAG, "setSelectedItemOnRecyc: First TV is null");
        }

        TextView textView = (TextView) linearLayoutManager.findViewByPosition(position);
        if (textView != null)
            textView.setBackgroundResource(R.color.grey);
        else Log.d(TAG, "setSelectedItemOnRecyc: Sec TV is null");
    }

    LinearLayoutManager linearLayoutManager;

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: ");
        conversationRecyclerView = b.listRecyclerView;
        conversationRecyclerView.addItemDecoration(new DividerItemDecoration(conversationRecyclerView.getContext(), DividerItemDecoration.VERTICAL));
        adapter = new RecyclerViewAdapterMessages();
        linearLayoutManager = new LinearLayoutManager(requireContext());
        conversationRecyclerView.setLayoutManager(linearLayoutManager);
        conversationRecyclerView.setHasFixedSize(true);
        conversationRecyclerView.setNestedScrollingEnabled(false);

        conversationRecyclerView.setAdapter(adapter);

    }

    private class RecyclerViewAdapterMessages extends RecyclerView.Adapter
            <RecyclerViewAdapterMessages.ViewHolderRightMessage> {

        @NonNull
        @Override
        public ViewHolderRightMessage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
            return new ViewHolderRightMessage(view);
        }

        public TextView currentTextView;

        @Override
        public void onBindViewHolder(@NonNull final ViewHolderRightMessage holder, int position) {
            holder.title.setText(currentList.get(position).getName());

            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "onClick: ");
                    if (currentTextView != null) {
                        currentTextView.setBackgroundResource(0);
                    }

                    currentTextView = holder.title;
                    currentTextView.setBackgroundResource(R.color.grey);

                    if (currentListType.equals(ATTRACTION)) {

                        ((AttractionsActivity) requireContext())
                                .loadAnotherUrlOnF(currentList.get(position).getLink());

                        ((AttractionsActivity) requireContext()).hideListFragment();
                        ((AttractionsActivity) requireContext()).showBrowserFragment();

                        ((AttractionsActivity) requireContext()).setSelectedOnList(position);
                        ((AttractionsActivity) requireContext()).setSelectedOnListH(position);

                    } else {
                        ((RestaurantsActivity) requireContext())
                                .loadAnotherUrlOnF(currentList.get(position).getLink());

                        ((RestaurantsActivity) requireContext()).hideListFragment();
                        ((RestaurantsActivity) requireContext()).showBrowserFragment();

                        ((RestaurantsActivity) requireContext()).setSelectedOnList(position);
                        ((RestaurantsActivity) requireContext()).setSelectedOnListH(position);
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            if (currentList == null)
                return 0;
            return currentList.size();
        }

        public class ViewHolderRightMessage extends RecyclerView.ViewHolder {

            TextView title;

            public ViewHolderRightMessage(@NonNull View v) {
                super(v);
                title = v.findViewById(R.id.item_text_view);

            }
        }

    }

    private void setValueToLists() {
        Log.d(TAG, "setValueToLists: ");

        attractionsList.add(new ListItemModel("The Lincoln Park Zoo", "https://www.lpzoo.org/"));
        attractionsList.add(new ListItemModel("Navy Pier", "https://navypier.org/"));
        attractionsList.add(new ListItemModel("The Museum of Science and Industry", "https://www.msichicago.org/"));
        attractionsList.add(new ListItemModel("The Art Institute", "https://www.artinstitutes.edu/"));
        attractionsList.add(new ListItemModel("The TILT", "https://www.thetilt.com/"));
        attractionsList.add(new ListItemModel("Shedd Aquarium", "https://www.sheddaquarium.org/"));
        attractionsList.add(new ListItemModel("Millennium Park", "https://www.chicago.gov/city/en/depts/dca/supp_info/millennium_park.html"));
        attractionsList.add(new ListItemModel("Field Museum", "https://www.fieldmuseum.org/"));


        restaurantsList.add(new ListItemModel("Alinea", "https://www.exploretock.com/alinea/?utm_source=search&utm_medium=place-actions&utm_content=ordering#pickup"));
        restaurantsList.add(new ListItemModel("BamBooQue", "https://www.bambooque.com/"));
        restaurantsList.add(new ListItemModel("GooseFoot", "https://www.goosefoot.net/menu"));//menu
        restaurantsList.add(new ListItemModel("Ever Restaurant", "https://www.exploretock.com/ever/?utm_source=search&utm_medium=place-actions&utm_content=reservations"));
        restaurantsList.add(new ListItemModel("Oriole", "https://www.exploretock.com/oriolechicago/?utm_source=search&utm_medium=place-actions&utm_content=reservations"));
        restaurantsList.add(new ListItemModel("Tanta Chicago", "https://www.tantachicago.com/"));
    }

    /*private void storeUrl(String url){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireContext());
        sp.edit().putString("URL", url).apply();
    }

    private String getUrl(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(requireContext());
        return sp.getString("URL","");
    }*/

}