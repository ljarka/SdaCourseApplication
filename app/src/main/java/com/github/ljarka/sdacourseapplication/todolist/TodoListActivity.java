package com.github.ljarka.sdacourseapplication.todolist;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.view.ActionMode.Callback;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.github.ljarka.sdacourseapplication.R;

import java.util.ArrayList;
import java.util.List;

public class TodoListActivity extends AppCompatActivity implements OnItemCheckStateChanged {

    private static final String ADAPTER_DATA = "adapter_data";
    private TodoListAdapter todoListAdapter;
    private String acitivityTitle;
    private ActionMode actionMode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_activity);
        acitivityTitle = getSupportActionBar().getTitle().toString();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        todoListAdapter = new TodoListAdapter();
        recyclerView.setAdapter(todoListAdapter);
        todoListAdapter.setCheckListener(this);

        final EditText editText = (EditText) findViewById(R.id.todo_edit_text);

        Button addButton = (Button) findViewById(R.id.add_button);
        addButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                todoListAdapter.addItem(editText.getText().toString());
                editText.setText("");
            }
        });
    }

    @Override
    public void onItemCheckStateChanged(int checkedItemsCount) {
        if (checkedItemsCount > 0) {
            if (actionMode == null) {
                createActionMode();
            }
            actionMode.setTitle(getResources().getQuantityString(R.plurals.checked_items_plural, checkedItemsCount,
                    checkedItemsCount));

        } else {
            if (actionMode != null) {
                actionMode.finish();
            }
            getSupportActionBar().setTitle(acitivityTitle);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(ADAPTER_DATA, new ArrayList<>(todoListAdapter.getItems()));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        todoListAdapter.setItems(savedInstanceState.<TodoListItem>getParcelableArrayList(ADAPTER_DATA));
    }

    private void createActionMode() {
        actionMode = startSupportActionMode(new Callback() {

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.todo_list_action_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.action_delete) {
                    todoListAdapter.deleteAllCheckedItems();
                    return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                todoListAdapter.deselectAllItems();
                actionMode = null;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
