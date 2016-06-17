package net.colindodd.giphytest.services;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import rx.schedulers.Schedulers;

public class GiphyService {

    private static final String BASE_URL = "http://api.giphy.com/";
    private final Retrofit retrofit;
    private final GiphyInterface giphyInterface;
    private final RxJavaCallAdapterFactory rxAdapter;

    public GiphyService() {
        this.rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(this.rxAdapter)
                .build();
        this.giphyInterface = this.retrofit.create(GiphyInterface.class);

    }

    public GiphyInterface getApi() {
        return this.giphyInterface;
    }
}
