package ru.wladislavshcherbakov.lab16.fragments

import ru.wladislavshcherbakov.lab16.R

class HareFragment : BaseStoryFragment() {
    override fun setupFragment() {
        setupButtons(
            imageRes = R.drawable.hare,
            textRes = R.string.hare_text,
            button1Text = R.string.run_away,
            button1Action = R.id.action_hareFragment_to_wolfFragment,
            button2Text = R.string.stay,
            button2Action = R.id.action_hareFragment_to_finalBadFragment
        )
    }
}