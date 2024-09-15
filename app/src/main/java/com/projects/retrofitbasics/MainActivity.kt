 package com.projects.retrofitbasics

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.projects.retrofitbasics.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //for logging creating okhttp client
        val okHttpClientBuilder = OkHttpClient.Builder()

        //adding logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

//        if (BuildConfig.DEBUG) {
//            //code
//        }

        okHttpClientBuilder.addInterceptor(loggingInterceptor)

        //retrofit object
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClientBuilder.build())
            .build()

        val gitHubClient = retrofit.create(GitHubClient::class.java)
        val call = gitHubClient.reposForUser("XYZ")

        call.enqueue(object:Callback<List<GitHubRepo>>{
            override fun onResponse(p0: Call<List<GitHubRepo>>, p1: Response<List<GitHubRepo>>) {
                val res = p1.body()
                Log.i("list",res.toString())
                binding.rvList.layoutManager = LinearLayoutManager(this@MainActivity)
                if(res!=null) {
                    val adapter = GitHubAdapter(res)
                    binding.rvList.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<List<GitHubRepo>>, p1: Throwable) {
                Toast.makeText(this@MainActivity,"Error",Toast.LENGTH_SHORT).show()
            }

        })
    }
}