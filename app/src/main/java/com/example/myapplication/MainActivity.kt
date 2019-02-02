package com.example.myapplication

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import kotlinx.android.synthetic.main.activity_main.*
import android.media.MediaPlayer
import android.view.SurfaceHolder
import android.media.AudioManager
import android.net.Uri
import java.io.IOException
import android.view.MotionEvent
import android.view.OrientationEventListener
import android.widget.FrameLayout
import android.widget.Toast
import kotlinx.android.synthetic.main.media_controller.*


class MainActivity : AppCompatActivity(), SurfaceHolder.Callback, MediaPlayer.OnPreparedListener, MyMediaController.MediaPlayerControl{

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {

    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        player?.setDisplay(holder);
        player?.prepareAsync();
    }

    override fun onPrepared(mp: MediaPlayer?) {
        controller?.setMediaPlayer(this);
        controller?.setAnchorView(findViewById<FrameLayout>(R.id.videoSurfaceContainer));
        player?.start();
    }

    override fun start() {
        if (player!=null) {
            return player?.start()!!;
        }
    }

    override fun pause() {
        if (player!=null) {
            return player?.pause()!!;
        }
    }

    override fun getDuration(): Int {
        if (player!=null) {
            return player?.duration!!;
        }else{
            return 0
        }
    }

    override fun getCurrentPosition(): Int {
        if (player!=null) {
            return player?.getCurrentPosition()!!;
        }else{
            return 0
        }
    }

    override fun seekTo(pos: Int) {
        if (player!=null) {
            return player?.seekTo(pos)!!;
        }
    }

    override fun isPlaying(): Boolean {
        if (player!=null) {
            return player?.isPlaying()!!;
        }else{
            return false
        }
    }

    override fun getBufferPercentage(): Int {
       return 0;
    }

    override fun canPause(): Boolean {
       return true
    }

    override fun canSeekBackward(): Boolean {
       return true
    }

    override fun canSeekForward(): Boolean {
       return true
    }

    override fun isFullScreen(): Boolean {
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            return true
        }else {
            return false
        }
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if(newConfig?.orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(applicationContext,"Portrait ",Toast.LENGTH_SHORT).show()
        }
        if(newConfig?.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Toast.makeText(applicationContext,"Landscape ",Toast.LENGTH_SHORT).show()
        }
    }

    override fun toggleFullScreen() {
        if (isFullScreen()){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        Toast.makeText(applicationContext,"Toggle Full",Toast.LENGTH_SHORT).show()
        //controller?.updateFullScreen()
    }

    var player: MediaPlayer? = null
    var controller: MyMediaController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val videoHolder = videoSurface.holder
        videoHolder.addCallback(this)

        player = MediaPlayer()
        controller = MyMediaController(this)
        val videoController = MediaController(this)
        /*videoController.setAnchorView(frameLayout)
       // videoController.show()
        videoView.setMediaController(videoController)
        videoView.setVideoPath("http://videocdn.bodybuilding.com/video/mp4/62000/62792m.mp4")
        videoView.start()*/
        try {
            player?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            player?.setDataSource(this, Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
            player?.setOnPreparedListener(this)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } catch (e: SecurityException) {
            e.printStackTrace()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }


    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        controller?.show()
        return false
    }
}
