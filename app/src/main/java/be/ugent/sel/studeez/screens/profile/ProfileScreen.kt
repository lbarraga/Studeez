package be.ugent.sel.studeez.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.ugent.sel.studeez.common.composable.Headline
import be.ugent.sel.studeez.common.composable.PrimaryScreenTemplate
import be.ugent.sel.studeez.common.composable.drawer.DrawerActions
import be.ugent.sel.studeez.common.composable.navbar.NavigationBarActions
import be.ugent.sel.studeez.resources
import kotlinx.coroutines.CoroutineScope
import be.ugent.sel.studeez.R.string as AppText

data class ProfileActions(
    val getUsername: suspend CoroutineScope.() -> String?,
    val getBiography: suspend CoroutineScope.() -> String?,
    val onEditProfileClick: () -> Unit
)

fun getProfileActions(
    viewModel: ProfileViewModel,
    open: (String) -> Unit
): ProfileActions {
    return ProfileActions(
        getUsername = { viewModel.getUsername() },
        getBiography = { viewModel.getBiography() },
        onEditProfileClick = { viewModel.onEditProfileClick(open) }
    )
}

@Composable
fun ProfileRoute(
    open: (String) -> Unit,
    viewModel: ProfileViewModel,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {
    ProfileScreen(
        profileActions = getProfileActions(viewModel, open),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
    )
}

@Composable
fun ProfileScreen(
    profileActions: ProfileActions,
    drawerActions: DrawerActions,
    navigationBarActions: NavigationBarActions,
) {
    var username: String? by remember { mutableStateOf("") }
    var biography: String? by remember { mutableStateOf("") }
    LaunchedEffect(key1 = Unit) {
        username = profileActions.getUsername(this)
        biography = profileActions.getBiography(this)
    }
    PrimaryScreenTemplate(
        title = resources().getString(AppText.profile),
        drawerActions = drawerActions,
        navigationBarActions = navigationBarActions,
        barAction = { EditAction(onClick = profileActions.onEditProfileClick) }
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            item {
                Headline(text = username ?: resources().getString(AppText.no_username))
            }
            item {
                Text(
                    text = biography ?: "",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(48.dp, 0.dp)
                )
            }
        }
    }
}

@Composable
fun EditAction(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = Icons.Default.Edit,
            contentDescription = resources().getString(AppText.edit_profile)
        )

    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        profileActions = ProfileActions({ null }, { null }, {}),
        drawerActions = DrawerActions({}, {}, {}, {}, {}),
        navigationBarActions = NavigationBarActions({ false }, {}, {}, {}, {}, {}, {}, {})
    )
}