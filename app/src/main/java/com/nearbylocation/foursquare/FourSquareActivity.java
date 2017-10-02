package com.nearbylocation.foursquare;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.nearbylocation.app.App;
import com.nearbylocation.R;
import com.nearbylocation.databinding.ActivityFourSquareBinding;
import com.nearbylocation.databinding.ActivityGooglePlacesBinding;
import com.nearbylocation.foursquare.adapter.FourSquareAdapter;
import com.nearbylocation.repository.remote.Repository;
import com.nearbylocation.repository.remote.model.foursquare.Venue;

import java.util.List;
import javax.inject.Inject;

public class FourSquareActivity extends AppCompatActivity implements FourSquareActivityContract.View {

    @Inject
    Repository repository;
    FourSquareActivityPresenter presenter;

    ActivityFourSquareBinding binding;
    List<Venue> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_four_square);
        setSupportActionBar(binding.toolbarFourSquare);

        ((App)getApplication()).getAppComponent().inject(this);

        binding.recyclerViewFourSquare.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        binding.recyclerViewFourSquare.setLayoutManager(layoutManager);

        FourSquareActivityContract.View fourSquareActivityView = this;
        presenter = new FourSquareActivityPresenter(fourSquareActivityView, repository);
        presenter.loadLocation();
    }


    @Override
    public void displayLocation(List<Venue> venues) {
        binding.progressBarFourSquare.setVisibility(View.GONE);
        items = venues;
        RecyclerView.Adapter mAdapter = new FourSquareAdapter(items);
        binding.recyclerViewFourSquare.setAdapter(mAdapter);
    }

    @Override
    public void displayInternetError() {
        binding.progressBarFourSquare.setVisibility(View.GONE);
        Toast.makeText(this, R.string.network_not_available, Toast.LENGTH_SHORT).show();
    }

    //______________________________________________________________________________________________ LIFECYCLE
    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unsubscribe();
    }
}
