package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import kotlinx.coroutines.delay
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
object InvisibleSessionManager {
    private var viewModel: SessionViewModel? = null
    private var mediaplayer: MediaPlayer? = null

    fun setParameters(viewModel: SessionViewModel, mediaplayer: MediaPlayer) {
        this.viewModel = viewModel
        this.mediaplayer = mediaplayer
    }

    suspend fun updateTimer() {
        if (viewModel != null) {
            while (true) {
                delay(1.seconds)
                viewModel!!.getTimer().tick()
                if (viewModel!!.getTimer().hasCurrentCountdownEnded() && !viewModel!!.getTimer().hasEnded()) {
                    mediaplayer?.start()
                }
            }
        }
    }

    fun removeParameters() {
        viewModel = null
        mediaplayer = null
    }
}