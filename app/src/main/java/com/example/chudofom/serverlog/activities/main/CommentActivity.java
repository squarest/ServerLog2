package com.example.chudofom.serverlog.activities.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import com.example.chudofom.serverlog.R;

public class CommentActivity extends AppCompatActivity {
    EditText comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        createToolbar();
        comment = (EditText) findViewById(R.id.edit_comment);
        comment.setText(getIntent().getExtras().getString("comment"));
    }

    private void createToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.comment_toolbar);
        toolbar.setTitle("Отчет");
        toolbar.inflateMenu(R.menu.comment_menu);
        toolbar.setOnMenuItemClickListener(item ->
        {
            doneComment();
            return false;
        });
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.setTitleTextColor(Color.WHITE);
    }

    private void doneComment() {
        Intent intent = new Intent();
        intent.putExtra("comment", comment.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
