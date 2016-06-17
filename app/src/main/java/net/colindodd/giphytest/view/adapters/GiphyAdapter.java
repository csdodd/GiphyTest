package net.colindodd.giphytest.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import net.colindodd.giphytest.R;
import net.colindodd.giphytest.model.GiphyData;

import java.util.ArrayList;
import java.util.List;

public class GiphyAdapter extends RecyclerView.Adapter<GiphyAdapter.ViewHolder> {

    public static final int NUM_ROWS = 2;
    private final Context context;
    private List<GiphyData> gifs;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView single_item__gif;

        public ViewHolder(View v) {
            super(v);
            single_item__gif = (ImageView) v.findViewById(R.id.single_item__gif);
        }
    }

    public GiphyAdapter(final Context context) {
        this.context = context;
        this.gifs = new ArrayList<>(0);
    }

    public void setGifs(final List<GiphyData> gifs) {
        this.gifs = gifs;
        notifyDataSetChanged();
    }

    @Override
    public GiphyAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent,
                                                      final int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 final int position) {
        final GiphyData giphyData = this.gifs.get(position);
        final String url = giphyData.images.fixed_width.url;
        final ImageView imageView = holder.single_item__gif;
        final GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        Glide.with(context)
                .load(url)
                .into(imageViewTarget);
    }

    @Override
    public int getItemCount() {
        final int overflow = this.gifs.size() % NUM_ROWS;
        return this.gifs.size() - overflow;
    }

}