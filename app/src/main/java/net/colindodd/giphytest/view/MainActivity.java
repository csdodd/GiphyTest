package net.colindodd.giphytest.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import net.colindodd.giphytest.R;
import net.colindodd.giphytest.model.GiphyData;
import net.colindodd.giphytest.presenters.SearchPresenter;
import net.colindodd.giphytest.services.GiphyInterface;
import net.colindodd.giphytest.services.GiphyService;
import net.colindodd.giphytest.view.adapters.GiphyAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SearchPresenter presenter;
    private RecyclerView recyclerView;
    private GiphyAdapter giphyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loadImages();
    }

    private void init() {
        initGiphyService();
        initRecyclerView();
        initInput();
    }

    private void initInput() {
        final EditText input = (EditText) findViewById(R.id.main_activity__input);
        input.addTextChangedListener(presenter.getTextChangedListener());
    }

    private void initGiphyService() {
        final GiphyService giphyService = new GiphyService();
        final GiphyInterface giphyInterface = giphyService.getApi();
        this.presenter = new SearchPresenter(this, giphyInterface);
    }

    private void initRecyclerView() {
        this.recyclerView = (RecyclerView) findViewById(R.id.main_activty__recycler_view);
        this.recyclerView.setHasFixedSize(true);

        final RecyclerView.LayoutManager recyclerLayoutManager = new GridLayoutManager(this, GiphyAdapter.NUM_ROWS);
        this.recyclerView.setLayoutManager(recyclerLayoutManager);

        this.giphyAdapter = new GiphyAdapter(this);
        this.recyclerView.setAdapter(this.giphyAdapter);
    }

    private void loadImages() {
        this.presenter.searchGifs("make it rain");
    }

    public void displayGifs(final List<GiphyData> gifs) {
        this.giphyAdapter.setGifs(gifs);
    }
}
