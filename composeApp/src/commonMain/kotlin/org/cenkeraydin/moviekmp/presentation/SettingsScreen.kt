package org.cenkeraydin.moviekmp.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import moviekmp.composeapp.generated.resources.Res
import moviekmp.composeapp.generated.resources.cancel
import moviekmp.composeapp.generated.resources.dark_mode
import moviekmp.composeapp.generated.resources.language
import moviekmp.composeapp.generated.resources.select_language
import org.cenkeraydin.moviekmp.LocalAppLanguage
import org.cenkeraydin.moviekmp.LocalThemeManager
import org.cenkeraydin.moviekmp.util.AppTheme
import org.cenkeraydin.moviekmp.util.Language
import org.jetbrains.compose.resources.stringResource



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen() {
    val themeManager = LocalThemeManager.current
    val languageManager = LocalAppLanguage.current

    val currentTheme by themeManager.currentTheme.collectAsState()
    val currentLanguage by languageManager.currentLanguage.collectAsState()

    var showLanguageDialog by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Dark Mode Setting
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(Res.string.dark_mode),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Switch(
                        checked = currentTheme == AppTheme.Dark,
                        onCheckedChange = { themeManager.toggleTheme() }
                    )
                }
            }
        }

        // Language Setting
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showLanguageDialog = true },
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = stringResource(Res.string.language),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = currentLanguage.displayName,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null
                    )
                }
            }
        }
    }

    // Language Selection Dialog
    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = {
                Text(stringResource(Res.string.select_language))
            },
            text = {
                LazyColumn {
                    items(Language.entries.toTypedArray()) { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    languageManager.changeLanguage(language)
                                    showLanguageDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = currentLanguage == language,
                                onClick = {
                                    languageManager.changeLanguage(language)
                                    showLanguageDialog = false
                                }
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = language.displayName,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = { showLanguageDialog = false }
                ) {
                    Text(stringResource(Res.string.cancel))
                }
            }
        )
    }
}
