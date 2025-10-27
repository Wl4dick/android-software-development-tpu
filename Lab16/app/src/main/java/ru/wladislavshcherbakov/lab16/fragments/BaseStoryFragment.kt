package ru.wladislavshcherbakov.lab16.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.wladislavshcherbakov.lab16.R

abstract class BaseStoryFragment : Fragment() {

    protected lateinit var storyImage: ImageView
    protected lateinit var storyText: TextView
    protected lateinit var button1: Button
    protected lateinit var button2: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_story, container, false)

        // Инициализируем View элементы
        storyImage = view.findViewById(R.id.storyImage)
        storyText = view.findViewById(R.id.storyText)
        button1 = view.findViewById(R.id.button1)
        button2 = view.findViewById(R.id.button2)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment()
    }

    abstract fun setupFragment()

    protected fun setupButtons(
        imageRes: Int,
        textRes: Int,
        button1Text: Int,
        button1Action: Int? = null,
        button2Text: Int? = null,
        button2Action: Int? = null
    ) {
        storyImage.setImageResource(imageRes)
        storyText.setText(textRes)

        button1.setText(button1Text)
        button1Action?.let { action ->
            button1.setOnClickListener {
                findNavController().navigate(action)
            }
        }

        if (button2Text != null && button2Action != null) {
            button2.visibility = View.VISIBLE
            button2.setText(button2Text)
            button2.setOnClickListener {
                findNavController().navigate(button2Action)
            }
        } else {
            button2.visibility = View.GONE
        }
    }
}