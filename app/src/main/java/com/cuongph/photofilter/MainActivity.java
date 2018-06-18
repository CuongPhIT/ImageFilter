package com.cuongph.photofilter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.cuongph.photofilter.imagefilter.FilterPack;
import com.cuongph.photofilter.imagefilter.imageprocessors.Filter;
import com.cuongph.photofilter.utils.ThumbnailItem;
import com.cuongph.photofilter.utils.ThumbnailsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @BindView(R.id.img_avatar)
    ImageView img_avatar;

    @BindView(R.id.rcv_filter)
    RecyclerView rcv_filer;

    private List<ThumbnailItem> thumbnailItems = new ArrayList<>();
    private FilterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        rcv_filer.setHasFixedSize(true);
        rcv_filer.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rcv_filer.setItemAnimator(new DefaultItemAnimator());

        final Bitmap mBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.test);

        img_avatar.setImageBitmap(mBitmap);


        prepareThumbnail(mBitmap);

        mAdapter = new FilterAdapter(thumbnailItems);
        rcv_filer.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new FilterAdapter.OnClickListener() {
            @Override
            public void onClick(Filter filter) {
                img_avatar.setImageBitmap(filter.processFilter(ImageUtils.resizeBitmap(40, mBitmap, MainActivity.this)));
            }
        });
    }

    public void prepareThumbnail(final Bitmap bitmap) {
        Runnable r = new Runnable() {
            public void run() {
                Bitmap thumbImage;

                thumbImage = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

                if (thumbImage == null)
                    return;

                ThumbnailsManager.clearThumbs();
                thumbnailItems.clear();

                // add normal bitmap first
                ThumbnailItem thumbnailItem = new ThumbnailItem();
                thumbnailItem.image = thumbImage;
                thumbnailItem.filterName = "nomal";
                ThumbnailsManager.addThumb(thumbnailItem);

                List<Filter> filters = FilterPack.getFilterPack(MainActivity.this);

                for (Filter filter : filters) {
                    ThumbnailItem tI = new ThumbnailItem();
                    tI.image = thumbImage;
                    tI.filter = filter;
                    tI.filterName = filter.getName();
                    ThumbnailsManager.addThumb(tI);
                }

                thumbnailItems.addAll(ThumbnailsManager.processThumbs(MainActivity.this));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();

                        Log.e("cuongph", thumbnailItems.size() + " size");
                    }
                });
            }
        };



        new Thread(r).start();
    }
}
