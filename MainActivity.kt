package com.example.networkretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.networkretrofit.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class MainActivity : AppCompatActivity() {
    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //리사이클러뷰 관련
        val adapter = CustomAdapter()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //레트로핏
        val retrofit = Retrofit.Builder()//레트로핏 생성
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())//json 데이터를 Repository 클래스의 컬렉션으로 변환해주는 컨버터
            .build()

        binding.buttonRequest.setOnClickListener {
            val githubService = retrofit.create(GithubService::class.java)//인터페이스로 객체 생성...users() 안데 비동기 통신으로 데이터 가져오는 enqueue() 추가함.
            githubService.users().enqueue(object: Callback<Repository> {
                override fun onResponse(call: Call<Repository>, response: Response<Repository>) {
                    adapter.userList = response.body() as Repository//서버로부터 전송된 데이터를 꺼내 어댑터의 userList에 담는다
                    adapter.notifyDataSetChanged()//변경사항 반영
                }

                override fun onFailure(call: Call<Repository>, t: Throwable) {

                }

            })//enqueue 호출되면 통신 시작됨.
        }
    }
}
interface GithubService{//호출 방식, 주소, 데이터 등 지정
    @GET("users/Kotlin/repos")//요청 주소.. Github 도메인은 제외
    fun users(): Call<Repository>//Github API 호출..
}