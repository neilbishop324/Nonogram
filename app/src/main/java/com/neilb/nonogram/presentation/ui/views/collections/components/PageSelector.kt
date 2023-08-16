package com.neilb.nonogram.presentation.ui.views.collections.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.neilb.nonogram.presentation.ui.theme.indigoColor
import com.neilb.nonogram.presentation.ui.views.collections.CollectionsViewModel
import com.neilb.nonogram.presentation.ui.views.collections.CollectionsViewModel.Companion.limit

@Composable
fun PageSelector(viewModel: CollectionsViewModel, isPublic: Boolean) {

    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        IconButton(onClick = {
            viewModel.updateSkipNumberInLimit(viewModel.skipNumberInLimit.value!! - limit, context, isPublic)
        }, enabled = viewModel.skipNumberInLimit.value != 0) {
            Icon(imageVector = Icons.Default.ArrowCircleLeft, contentDescription = null)
        }

        if (viewModel.skipNumberInLimit.value != 0) {
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(indigoColor, CircleShape)
                    .clickable { viewModel.updateSkipNumberInLimit(viewModel.skipNumberInLimit.value!! - limit, context, isPublic) },
                contentAlignment = Alignment.Center
            ) {
                Text(text = ((viewModel.skipNumberInLimit.value!! - limit) / limit).toString(), color = Color.White)
            }
        }

        Spacer(modifier = Modifier.width(6.dp))

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(indigoColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = ((viewModel.skipNumberInLimit.value!!) / limit).toString(), color = Color.White)
        }

        if (viewModel.documentCount.value > viewModel.skipNumberInLimit.value!! + limit) {
            Spacer(modifier = Modifier.width(6.dp))
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(indigoColor, CircleShape)
                    .clickable {
                        viewModel.updateSkipNumberInLimit(viewModel.skipNumberInLimit.value!! + limit, context, isPublic)
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = ((viewModel.skipNumberInLimit.value!! + limit) / limit).toString(), color = Color.White)
            }
        }

        Spacer(modifier = Modifier.width(6.dp))

        IconButton(onClick = {
            viewModel.updateSkipNumberInLimit(viewModel.skipNumberInLimit.value!! + limit, context, isPublic)
        }, enabled = viewModel.documentCount.value > viewModel.skipNumberInLimit.value!! + limit) {
            Icon(imageVector = Icons.Default.ArrowCircleRight, contentDescription = null)
        }
    }
}