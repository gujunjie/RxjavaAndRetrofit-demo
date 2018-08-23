package com.example.abc.myapplication22;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("rigister")
    Observable<Register> register(@Query("key") String key,
                                  @Query("username") String username,
                                  @Query("password") String password);

    @GET("login")
    Observable<Login> login(@Query("key") String key,
                            @Query("username") String username,
                            @Query("password") String password);
}
