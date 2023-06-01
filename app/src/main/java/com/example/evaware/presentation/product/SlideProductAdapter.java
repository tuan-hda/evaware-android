package com.example.evaware.presentation.product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.evaware.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SlideProductAdapter extends PagerAdapter {
    private Context context;
    private List<String> slideList;

    public SlideProductAdapter(Context context, List<String> slideList) {
        this.context = context;
        this.slideList = slideList;
    }

    @Override
    public int getCount() {
        return slideList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View slideLayout = inflater.inflate(R.layout.item_product_slide, container, false);

        ImageView imageView = slideLayout.findViewById(R.id.img_product_slide);
        String url = slideList.get(position);
        Picasso.with(context)
                .load(url)
                .into(imageView);

        container.addView(slideLayout);
        return slideLayout;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}