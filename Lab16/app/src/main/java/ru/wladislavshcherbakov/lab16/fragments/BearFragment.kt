package ru.wladislavshcherbakov.lab16.fragments

import ru.wladislavshcherbakov.lab16.R

class BearFragment : BaseStoryFragment() {
    override fun setupFragment() {
        setupButtons(
            imageRes = R.drawable.bear,
            textRes = R.string.bear_text,
            button1Text = R.string.run_away,
            button1Action = R.id.action_bearFragment_to_foxFragment,
            button2Text = R.string.stay,
            button2Action = R.id.action_bearFragment_to_finalBadFragment
        )
    }
}