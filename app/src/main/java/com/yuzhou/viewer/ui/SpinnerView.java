package com.yuzhou.viewer.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListPopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuzhou on 2015/08/05.
 */
public class SpinnerView extends EditText
{

    private static class OnClickPopupSpinnerListener implements OnClickListener
    {
        private final SpinnerView spinnerView;
        private final List<String> items;

        public OnClickPopupSpinnerListener(SpinnerView spinnerView, List<String> items)
        {
            this.spinnerView = spinnerView;
            this.items = new ArrayList<>(items);
        }

        @Override
        public void onClick(View v)
        {
            final ListPopupWindow lpw = new ListPopupWindow(spinnerView.getContext());
            lpw.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                {
                    String item = items.get(position);
                    spinnerView.setText(item);
                    lpw.dismiss();
                }
            });

            lpw.setAdapter(new ArrayAdapter<String>(spinnerView.getContext(), android.R.layout.simple_list_item_1, items));
            lpw.setAnchorView(v);
            lpw.setModal(true);
            lpw.show();
        }

    }

    public SpinnerView(Context context)
    {
        super(context);
    }

    public SpinnerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SpinnerView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    public SpinnerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setItems(List<String> items)
    {
        setFocusable(false);
        setOnClickListener(new OnClickPopupSpinnerListener(this, items));
    }

}
