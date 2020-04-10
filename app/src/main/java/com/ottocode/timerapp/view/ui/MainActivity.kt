package com.ottocode.timerapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ottocode.timerapp.R
import com.ottocode.timerapp.databinding.ActivityMainBinding
import com.ottocode.timerapp.viewmodel.TimerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

@Suppress("SpellCheckingInspection")
class MainActivity : AppCompatActivity() {

    private lateinit var mBind: ActivityMainBinding
    private lateinit var model: TimerViewModel


    // Milisaniye cinsinden başlama zamanı. Başlangıçta 0 olarak initialize ettik.
    private var mStartTimeInMillis: Long = 0

    // Geri sayım class'ını tanımladık.
    private lateinit var mCountDownTimer: CountDownTimer

    // Timer stop-start
    private var mTimerRunning: Boolean = false

    //Milisaniye cinsinden kalan süre : mTimeLeftInMillis
    //Başlangıç zamanımızı Kalan süreye eşitledik.
    private var mTimeLeftInMillis: Long = mStartTimeInMillis


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mBind.lifecycleOwner = this
        model = TimerViewModel()
        mBind.timeVM = model


        mBind.setTime.setOnClickListener {
            val inputHour = mBind.hourPicker.text.toString()        // Eğer Saat değeri girilirse.
            val inputMinute = mBind.minutePicker.text.toString()    // Eğer Dakika değeri girilirse.
            val inputSecond = mBind.secondPicker.text.toString()    // Eğer Saniye değeri girilirse.

            val millisInputHour   = inputHour.toLong() * 3600000         // Saat -> 3,600,000 ile milisaniye yaptık.
            val millisInputMinute = inputMinute.toLong() * 60000      // Dakika -> 60,000 ile milisaniye yaptık.
            val millisInputSecond = inputSecond.toLong() * 1000      // Saniye -> 1,000 ile milisaniye yaptık.


            val resultMillisInputs = millisInputHour + millisInputMinute + millisInputSecond
            setTime(resultMillisInputs)
        }
        mBind.startButton.setOnClickListener {

            if (mTimerRunning) {
                pauseTimer()
            } else {
                startTimer()
            }

        }
        mBind.resetButton.setOnClickListener {
            resetTimer()
        }

        mBind.hourSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mBind.hourTime.text = hour_seek_bar.progress.toString()
                mBind.hourPicker.setText(hour_seek_bar.progress.toString())


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mBind.hourTime.text = hour_seek_bar.progress.toString()
                mBind.hourPicker.setText(hour_seek_bar.progress.toString())

            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mBind.hourTime.text = hour_seek_bar.progress.toString()
                Toast.makeText(this@MainActivity,
                    "Progress is: " + hour_seek_bar.progress,
                    Toast.LENGTH_SHORT).show()
            }


        })
        mBind.minuteSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mBind.minuteTime.text = minute_seek_bar.progress.toString()
                mBind.minutePicker.setText(minute_seek_bar.progress.toString())


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mBind.minuteTime.text = minute_seek_bar.progress.toString()
                mBind.minutePicker.setText(minute_seek_bar.progress.toString())
            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mBind.minuteTime.text = minute_seek_bar.progress.toString()
                mBind.minutePicker.setText(minute_seek_bar.progress.toString())
                Toast.makeText(this@MainActivity,
                    "Progress is: " + minute_seek_bar.progress,
                    Toast.LENGTH_SHORT).show()
            }


        })
        mBind.secondSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                mBind.secondTime.text = second_seek_bar.progress.toString()
                mBind.secondPicker.setText(second_seek_bar.progress.toString())


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mBind.secondTime.text = second_seek_bar.progress.toString()
                mBind.secondPicker.setText(second_seek_bar.progress.toString())
            }


                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    mBind.secondTime.text = second_seek_bar.progress.toString()
                    mBind.secondPicker.setText(second_seek_bar.progress.toString())

                    Toast.makeText(this@MainActivity,
                    "Progress is: " + second_seek_bar.progress,
                    Toast.LENGTH_SHORT).show()
            }


        })

    }

    private fun setTime(resultMillisInputs: Long) {
        mStartTimeInMillis = resultMillisInputs // Toplam milisaniyeleri başlangıç değişkenine atadık.
        resetTimer()
    }

    private fun pauseTimer() {
        mCountDownTimer.cancel()
        mTimerRunning = false
    }

    private fun resetTimer() {
        mTimeLeftInMillis = mStartTimeInMillis //Kalan süreyi Başlangıç değişkeniyle eşitledik.
        updateCountDownText()
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false

            }
        }
            .start()

        mTimerRunning = true
        // Buraya Stop düğmesi koyacağız. SetImage gibi bir şey ile
    }

    private fun updateCountDownText() {
        val hours: Long = (mTimeLeftInMillis / 1000) / 3600
        val minutes: Long = ((mTimeLeftInMillis / 1000) % 3600) / 60
        val seconds: Long = (mTimeLeftInMillis / 1000) % 60

        if (mBind.hourPicker.text.isNotEmpty()) {

            mBind.hourTime.text = String.format(Locale.getDefault(), "%02d", hours.toInt())
            mBind.minuteTime.text = String.format(Locale.getDefault(), "%02d", minutes.toInt())
            mBind.secondTime.text = String.format(Locale.getDefault(), "%02d", seconds.toInt())

        } else {
            mBind.minuteTime.text = String.format(Locale.getDefault(), "%02d", minutes.toInt())
            mBind.secondTime.text = String.format(Locale.getDefault(), "%02d", seconds.toInt())
        }

    }

}