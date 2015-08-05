package com.yuzhou.viewer.ui;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhou on 2015/08/04.
 */
public class OnClickListPopupTextViewListener implements View.OnClickListener
{
    private final EditText editText;
    private final List<String> items;

    public OnClickListPopupTextViewListener(EditText editText, List<String> items)
    {
        this.editText = editText;
        this.items = new ArrayList<>();
        this.items.addAll(items);
    }

    @Override
    public void onClick(View v)
    {
        final ListPopupWindow lpw = new ListPopupWindow(editText.getContext());
        lpw.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String item = items.get(position);
                editText.setText(item);
                lpw.dismiss();
            }
        });

        lpw.setAdapter(new ArrayAdapter<String>(editText.getContext(), android.R.layout.simple_list_item_1, items));
        lpw.setAnchorView(v);
        lpw.setModal(true);
        lpw.show();
    }

}
