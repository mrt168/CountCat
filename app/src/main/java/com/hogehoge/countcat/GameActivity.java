package com.hogehoge.countcat;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

import org.w3c.dom.Text;

import java.util.logging.LogRecord;

/**
 * Created by kokies on 2015/06/03.
 */
public class GameActivity extends Activity {

    //counterのテキスト
    TextView counter;
    //カウント数
    int count;

    //猫の数
    int catNum;

    //timer用のHandler
    private Handler handler;
    //時間の秒数
    int time = 10;
    //タイマー用のテキスト
    TextView timerText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //カウントを0に
        count = 0;
        //counterのテキストをidで読み込む
        counter = (TextView)findViewById(R.id.count);
        //counterにテキストをセットする
        counter.setText(count+"匹");
        //プラスボタンを読み込む
        Button plusBtn = (Button)findViewById(R.id.plusBtn);
        //マイナスボタンを読み込む
        Button minusBtn = (Button)findViewById(R.id.minusBtn);
        //プラスボタンのクリックイベント
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //プラスボタンをおした時
                count++;
                counter.setText(count+"匹");
            }
        });
        //マイナスボタンのクリックイベント
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //マイナスボタンをおした時
                count--;
                counter.setText(count+"匹");
            }
        });

        //猫の数を決める
        //Randomクラスのインスタンス化
        Random rnd = new Random();

        catNum = rnd.nextInt(10)+10;

        for(int i= 0;i<catNum;i++) {
            //猫のイメージを追加する
            makeCatImage();
        }

        //タイマー用のテキストを読み込む
        timerText = (TextView)findViewById(R.id.timer);

        //タイマー用のHandler

        Timer timer = new Timer();
        handler = new Handler();


        timer.schedule( new TimerTask(){
            @Override
            public void run() {
                // handlerを通じて処理を送る
                handler.post(new Runnable() {
                    public void run() {

                        //実行間隔分をtimeから減らす
                        time -= 1;


                        //現在のTimeを表示する
                        timerText.setText("あと"+time+"秒");

                        //timeが0になった時
                        if(time==0)
                        {
                            //ResultAcitivityに画面遷移する
                            Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                            //Intentにカウント数が正解か不正解かの情報をくっつける
                            intent.putExtra("result",catNum==count);
                            startActivity(intent);
                            //もうゲーム画面は必要ないのでbackでもどれないように終了しておく
                            finish();
                        }
                    }
                });
            }
        },
        0,//何秒後に実行するか
        1000//何秒間隔で実行するか
        );
    }

    private void makeCatImage()
    {
        // ディスプレイから横幅を取得する
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        //ランダムな猫の位置X
        Random rnd = new Random();

        int ranX = -rnd.nextInt(200);

        int ranY = rnd.nextInt(size.y)+200;
        //猫を走らせるレイアウトを取得する
        FrameLayout gameLayout = (FrameLayout)findViewById(R.id.gameLayout);
        //猫のImageViewを作る
        ImageView cat = new ImageView(this);
        //猫の画像をdrawableから選択
        cat.setImageResource(R.drawable.cat);
        //猫の位置を指定
        cat.setX(ranX);
        cat.setY(ranY);
        //猫にアニメーションをセット
        animateTranslationX(cat,ranX);
        //猫の画像サイズを指定
        cat.setLayoutParams(new FrameLayout.LayoutParams(200,200));
        gameLayout.addView(cat);
    }

    // 猫を移動させる
    private void animateTranslationX( ImageView target, int ranX) {
        // ディスプレイから横幅を取得する
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);

        // 猫を画面端まで変化させます
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat( target, "translationX", ranX, size.x+300 );

        // 4秒かけて実行させます
        objectAnimator.setDuration( 4000 );

        // アニメーションを開始します
        objectAnimator.start();
    }
}
