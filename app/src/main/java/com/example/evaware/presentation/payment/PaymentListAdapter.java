package com.example.evaware.presentation.payment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.AddressModel;
import com.example.evaware.data.model.PaymentMethodModel;
import com.example.evaware.databinding.PaymentMethodItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.ViewHolder> {
    private final Activity context;
    private List<PaymentMethodModel> paymentMethods;
    private int selectedIndex = 0;
    private boolean isPlain = false;
    private static final String TAG = "PaymentListAdapter";
    private NewCardBottomSheetFragment bottomSheetFragment;
    private FragmentManager fragmentManager;

    public PaymentListAdapter(Activity context) {
        this.context = context;
    }

    public void setPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public PaymentListAdapter(Activity context, List<PaymentMethodModel> paymentMethods) {
        this.context = context;
        this.paymentMethods = paymentMethods;
    }

    public PaymentListAdapter(Activity context, List<PaymentMethodModel> paymentMethods, boolean isPlain, NewCardBottomSheetFragment bottomSheetFragment, FragmentManager fragmentManager) {
        this.context = context;
        this.paymentMethods = paymentMethods;
        this.isPlain = isPlain;
        this.bottomSheetFragment = bottomSheetFragment;
        this.fragmentManager = fragmentManager;
    }
    public PaymentMethodModel getCurrentSelect() {
        return paymentMethods.get(selectedIndex);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PaymentMethodItemBinding binding = PaymentMethodItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PaymentMethodModel item = paymentMethods.get(position);
        holder.binding.textCard.setText(item.account_no.substring(12, 16) + " " + item.provider);
        Picasso.with(context).load(item.logo).into(holder.binding.logo);
        holder.binding.textExp.setText(item.exp);
        if (isPlain) {
            holder.binding.indicator.setVisibility(View.GONE);
            holder.binding.getRoot().setOnClickListener(view -> {
                Bundle args = new Bundle();
                args.putSerializable("data", item);
                bottomSheetFragment.setArguments(args);
                bottomSheetFragment.show(fragmentManager, "NewCardBottomSheetFragment");
            });
        } else {
            holder.binding.getRoot().setOnClickListener(view -> {
                int oldIndex = selectedIndex;
                selectedIndex = holder.getAdapterPosition();
                notifyItemChanged(oldIndex);
                notifyItemChanged(selectedIndex);
            });
            if (selectedIndex == position) {
                holder.binding.indicator.setBackgroundResource(R.drawable.select_indicator);
            } else {
                holder.binding.indicator.setBackgroundResource(R.drawable.unselect_indicator);
            }
        }
    }

    @Override
    public int getItemCount() {
        return paymentMethods.size();
    }


//    @Override
//    public View getView(int i, View view, ViewGroup viewGroup) {
//        ViewHolder viewHolder = null;
//
//        if (view == null) {
//            viewHolder = new ViewHolder();
//            LayoutInflater inflater = context.getLayoutInflater();
//            view = inflater.inflate(R.layout.payment_method_item, viewGroup, false);
//
//            viewHolder.logo = view.findViewById(R.id.logo);
//            viewHolder.textCard = view.findViewById(R.id.text_card);
//            viewHolder.textExp = view.findViewById(R.id.text_exp);
//            viewHolder.indicator = view.findViewById(R.id.indicator);
//            view.setTag(viewHolder);
//        } else {
//            viewHolder = (ViewHolder) view.getTag();
//        }
//
//        PaymentMethodModel paymentMethod = paymentMethods.get(i);
//        Picasso.with(context).load(paymentMethod.logo).into(viewHolder.logo);
//        if (paymentMethod.isCard) {
//            viewHolder.textCard.setText(paymentMethod.provider + " " + paymentMethod.accountNo);
//            viewHolder.textExp.setText(paymentMethod.exp);
//        } else {
//            viewHolder.textCard.setText(paymentMethod.provider);
//            viewHolder.textExp.setVisibility(View.GONE);
//        }
//
//        if (selectedIndex == i) {
//            viewHolder.indicator.setBackgroundResource(R.drawable.select_indicator);
//        } else {
//            viewHolder.indicator.setBackgroundResource(R.drawable.unselect_indicator);
//        }
//
//        view.setOnClickListener(view1 -> {
//            selectedIndex = i;
//            notifyDataSetChanged();
//        });
//
//        return view;
//    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        PaymentMethodItemBinding binding;

        public ViewHolder(@NonNull PaymentMethodItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
