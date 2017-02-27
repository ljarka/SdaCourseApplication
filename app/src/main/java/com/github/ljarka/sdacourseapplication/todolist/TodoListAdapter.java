package com.github.ljarka.sdacourseapplication.todolist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.github.ljarka.sdacourseapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListAdapter.MyViewHolder> {

    private List<TodoListItem> items = new ArrayList<>();
    private OnItemCheckStateChanged checkListener;

    public void setCheckListener(OnItemCheckStateChanged checkListener) {
        this.checkListener = checkListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final TodoListItem listItem = items.get(position);
        holder.textView.setText(listItem.getText());
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(listItem.isChecked());
        holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listItem.setChecked(isChecked);
                if (checkListener != null) {
                    checkListener.onItemCheckStateChanged(getCheckedItemsCount());
                }
            }
        });
    }

    private int getCheckedItemsCount() {
        int count = 0;

        for (TodoListItem item : items) {
            if (item.isChecked()) {
                count++;
            }
        }

        return count;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(String item) {
        items.add(new TodoListItem(item));
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        CheckBox checkBox;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.item_text);
            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
        }

    }

    public void deselectAllItems() {
        for (TodoListItem item : items) {
            item.setChecked(false);
        }
        notifyDataSetChanged();
    }

    public void deleteAllCheckedItems() {
        List<TodoListItem> newListItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isChecked()) {
                newListItems.add(items.get(i));
            }
        }
        items = newListItems;
        notifyDataSetChanged();
        if (checkListener != null) {
            checkListener.onItemCheckStateChanged(getCheckedItemsCount());
        }
    }

    public List<TodoListItem> getItems() {
        return items;
    }

    public void setItems(List<TodoListItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
