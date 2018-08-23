package com.example.abc.myapplication22;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "text";
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_showmessage)
    TextView tvShowmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    public void rxjavaThread() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) {
                Log.d(TAG, Thread.currentThread().getName());
                e.onNext(1);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.d(TAG, "onNext: " + integer);
                        Log.d(TAG, Thread.currentThread().getName());
                    }
                });
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
              retrofitAndRxjavaWithRigister();
                break;
            case R.id.btn_login:
                retrofitAndRxjavaWithLogin();
                break;
        }
    }

    public void retrofitAndRxjavaWithRigister() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        UserService userService = retrofit.create(UserService.class);

        userService.register("2783fa3f54cfa", etUsername.getText().toString(), etPassword.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Register>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Register value) {
                            if(value.getRetCode().equals("200"))
                            {
                                Log.d(TAG, value.getRetCode());
                                Log.d(TAG, value.getMsg());
                                //Log.d(TAG, value.getUid());
                                Toast.makeText(MainActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            }

                        tvShowmessage.setText(value.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,"错误,注册失败",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public void retrofitAndRxjavaWithLogin()
    {
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://apicloud.mob.com/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        UserService userService=retrofit.create(UserService.class);

        userService.login("2783fa3f54cfa", etUsername.getText().toString(), etPassword.getText().toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Login value) {
                       if(value.getRetCode().equals("200"))
                       {
                           Log.d(TAG, "token="+value.getResult().getToken());
                           Log.d(TAG, "uid="+value.getResult().getUid());

                           Toast.makeText(MainActivity.this,"登陆成功",Toast.LENGTH_SHORT).show();
                       }
                        tvShowmessage.setText(value.getMsg());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this,"error :(",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
