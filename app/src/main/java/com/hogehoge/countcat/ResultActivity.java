package com.hogehoge.countcat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class ResultActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //activityResultを呼ぶように変える
        setContentView(R.layout.activity_result);
        //1.backBtnを呼び出す
        Button backBtn = (Button) findViewById(R.id.backBtn);
        //2.ボタンをクリックした時のイベントを作成する
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ボタンを押すと画面を消してスタートに戻れるようにする
                finish();
            }
        });

        //テキストを呼び出す
        TextView resultText = (TextView)findViewById(R.id.resultText);

        //渡されたintentを読み込む
        Intent intent = getIntent();
        //intentからresultを読み込む、何もなかった場合はfalseになる
        boolean result = intent.getBooleanExtra("result",false);
        if(result){
            //正しかった場合
            resultText.setText("正解");
        }else{
            //間違っていた場合
            resultText.setText("残念");
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
