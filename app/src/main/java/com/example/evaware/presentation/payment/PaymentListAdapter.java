package com.example.evaware.presentation.payment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.evaware.R;
import com.example.evaware.data.model.PaymentMethodModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentListAdapter extends BaseAdapter {
    private final Activity context;
    private final List<PaymentMethodModel> paymentMethods;
    private int selectedIndex = 0;

    public PaymentListAdapter(Activity context, List<PaymentMethodModel> paymentMethods) {
        this.context = context;
        this.paymentMethods = paymentMethods;
    }

    @Override
    public int getCount() {
        return paymentMethods.size();
    }

    @Override
    public Object getItem(int i) {
        return paymentMethods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.payment_method_item, viewGroup, false);

            viewHolder.logo = view.findViewById(R.id.logo);
            viewHolder.textCard = view.findViewById(R.id.text_card);
            viewHolder.textExp = view.findViewById(R.id.text_exp);
            viewHolder.indicator = view.findViewById(R.id.indicator);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        PaymentMethodModel paymentMethod = paymentMethods.get(i);
        Picasso.with(context).load(paymentMethod.logo).into(viewHolder.logo);
        if (paymentMethod.isCard) {
            viewHolder.textCard.setText(paymentMethod.provider + " " + paymentMethod.accountNo);
            viewHolder.textExp.setText(paymentMethod.exp);
        } else {
            viewHolder.textCard.setText(paymentMethod.provider);
            viewHolder.textExp.setVisibility(View.GONE);
        }

        if (selectedIndex == i) {
            viewHolder.indicator.setBackgroundResource(R.drawable.select_indicator);
        } else {
            viewHolder.indicator.setBackgroundResource(R.drawable.unselect_indicator);
        }

        view.setOnClickListener(view1 -> {
            selectedIndex = i;
            notifyDataSetChanged();
        });

        return view;
    }

    static class ViewHolder {
        ImageView logo;
        TextView textCard, textExp;
        CardView indicator;
    }
}
