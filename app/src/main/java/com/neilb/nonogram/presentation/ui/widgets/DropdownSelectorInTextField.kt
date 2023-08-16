package com.neilb.nonogram.presentation.ui.widgets

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelectorInTextField(
    visibility: Boolean,
    setVisibility: (Boolean) -> Unit,
    label: String,
    options: List<String> = listOf(),
    selectedOptionText: String,
    setSelectedOptionText: (String) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = visibility,
        onExpandedChange = { setVisibility(!visibility) },
    ) {
        TextField(
            modifier = Modifier.menuAnchor(),
            readOnly = true,
            value = selectedOptionText,
            onValueChange = {},
            label = { Text(label) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = visibility) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = visibility,
            onDismissRequest = { setVisibility(false) },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        setSelectedOptionText(selectionOption)
                        setVisibility(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}