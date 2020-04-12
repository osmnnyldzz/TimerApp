package com.ottocode.timerapp.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ottocode.timerapp.R
import com.ottocode.timerapp.databinding.ActivityMainBinding
import com.ottocode.timerapp.viewmodel.TimerViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.w3c.dom.Text
import java.util.*

@Suppress("SpellCheckingInspection")
class MainActivity : AppCompatActivity() {

    private lateinit var mBind: ActivityMainBinding
    private lateinit var model: TimerViewModel

    companion object {
        private var mInputHour: String = ""
        private var mMillisInputHour: Long = 0

        private var mInputMinute: String = ""
        private var mMillisInputMinute: Long = 0

        private var mInputSecond: String = ""
        private var mMillisInputSecond: Long = 0

    }

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
        setTime(mMillisInputHour, mMillisInputMinute, mMillisInputSecond)


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

        mBind.hourPicker.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                model.hourTime.value = model.hour.value

                mInputHour = model.hour.value.toString()
                mMillisInputHour = mInputHour.toLong() * 3600000

                setTime(mMillisInputHour, mMillisInputMinute, mMillisInputSecond)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {1
            }

        })
        mBind.minutePicker.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                model.minuteTime.value = model.minute.value

                mInputMinute = model.minute.value.toString()
                mMillisInputMinute = mInputMinute.toLong() * 60000

                setTime(mMillisInputHour, mMillisInputMinute, mMillisInputSecond)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        mBind.secondPicker.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                model.secondTime.value = model.second.value
                mInputSecond = model.second.value.toString()
                mMillisInputSecond = mInputSecond.toLong() * 1000

                setTime(mMillisInputHour, mMillisInputMinute, mMillisInputSecond)

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        mBind.hourSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                mInputHour = hour_seek_bar.progress.toString()
                mMillisInputHour = mInputHour.toLong() * 3600000


                setTime(mMillisInputHour, mMillisInputMinute, mMillisInputSecond)

                model.hourTime.value = hour_seek_bar.progress.toString()
                model.hour.value = hour_seek_bar.progress.toString()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mBind.hourTime.text = hour_seek_bar.progress.toString()
                mBind.hourPicker.setText(hour_seek_bar.progress.toString())

            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mBind.hourTime.text = hour_seek_bar.progress.toString()
                Toast.makeText(
                    this@MainActivity,
                    "Progress is: " + hour_seek_bar.progress,
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
        mBind.minuteSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                mInputMinute = minute_seek_bar.progress.toString()
                mMillisInputMinute = mInputMinute.toLong() * 60000

                setTime(mMillisInputHour, mMillisInputMinute, mMillisInputSecond)

                mBind.minuteTime.text = minute_seek_bar.progress.toString()
                model.minute.value = minute_seek_bar.progress.toString()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mBind.minuteTime.text = minute_seek_bar.progress.toString()
                mBind.minutePicker.setText(minute_seek_bar.progress.toString())
            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mBind.minuteTime.text = minute_seek_bar.progress.toString()
                mBind.minutePicker.setText(minute_seek_bar.progress.toString())
                Toast.makeText(
                    this@MainActivity,
                    "Progress is: " + minute_seek_bar.progress,
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
        mBind.secondSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                mInputSecond = second_seek_bar.progress.toString()
                mMillisInputSecond = mInputSecond.toLong() * 1000

                setTime(mMillisInputHour, mMillisInputMinute, mMillisInputSecond)

                mBind.secondTime.text = second_seek_bar.progress.toString()

                model.second.value = second_seek_bar.progress.toString()


            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                mBind.secondTime.text = second_seek_bar.progress.toString()
                mBind.secondPicker.setText(second_seek_bar.progress.toString())
            }


            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                mBind.secondTime.text = second_seek_bar.progress.toString()
                mBind.secondPicker.setText(second_seek_bar.progress.toString())

                Toast.makeText(
                    this@MainActivity,
                    "Progress is: " + second_seek_bar.progress,
                    Toast.LENGTH_SHORT
                ).show()
            }


        })

    }


    private fun setTime(hour: Long, minute: Long, second: Long) {

        mStartTimeInMillis = hour + minute + second // Toplam milisaniyeleri başlangıç değişkenine atadık.
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