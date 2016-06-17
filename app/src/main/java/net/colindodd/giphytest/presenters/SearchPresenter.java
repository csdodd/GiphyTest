package net.colindodd.giphytest.presenters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import net.colindodd.giphytest.model.GiphyResponse;
import net.colindodd.giphytest.services.GiphyInterface;
import net.colindodd.giphytest.view.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class SearchPresenter {

    private final MainActivity activity;
    private final GiphyInterface giphyInterface;

    public SearchPresenter(final MainActivity activity, final GiphyInterface giphyInterface) {
        this.activity = activity;
        this.giphyInterface = giphyInterface;
    }

    public void searchGifs(final String query) {
        final Observable<GiphyResponse> call = giphyInterface.search(query);
        call.observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<GiphyResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                // cast to retrofit.HttpException to get the response code
                if (e instanceof HttpException) {
                    HttpException response = (HttpException)e;
                    int code = response.code();
                    Log.e("GiphyService", "onError. " + code);
                }
            }

            @Override
            public void onNext(final GiphyResponse response) {
                activity.displayGifs(response.data);
            }
        });
    }

    public TextWatcher getTextChangedListener() {
        return textListener;
    }

    private final TextWatcher textListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        private Timer timer=new Timer();
        private final long DELAY = 500; // milliseconds

        @Override
        public void afterTextChanged(final Editable s) {
            timer.cancel();
            timer = new Timer();
            timer.schedule(
                    new TimerTask() {
                        @Override
                        public void run() {
                            searchGifs(s.toString());
                        }
                    },
                    DELAY
            );
        }
    };
}
