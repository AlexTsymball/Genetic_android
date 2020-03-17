package com.example.genetic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public int [] first;
    public int [] second;
    public int [] third;
    public int [] fourth;
    public void onButtonClick(View v){
        EditText a = (EditText) findViewById(R.id.a);
        EditText b = (EditText) findViewById(R.id.b);
        EditText c = (EditText) findViewById(R.id.c);
        EditText d = (EditText) findViewById(R.id.d);
        EditText y = (EditText) findViewById(R.id.y);
        TextView res = (TextView)findViewById(R.id.resText);
        TextView time = (TextView)findViewById(R.id.t);

        int gran = (int) Integer.parseInt(y.getText().toString())/2;
        first = generate(gran);
        second = generate(gran);
        third = generate(gran);
        fourth = generate(gran);
        int [] cor = {Integer.parseInt(a.getText().toString()),Integer.parseInt(b.getText().toString()),
                Integer.parseInt(c.getText().toString()),Integer.parseInt(d.getText().toString())};
        long timeBefore = System.nanoTime();
        int [] result = find(cor, Integer.parseInt(y.getText().toString()));
        long timeAfter = System.nanoTime();

        time.setText(Double.toString((timeAfter-timeBefore)*Math.pow(10,-9)));
        res.setText(Arrays.toString(result));
    }

    private int[] find(int [] cor,  int y) {
        int f1 = 0;
        int f2 = 0;
        int f3 = 0;
        int f4 = 0;
        for (int i = 0; i < first.length; i++) {
            f1 += first[i]*cor[i];
            f2 += second[i]*cor[i];
            f3 += third[i]*cor[i];
            f4 += fourth[i]*cor[i];
        }
        double delta1 = Math.abs(y-f1);
        double delta2 = Math.abs(y-f2);
        double delta3 = Math.abs(y-f3);
        double delta4 = Math.abs(y-f4);

        if(delta1 == 0){
            return first;
        }
        if(delta2 == 0){
            return second;
        }
        if(delta3 == 0){
            return third;
        }
        if(delta4 == 0){
            return fourth;
        }

        double sumDelta = 1/delta1 + 1/delta2 + 1/delta3 + 1/delta4;

        int pros1 = (int)((1/delta1)/sumDelta * 100);
        int pros2 = (int)((1/delta2)/sumDelta * 100);
        int pros3 = (int)((1/delta3)/sumDelta * 100);
        int pros4 = (int)((1/delta4)/sumDelta * 100);

        int[] newfirst = fortuneWheel(pros1,pros2,pros3,pros4);
        int[] newsecond = fortuneWheel(pros1,pros2,pros3,pros4);
        int[] newthird = fortuneWheel(pros1,pros2,pros3,pros4);
        int[] newfourth = fortuneWheel(pros1,pros2,pros3,pros4);


       int tmp = newfirst[0];
       newfirst[0] = newsecond[0];
       newsecond[0] = tmp;

       tmp = newthird[newthird.length-1];
       newthird[newthird.length-1] = newfourth[newthird.length-1];
       newfourth[newthird.length-1] = tmp;

       int mut1 = (int)(Math.random()*(newthird.length-1));
       int mut2 = (int)(Math.random()*(newthird.length-1));
       if(mut1 == 0){
           newfirst[mut2] += 1;
       }
       if(mut1 == 1){
           newsecond[mut2] += 1;
       }
       if(mut1 == 2){
           newthird[mut2] += 1;
       }
       if(mut1 == 3){
           newfourth[mut2] += 1;
       }

       first = newfirst;
       second = newsecond;
       third = newthird;
       fourth = newfourth;

       int [] result = find(cor,y);
        return result;
    }

    private int[] fortuneWheel(int pros1, int pros2, int pros3, int pros4) {
        int number = (int)(Math.random()*100);
        if(number < pros1){
            return  first;
        }
        if(number < pros2 + pros1){
            return second;
        }
        if(number < pros2 + pros1+ pros3){
            return third;
        }
        if(number <= 100){
            return fourth;
        }
        return null;
    }

    private int[] generate(int gran) {
        int [] arr = new int[4];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int)(Math.random()*gran);
        }
        return arr;
    }
}
