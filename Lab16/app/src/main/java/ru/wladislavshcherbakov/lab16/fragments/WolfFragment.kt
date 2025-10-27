package ru.wladislavshcherbakov.lab16.fragments

import ru.wladislavshcherbakov.lab16.R

class WolfFragment : BaseStoryFragment() {
    override fun setupFragment() {
        setupButtons(
            imageRes = R.drawable.wolf,
            textRes = R.string.wolf_text,
            button1Text = R.string.run_away,
            button1Action = R.id.action_wolfFragment_to_bearFragment,
            button2Text = R.string.stay,
            button2Action = R.id.action_wolfFragment_to_finalBadFragment
        )
    }
}