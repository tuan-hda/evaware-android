package com.example.evaware.presentation.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.evaware.R;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    Context context;
    List<Category> caterogyList;
    LayoutInflater inflater;

    public CategoryAdapter(Context context, List<Category> caterogyList) {
        this.context = context;
        this.caterogyList = caterogyList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return caterogyList.size();
    }

    @Override
    public Object getItem(int i) {
        return caterogyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Category item = caterogyList.get(i);
        view = inflater.inflate(R.layout.item_category, null);
        //Get element
//        TextView name = (TextView) view.findViewById(R.id.itCategory_tv_name);
//        TextView selected = (TextView) view.findViewById(R.id.itCategory_tv_selected);
//        //Set value
//        name.setText(item.getName());
//        selected.setText((item.getSelected()));

        return view;
    }
}
