package com.yuzhou.viewer.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.google.common.collect.Lists;
import com.yuzhou.viewer.R;
import com.yuzhou.viewer.service.GoogleApiParam;

public class AdvancedSearchActivity extends AppCompatActivity
{
    private GoogleApiParam searchPrefs;
    private SpinnerView etSize;
    private SpinnerView etColor;
    private SpinnerView etType;
    private EditText etSite;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advanced_search);

        etSize = (SpinnerView) findViewById(R.id.etAdvSize);
        etSize.setItems(Lists.newArrayList(new String[]{"small", "medium", "large", "extra-large"}));

        etColor = (SpinnerView) findViewById(R.id.etAdvColor);
        etColor.setItems(Lists.newArrayList(new String[]{"black", "blue", "brown", "gray", "green"}));

        etType = (SpinnerView) findViewById(R.id.etAdvType);
        etType.setItems(Lists.newArrayList(new String[]{"face", "photo", "clip art", "line art"}));

        etSite = (EditText) findViewById(R.id.etAdvSite);


        searchPrefs = (GoogleApiParam) getIntent().getParcelableExtra("prefs");
        etSize.setText(searchPrefs.getImgSize());
        etColor.setText(searchPrefs.getImgColor());
        etType.setText(searchPrefs.getImgType());
        etSite.setText(searchPrefs.getSiteFilter());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_advanced_search, menu);
        return true;
    }

    @Override
    public Intent getSupportParentActivityIntent()
    {
        finish();
        return null;
    }

    public void onClickSave(View view)
    {
        searchPrefs.setImgSize(etSize.getText().toString());
        searchPrefs.setImgColor(etColor.getText().toString());
        searchPrefs.setImgType(etType.getText().toString());
        searchPrefs.setSiteFilter(etSite.getText().toString());

        Intent intent = getIntent();
        intent.putExtra("new_prefs", searchPrefs);

        setResult(RESULT_OK, intent);
        finish();
    }

}
