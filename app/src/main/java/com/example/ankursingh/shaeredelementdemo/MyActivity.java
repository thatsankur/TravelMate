package com.example.ankursingh.shaeredelementdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.ChangeBounds;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ankursingh.shaeredelementdemo.R;
import com.example.ankursingh.shaeredelementdemo.model.PlaceApiResponse;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MyActivity extends Activity {
    public static void loadImage(String url,ImageView imageView,Context ctx) {
        //loadImageGlide(ctx,url,imageView,android.R.drawable.btn_minus);
        Glide.with(ctx)
                .load(url)
                //.diskCacheStrategy(DiskCacheStrategy.RESULT)
                .placeholder(android.R.drawable.btn_minus)
                .into(imageView);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.main_container, new PlaceholderFragment())
                    .commit();
        }
        try {
            String key ="AIzaSyCnCh9YHTgrJRGFSSgLNU96YvT31kaBukA";
            post("https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJrTLr-GyuEmsRBfy61i59si0&key="+key,
                    "", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Log.d("RES", new Gson().<String>fromJson(response.body().string(), PlaceApiResponse.class));
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
        Context ctx;
        public ImageAdapter(Context pContext){
            this();
            ctx = pContext;
        }
        public interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        private OnItemClickListener mItemClickListener;

        List<String> items = new ArrayList<>();
        public void setOnItemClickListener(OnItemClickListener listener) {
            this.mItemClickListener = listener;
        }

        public List<String> getItemList(){
            return items;
        }
        private  ImageAdapter() {
            items.add("http://d3f85kxh5sxy27.cloudfront.net/56ae3c4e0b04512cde6c263a.jpg");
            items.add("https://www.planwallpaper.com/static/images/Winter-Tiger-Wild-Cat-Images.jpg");
            items.add("https://static.pexels.com/photos/1848/nature-sunny-red-flowers.jpg");
            items.add("https://static.pexels.com/photos/909/flowers-garden-colorful-colourful.jpg");
            items.add("http://www.gettyimages.ca/gi-resources/images/Homepage/Hero/UK/CMS_Creative_164657191_Kingfisher.jpg");
            items.add("https://www.planwallpaper.com/static/images/Frozen-Logo-Symbol-HD-Images.jpg");
            items.add("https://www.planwallpaper.com/static/images/magic-of-blue-universe-images.jpg");
            items.add("http://iwhatsappstatus.in/wp-content/uploads/2015/12/happy-new-year-2016-hd-images.jpg");
            items.add("http://i.huffpost.com/gen/3848572/images/n-SOUTH-INDIA-IMAGE-FROM-SPACE-large570.jpg");
            items.add("http://i164.photobucket.com/albums/u8/hemi1hemi/COLOR/COL9-6.jpg");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int i) {
            loadImage(items.get(i),viewHolder.blue,ctx);
            viewHolder.container.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, i);
                    }
                }
            });
            /*viewHolder.blue.setTransitionName("testBlue" + i);
            viewHolder.orange.setTransitionName("testOrange" + i);*/
        }



        @Override
        public int getItemCount() {
            return items.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View orange;
            public ImageView blue;
            public View container;

            public ViewHolder(View itemView) {
                super(itemView);
                blue = (ImageView)itemView.findViewById(R.id.blue_bar);
                orange = itemView.findViewById(R.id.orange_bar);
                container = itemView.findViewById(R.id.container);
            }
        }
    }
    /**
     * A placeholder fragment containing a simple view.
     */
    @SuppressLint("ValidFragment")
    public class PlaceholderFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);

            RecyclerView list = (RecyclerView) rootView.findViewById(R.id.streams_list);
            list.setLayoutManager(new LinearLayoutManager(getActivity()));
            final ImageAdapter adapter = new ImageAdapter(getActivity());

            adapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {


                    View orange = view.findViewById(R.id.orange_bar);
                    View blue = view.findViewById(R.id.blue_bar);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    Pair<View, String> p1 = Pair.create(blue, "blue_bar");
                    Pair<View, String> p2 = Pair.create(orange, "orange_bar");
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(getActivity(), p1, p2);
                    intent.putExtra("url",adapter.getItemList().get(position));
                    startActivity(intent, options.toBundle());
                }
            });
            list.setAdapter(adapter);

            return rootView;
        }
    }
    public static void loadImageGlide(Context pContext, String url, ImageView imageView, int defaultBackground) {
        Picasso.with(pContext)
                .load(url)
                .placeholder(defaultBackground)
                .into(imageView);
    }



    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    Call post(String url, String json, Callback callback) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }
}
