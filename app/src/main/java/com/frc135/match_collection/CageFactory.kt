package com.frc135.match_collection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frc135.match_collection.Constants.AllianceColor

object CageFactory {
    private var name: String = ""

    @Composable
    fun createCage(
        name: String,
        stageLevel: Constants.StageLevel,
        onStageLevelChange: (Constants.StageLevel) -> Unit,
        allianceColor: AllianceColor,
        orientation: Boolean,
        maxWidth: Dp,
        maxHeight: Dp,
        modifier: Modifier
    ) {
        this.name = name
        Box(
            modifier = Modifier
                .size(maxWidth / 4, maxHeight / 8)
                .then(modifier)
                .clickable {
                    val newPressTime = System.currentTimeMillis()
                    if (buttonPressedTime + 250 < newPressTime) {
                        buttonPressedTime = newPressTime
                        when (stageLevel) {
                            Constants.StageLevel.N -> onStageLevelChange(Constants.StageLevel.D)
                            Constants.StageLevel.D -> onStageLevelChange(Constants.StageLevel.DF)
                            Constants.StageLevel.DF -> onStageLevelChange(Constants.StageLevel.S)
                            Constants.StageLevel.S -> onStageLevelChange(Constants.StageLevel.SF)
                            Constants.StageLevel.SF -> onStageLevelChange(Constants.StageLevel.N)
                        }
                    }
                }
                .border(4.dp, getBorderColor(stageLevel))
                .background(getBackgroundColor(stageLevel))
                .rotate(if (orientation) 0f else 180f),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = getStageText(stageLevel),
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (stageLevel == Constants.StageLevel.DF) Color.White else Color.Black
                    )
                )
            }
        }
    }

    private fun getBorderColor(stageLevel: Constants.StageLevel): Color {
        return when (stageLevel) {
            Constants.StageLevel.D -> Color(24, 125, 20).copy(alpha = 0.6f)
            Constants.StageLevel.DF -> Color(125, 20, 20).copy(alpha = 0.6f)
            Constants.StageLevel.S -> Color(200, 200, 50).copy(alpha = 0.6f) // Shallow On
            Constants.StageLevel.SF -> Color(150, 50, 50).copy(alpha = 0.6f) // Shallow Fail
            else -> Color(255, 87, 34).copy(alpha = 0.6f)
        }
    }

    private fun getBackgroundColor(stageLevel: Constants.StageLevel): Color {
        return when (stageLevel) {
            Constants.StageLevel.D -> Color.Green.copy(alpha = 0.6f)
            Constants.StageLevel.DF -> Color.Red.copy(alpha = 0.6f)
            Constants.StageLevel.S -> Color.Yellow.copy(alpha = 0.6f)
            Constants.StageLevel.SF -> Color.DarkGray.copy(alpha = 0.6f)
            else -> Color(255, 152, 0).copy(alpha = 0.6f)
        }
    }

    private fun getStageText(stageLevel: Constants.StageLevel): String {
        return when (stageLevel) {
            Constants.StageLevel.S -> "SHALLOW ON"
            Constants.StageLevel.SF -> "SHALLOW FAILED"
            Constants.StageLevel.D -> "DEEP ON"
            Constants.StageLevel.DF -> "DEEP FAILED"
            else -> "$name CAGE"
        }
    }

}