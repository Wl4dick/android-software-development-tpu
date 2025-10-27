package ru.wladislavshcherbakov.lab16.fragments

import ru.wladislavshcherbakov.lab16.R

class IntroFragment : BaseStoryFragment() {
    override fun setupFragment() {
        setupButtons(
            imageRes = R.drawable.elders,
            textRes = R.string.intro_text,
            button1Text = R.string.run_away,
            button1Action = R.id.action_introFragment_to_hareFragment,
            button2Text = R.string.stay,
            button2Action = R.id.action_introFragment_to_finalBadFragment
        )
    }
}