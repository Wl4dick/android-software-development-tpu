package ru.wladislavshcherbakov.lab16.fragments

import ru.wladislavshcherbakov.lab16.R

class FoxFragment : BaseStoryFragment() {
    override fun setupFragment() {
        setupButtons(
            imageRes = R.drawable.fox,
            textRes = R.string.fox_text,
            button1Text = R.string.obey,
            button1Action = R.id.action_foxFragment_to_finalBadFragment,
            button2Text = R.string.run_away,
            button2Action = R.id.action_foxFragment_to_finalGoodFragment
        )
    }
}