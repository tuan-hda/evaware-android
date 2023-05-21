package com.example.evaware.presentation.address;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.evaware.R;
import com.example.evaware.data.model.AddressModel;
import com.example.evaware.databinding.DeliveryAddressItemBinding;
import com.example.evaware.utils.GlobalStore;

import java.util.List;

public class AddressListAdapter extends RecyclerView.Adapter<AddressListAdapter.ViewHolder> {
    private final Activity context;
    private final List<AddressModel> addresses;
    private int selectedIndex = 0;
    private boolean isPlain = false;
    private ActivityResultLauncher<Intent> launcher;
    private static final String TAG = "AddressListAdapter";

    public AddressListAdapter(Activity context, List<AddressModel> addresses) {
        this.context = context;
        this.addresses = addresses;
    }

    public AddressListAdapter(Activity context, List<AddressModel> addresses, boolean isPlain, ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.addresses = addresses;
        this.isPlain = isPlain;
        this.launcher = launcher;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DeliveryAddressItemBinding binding = DeliveryAddressItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        
        AddressModel address = addresses.get(position);
        Log.d(TAG, "onBindViewHolder: " + address.path);
        String addressStr = address.province + ", " + address.district + ", " + address.ward + "," +
                " " + address.street;
        String contactStr = address.name + ", " + address.phone;
        holder.binding.textCard.setText(addressStr);
        holder.binding.textContact.setText(contactStr);

        if (isPlain) {
            holder.binding.indicator.setVisibility(View.GONE);
        } else {
            if (selectedIndex == holder.getAdapterPosition()) {
                holder.binding.indicator.setBackgroundResource(R.drawable.select_indicator);
            } else {
                holder.binding.indicator.setBackgroundResource(R.drawable.unselect_indicator);
            }
        }

        holder.binding.getRoot().setOnClickListener(v -> {
            int old = selectedIndex;
            selectedIndex = holder.getAdapterPosition();

            if (isPlain) {
                Intent intent = new Intent(context, AddAddressActivity.class);
                intent.putExtra("type", "update");
                GlobalStore.addressModel = address;
                launcher.launch(intent);
            } else {
                notifyItemChanged(old);
                notifyItemChanged(selectedIndex);
            }
        });
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return addresses.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        DeliveryAddressItemBinding binding;

        public ViewHolder(@NonNull DeliveryAddressItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
