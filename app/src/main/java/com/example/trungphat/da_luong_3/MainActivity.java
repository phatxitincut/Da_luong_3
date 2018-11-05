package com.example.trungphat.da_luong_3;

import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ProgressBar myBar;
    TextView tv_display;
    EditText edit_nhap;
    Button btn_dosomething;

    int accum=0;
    long startingMills = System.currentTimeMillis(); //lay tg từ hệ thống
    String dulieuhienthi;

    Handler myhanler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBar=findViewById(R.id.myBar);
        myBar.setMax(100);
        tv_display=(TextView) findViewById(R.id.tv_display);
        edit_nhap=(EditText) findViewById(R.id.editText);
        btn_dosomething=(Button) findViewById(R.id.button);

        btn_dosomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String txt = edit_nhap.getText().toString();
                Toast.makeText(getApplicationContext(),"You typed:" + txt, Toast.LENGTH_SHORT).show();

            }
        });

    }

    protected void onStart(){
        super.onStart();
        myBar.incrementProgressBy(0);
        //Tạo luong nền để xử lý công việc
        Thread myThread1=new Thread(new Runnable() {
            @Override
            public void run() {
                //Các lệnh xử lý background thread đặt ở đây
                try {
                    for(int i=0; i<50; i++)
                    {
                        Thread.sleep(1000);//mô phỏng 1s bận rộn của activity
                        myhanler.post(foreroundTask);
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        myThread1.start();
    }

    private Runnable foreroundTask= new Runnable() {
        @Override
        public void run() {
            try{
                int progressStep = 2;
                tv_display.setText("Một vài dữ liệu quan trọng đang được thu thập. Vui lòng chờ..."+"\n Số giây còn lại  " + (50-(System.currentTimeMillis()-startingMills)/1000));
                myBar.incrementProgressBy(progressStep);
                accum+=progressStep;
                if (accum>=myBar.getMax()){
                    tv_display.setText("Background đã xử lý xong");
                    myBar.setVisibility(View.INVISIBLE);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    };
}
