package ru.wladislavshcherbakov.lab16.fragments

import ru.wladislavshcherbakov.lab16.R

class FinalBadFragment : BaseStoryFragment() {
    override fun setupFragment() {
        setupButtons(
            imageRes = R.drawable.final_bad,
            textRes = R.string.final_bad_text,
            button1Text = R.string.try_again,
            button1Action = R.id.action_finalBadFragment_to_introFragment
        )
    }
}