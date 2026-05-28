package com.example.sitemaster

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup

class MainActivity : Activity() {

    private var currentScreen = HOME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentScreen = savedInstanceState?.getString(KEY_SCREEN) ?: HOME
        showScreen(currentScreen)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_SCREEN, currentScreen)
        super.onSaveInstanceState(outState)
    }

    private fun showScreen(screen: String) {
        currentScreen = screen

        val layout = when (screen) {
            REFERENCE_STYLE -> R.layout.activity_reference_style
            AI_REPORT -> R.layout.activity_ai_report
            SIMILARITY -> R.layout.activity_similarity_tracking
            MENU -> R.layout.activity_menu
            else -> R.layout.activity_home
        }

        setContentView(layout)

        applyBackgrounds(findViewById(android.R.id.content))
        setupColourBlocks()
        setupNavigation()
    }

    private fun setupNavigation() {
        // Bottom navigation
        go(R.id.navHome, HOME)
        go(R.id.navUpload, REFERENCE_STYLE)
        go(R.id.navMore, MENU)

        // Home page buttons/cards
        go(R.id.uploadDocumentButton, REFERENCE_STYLE)
        go(R.id.cardAutomaticReferencing, REFERENCE_STYLE)
        go(R.id.cardAiHuman, AI_REPORT)
        go(R.id.cardSimilarity, SIMILARITY)

        // Back buttons
        go(R.id.backToHome, HOME)

        // Reference style page
        go(R.id.applyStyleButton, HOME)

        // Menu page
        go(R.id.menuHome, HOME)
        go(R.id.menuAutomaticReferencing, REFERENCE_STYLE)
        go(R.id.menuAiHuman, AI_REPORT)
        go(R.id.menuSimilarity, SIMILARITY)
    }

    private fun go(viewId: Int, screen: String) {
        findViewById<View>(viewId)?.setOnClickListener {
            showScreen(screen)
        }
    }

    private fun setupColourBlocks() {
        colourBlock(R.id.blockAutomaticReferencing, Colours.BLOCK_PURPLE)
        colourBlock(R.id.blockReferenceGeneration, Colours.BLOCK_GREEN)
        colourBlock(R.id.blockAiHuman, Colours.BLOCK_YELLOW)
        colourBlock(R.id.blockSimilarity, Colours.BLOCK_RED)

        colourBlock(R.id.blockPastReports, Colours.BLOCK_BLUE)
        colourBlock(R.id.blockLogout, Colours.BLOCK_RED)

        colourBlock(R.id.blockSectionIntro, Colours.BLOCK_RED)
        colourBlock(R.id.blockSectionLiterature, Colours.BLOCK_YELLOW)
        colourBlock(R.id.blockSectionMethodology, Colours.BLOCK_GREEN)
    }

    private fun colourBlock(viewId: Int, colour: String) {
        val view = findViewById<View>(viewId) ?: return
        view.background = rounded(colour, 10)
    }

    private fun applyBackgrounds(view: View) {
        when (view.tag as? String) {
            "bg_alert_red" -> setBg(view, Colours.ALERT_RED, 10, 1, Colours.ALERT_RED_BORDER)
            "bg_alert_yellow" -> setBg(view, Colours.ALERT_YELLOW, 10, 1, Colours.ALERT_YELLOW_BORDER)
            "bg_bottom_bar" -> setBg(view, Colours.WHITE, 0, 1, Colours.LINE)
            "bg_card" -> setBg(view, Colours.WHITE, 12, 1, Colours.LINE)
            "bg_card_selected" -> setBg(view, Colours.WHITE, 12, 2, Colours.PURPLE)
            "bg_chip" -> setBg(view, Colours.CHIP, 7, 1, Colours.LINE)
            "bg_done" -> setBg(view, Colours.DONE, 8)
            "bg_menu_dropdown" -> setBg(view, Colours.WHITE, 8, 1, Colours.MENU_BORDER)
            "bg_menu_selected" -> setBg(view, Colours.PURPLE_LIGHT, 7)
            "bg_purple_button" -> setBg(view, Colours.PURPLE, 14)
            "bg_purple_upload" -> setBg(view, Colours.PURPLE, 18)
            "bg_screen" -> setBg(view, Colours.SCREEN, 0)
            "bg_stat_purple" -> setBg(view, Colours.STAT_PURPLE, 9, 1, Colours.STAT_PURPLE_BORDER)
            "bg_stat_red" -> setBg(view, Colours.STAT_RED, 9, 1, Colours.STAT_RED_BORDER)
            "bg_upload_inner" -> setBg(view, Colours.UPLOAD_INNER, 14)
        }

        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                applyBackgrounds(view.getChildAt(i))
            }
        }
    }

    private fun setBg(
        view: View,
        fillColour: String,
        radiusDp: Int,
        strokeDp: Int = 0,
        strokeColour: String = "#00000000"
    ) {
        view.background = rounded(fillColour, radiusDp, strokeDp, strokeColour)
    }

    private fun rounded(
        fillColour: String,
        radiusDp: Int,
        strokeDp: Int = 0,
        strokeColour: String = "#00000000"
    ): GradientDrawable {
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.RECTANGLE
        shape.setColor(Color.parseColor(fillColour))
        shape.cornerRadius = dp(radiusDp).toFloat()

        if (strokeDp > 0) {
            shape.setStroke(dp(strokeDp), Color.parseColor(strokeColour))
        }

        return shape
    }

    private fun dp(value: Int): Int {
        return (value * resources.displayMetrics.density).toInt()
    }

    private object Colours {
        // Main colours
        const val WHITE = "#FFFFFF"
        const val SCREEN = "#FFFFFF"
        const val LINE = "#E3E0F0"
        const val PURPLE = "#4F3AF2"
        const val PURPLE_LIGHT = "#EFECFF"

        // Cards and labels
        const val CHIP = "#F2F1FA"
        const val DONE = "#CBF3D5"
        const val UPLOAD_INNER = "#6655F7"

        // Alert boxes
        const val ALERT_RED = "#FFE2E2"
        const val ALERT_RED_BORDER = "#FFAAAA"
        const val ALERT_YELLOW = "#FFF1C9"
        const val ALERT_YELLOW_BORDER = "#F2C558"

        // Similarity stat boxes
        const val STAT_RED = "#FFE2E2"
        const val STAT_RED_BORDER = "#FFB6B6"
        const val STAT_PURPLE = "#F0EEFF"
        const val STAT_PURPLE_BORDER = "#D6D0FF"

        // Menu
        const val MENU_BORDER = "#DAD7E9"

        // Small coloured service blocks
        const val BLOCK_PURPLE = "#EFECFF"
        const val BLOCK_GREEN = "#E8F8EF"
        const val BLOCK_YELLOW = "#FFF0BF"
        const val BLOCK_RED = "#FFE4E4"
        const val BLOCK_BLUE = "#E8F4FF"
    }

    companion object {
        private const val KEY_SCREEN = "current_screen"

        private const val HOME = "home"
        private const val REFERENCE_STYLE = "reference_style"
        private const val AI_REPORT = "ai_report"
        private const val SIMILARITY = "similarity"
        private const val MENU = "menu"
    }
}
