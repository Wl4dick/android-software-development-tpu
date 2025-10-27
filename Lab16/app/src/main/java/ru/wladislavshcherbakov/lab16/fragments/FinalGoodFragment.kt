package ru.wladislavshcherbakov.lab16.fragments

import ru.wladislavshcherbakov.lab16.R

class FinalGoodFragment : BaseStoryFragment() {
    override fun setupFragment() {
        setupButtons(
            imageRes = R.drawable.final_good,
            textRes = R.string.final_good_text,
            button1Text = R.string.start_again,
            button1Action = R.id.action_finalGoodFragment_to_introFragment
        )
    }
}