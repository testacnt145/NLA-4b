package com.nearbylocation.googleplaces;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nearbylocation.app.App;
import com.nearbylocation.R;
import com.nearbylocation.databinding.ActivityGooglePlacesBinding;
import com.nearbylocation.databinding.ActivityMainBinding;
import com.nearbylocation.googleplaces.adapter.GooglePlacesAdapter;
import com.nearbylocation.repository.remote.Repository;
import com.nearbylocation.repository.remote.model.foursquare.Venue;
import com.nearbylocation.repository.remote.model.googleplaces.Result;

import java.util.List;
import javax.inject.Inject;

public class GooglePlacesActivity extends AppCompatActivity implements GooglePlacesActivityContract.View {

    @Inject
    Repository repository;
    GooglePlacesActivityPresenter presenter;

    ActivityGooglePlacesBinding binding;
    List<Result> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_google_places);
        setSupportActionBar(binding.toolbarGooglePlaces);

        ((App)getApplication()).getAppComponent().inject(this);

        binding.recyclerViewGooglePlaces.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewGooglePlaces.setLayoutManager(layoutManager);

        GooglePlacesActivityContract.View googlePlacesActivityView = this;
        presenter = new GooglePlacesActivityPresenter(googlePlacesActivityView, repository);
        presenter.loadLocation();
    }

    @Override
    public void displayLocation(List<Result> venues) {
        binding.progressBarGooglePlaces.setVisibility(View.GONE);
        items = venues;
        RecyclerView.Adapter mAdapter = new GooglePlacesAdapter(items);
        binding.recyclerViewGooglePlaces.setAdapter(mAdapter);
    }

    @Override
    public void displayInternetError() {
        binding.progressBarGooglePlaces.setVisibility(View.GONE);
        Toast.makeText(this, R.string.network_not_available, Toast.LENGTH_SHORT).show();
    }

    //______________________________________________________________________________________________ LIFECYCLE
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }

    @BindingAdapter("android:src")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext()).
                load(url).
                placeholder(R.mipmap.ic_launcher).
                into(view);
    }
}
