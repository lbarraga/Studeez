package be.ugent.sel.studeez.screens.timer_overview.add_timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Checkbox
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.R
import be.ugent.sel.studeez.common.composable.BasicButton
import be.ugent.sel.studeez.common.composable.SecondaryScreenTemplate
import be.ugent.sel.studeez.resources

data class AddTimerActions(
    val goBack: () -> Unit,
    val onStudyTimeHoursChange: (Float) -> Unit,
    val onStudyTimeMinutesChange: (Float) -> Unit,
    val onBreakTimeChange: (Float) -> Unit,
    val onRepeatsChange: (Float) -> Unit,
    val onWithBreaksChange: (Boolean) -> Unit,
    val addTimer: () -> Unit,
    val onNameChange: (String) -> Unit,
    val onDescriptionChange: (String) -> Unit,
)

fun getAddTimerActions(
    goBack: () -> Unit,
    viewModel: AddTimerViewModel,
): AddTimerActions {
    return AddTimerActions(
        goBack = goBack,
        onWithBreaksChange = viewModel::onWithBreaksChange,
        onStudyTimeHoursChange = viewModel::onStudyTimeHoursChange,
        onStudyTimeMinutesChange = viewModel::onStudyTimeMinutesChange,
        onBreakTimeChange = viewModel::onBreakTimeChange,
        onRepeatsChange = viewModel::onRepeatsChange,
        addTimer = viewModel::addTimer,
        onNameChange = viewModel::onNameChange,
        onDescriptionChange = viewModel::onDescriptionChange
    )
}

@Composable
fun AddTimerRoute(
    open: (String) -> Unit,
    openAndPopUp: (String, String) -> Unit,
    goBack: () -> Unit,
    viewModel: AddTimerViewModel,
) {
    val uiState by viewModel.uiState

    AddTimerScreen(
        addTimerActions = getAddTimerActions(
            goBack = goBack,
            viewModel = viewModel,
        ),
        uiState = uiState
    )
}

@Composable
fun AddTimerScreen(
    addTimerActions: AddTimerActions,
    uiState: AddTimerUiState,
) {
    SecondaryScreenTemplate(
        title = resources().getString(R.string.add_timer),
        popUp = addTimerActions.goBack
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "How long do you want to study?",
                    textAlign = TextAlign.Center
                )
            }

            LazyColumn(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                item{
                    Text(
                        text = "${uiState.studyTimeHours.toInt()} hour${ if (uiState.studyTimeHours == 1f) "" else "s"}"
                    )
                    Slider(
                        value = uiState.studyTimeHours,
                        onValueChange = {
                            addTimerActions.onStudyTimeHoursChange(it)
                        },
                        steps = 8,
                        valueRange = 1f..10f,
                        enabled = true
                    )

                    Text(
                        text = "${uiState.studyTimeMinutes.toInt()} minutes"
                    )
                    Slider(
                        value = uiState.studyTimeMinutes,
                        onValueChange = {
                            addTimerActions.onStudyTimeMinutesChange(it)
                        },
                        steps = 11,
                        valueRange = 0f..60f,
                        enabled = true
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "With breaks?",
                        )
                        Checkbox(
                            checked = uiState.withBreaks,
                            onCheckedChange = { addTimerActions.onWithBreaksChange(it) }
                        )
                    }

                    Text(
                        text = if (uiState.withBreaks) "breaks of ${uiState.breakTime.toInt()} minutes" else "",
                    )
                    Slider(
                        value = uiState.breakTime,
                        onValueChange = {
                            addTimerActions.onBreakTimeChange(it)
                        },
                        steps = 11,
                        valueRange = 0f..60f,
                        enabled = uiState.withBreaks
                    )

                    Text(
                        text = if (uiState.withBreaks) "${uiState.repeats.toInt()} breaks" else ""
                    )
                    Slider(
                        value = uiState.repeats,
                        onValueChange = {
                            addTimerActions.onRepeatsChange(it)
                        },
                        steps = 8,
                        valueRange = 1f..10f,
                        enabled = uiState.withBreaks
                    )

                    Text(
                        text = "Timer name"
                    )
                    TextField(
                        value = uiState.name,
                        onValueChange = { addTimerActions.onNameChange(it) }
                    )

                    Text(
                        text = "Timer description"
                    )
                    TextField(
                        value = uiState.description,
                        onValueChange = { addTimerActions.onDescriptionChange(it) }
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BasicButton(
                            text = R.string.add_timer,
                            modifier = Modifier,
                            onClick = addTimerActions.addTimer
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun AddTimerScreenPreview() {
    AddTimerScreen(
        addTimerActions = AddTimerActions({}, {}, {}, {}, {}, {}, {}, {}, {}),
        uiState = AddTimerUiState()
    )
}