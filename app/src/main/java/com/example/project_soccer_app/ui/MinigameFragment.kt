package com.example.project_soccer_app.ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.project_soccer_app.databinding.FragmentMinigameBinding
import kotlinx.coroutines.*
import java.lang.Math.abs
import java.lang.Runnable
import kotlin.math.pow

class MinigameFragment: Fragment() {
    lateinit var fragmentMinigameBinding: FragmentMinigameBinding
    var handler: Handler = Handler(Looper.getMainLooper())
    var runnable:Runnable = Runnable{}
    var job = Job()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentMinigameBinding = FragmentMinigameBinding.inflate(layoutInflater)

        var lastX = 0f
        var lastY = 0f

        CoroutineScope(Dispatchers.Main + job).launch{ // job이 cancel되면  이 coroutine도 종료
            var initBallX = fragmentMinigameBinding.ball.getX()
            var initBallY = fragmentMinigameBinding.ball.getY()

            while(true){
                var randomGoX = (( Math.random() * 500).toInt() - 250).toFloat()
                var resultPosiX = fragmentMinigameBinding.minigameGoolkeeper.getX() + randomGoX
                var distanceX = fragmentMinigameBinding.minigameGoolkeeper.getX() - fragmentMinigameBinding.ball.getX()
                var distanceY = fragmentMinigameBinding.minigameGoolkeeper.getY() - fragmentMinigameBinding.ball.getY()

                Log.d("distanceX", distanceX.toString())
                Log.d("distanceY", distanceY.toString())
                if(abs(distanceY) < 200 && abs(distanceX) < 200){
                    imageMove(fragmentMinigameBinding.minigameGoolkeeper, -distanceX, -distanceY, 100L)
                }
                else if(resultPosiX > 100 && resultPosiX < 700){
                    imageMove(fragmentMinigameBinding.minigameGoolkeeper, randomGoX, 0f, 500L)
                }
                else{
                    imageMove(fragmentMinigameBinding.minigameGoolkeeper, 0f, 0f, 500L)
                }

                // 원래 상태로 되돌림
                if(fragmentMinigameBinding.ball.getX() != initBallX ||
                    fragmentMinigameBinding.ball.getY() != initBallY){
                    delay(500)
                    fragmentMinigameBinding.ball.setX(initBallX)
                    fragmentMinigameBinding.ball.setY(initBallY)
                }

                delay(500)
            }
        }

        CoroutineScope(Dispatchers.Main + job).launch{ // job이 cancel되면  이 coroutine도 종료

            while(true){
                var distanceX = fragmentMinigameBinding.minigameGoolkeeper.getX() - fragmentMinigameBinding.ball.getX()
                var distanceY = fragmentMinigameBinding.minigameGoolkeeper.getY() - fragmentMinigameBinding.ball.getY()
                var ballX = fragmentMinigameBinding.ball.getX()
                var ballY = fragmentMinigameBinding.ball.getY()

                var distance = distanceX.pow(2) + distanceY.pow(2)
                if(distance < 1000){
                    Log.d("save", "save")
                }
                else if(ballX > 100 && ballX < 700
                    && ballY < 550 && ballY > 230){
                    Log.d("goal", ballY.toString())
                }

                delay(10)
            }
        }

        fragmentMinigameBinding.ball.setOnTouchListener{
            v, event ->
                when(event.action){
                    MotionEvent.ACTION_DOWN ->{
                        lastX = event.rawX
                        lastY = event.rawY
                    }

                    MotionEvent.ACTION_UP ->{
                        var goX = (lastX - event.rawX) *2
                        var goY = (lastY - event.rawY) *2
                        imageMove(fragmentMinigameBinding.ball, goX, goY, 500L) // ~ 만큼 이동
                    }
                }
            true
        }

        val view = fragmentMinigameBinding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
    }

    // onPause 사용할 경우 안드로이드 홈화면 갔다 돌아오면 coroutine 작동하지 않음
    override fun onDestroy() {
        job.cancel() // fragment가 destroy될 때 coroutine 종료
        super.onDestroy()
    }

    fun imageMove(imageView: ImageView, goX:Float, goY:Float, duration1:Long){
        runnable = object:Runnable{
            override fun run() {
                ObjectAnimator.ofFloat(imageView, "translationX", goX).apply{
                    duration = duration1
                    start()
                }
                ObjectAnimator.ofFloat(imageView, "translationY", goY).apply{
                    duration = duration1
                    start()
                }
                handler.postDelayed(runnable, duration1) // duration1 시간 이후에 runnable객체를 mainThread의 messageQueue에 전달한다
            }
        }
        handler.post(runnable) // runnable객체를 mainThread의 messageQueue에 전달한다
    }
}