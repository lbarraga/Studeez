package be.ugent.sel.studeez.screens.session

import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import be.ugent.sel.studeez.data.local.models.timer_functional.FunctionalTimer
import be.ugent.sel.studeez.screens.session.sessionScreens.composables.GetSessionScreenComposable

data class SessionActions(
    val getTimer: () -> FunctionalTimer,
    val getTask: () -> String,
    val endSession: () -> Unit
)

private fun getSessionActions(
    viewModel: SessionViewModel,
    openAndPopUp: (String, String) -> Unit,
): SessionActions {
    return SessionActions(
        getTimer = viewModel::getTimer,
        getTask = viewModel::getTask,
        endSession = { viewModel.endSession(openAndPopUp) },
    )
}

@Composable
fun SessionRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SessionViewModel,
) {
    val context = LocalContext.current
    val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    val mediaplayer = MediaPlayer.create(context, uri)
    mediaplayer.isLooping = false

    InvisibleSessionManager.setParameters(
        viewModel = viewModel,
        mediaplayer = mediaplayer
    )

    val soundPlayer = SoundPlayer(LocalContext.current)
    val sessionActions = getSessionActions(viewModel, openAndPopUp)
    val sessionScreen = viewModel.getTimer().accept(GetSessionScreenComposable(soundPlayer, open, sessionActions))

    sessionScreen()
}
