package ru.wladislavshcherbakov.lab17

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import ru.wladislavshcherbakov.lab17.databinding.FragmentMusicBinding

class MusicFragment : Fragment() {

    private var _binding: FragmentMusicBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMusicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        val genres = listOf(
            getString(R.string.tab_pop),
            getString(R.string.tab_rock),
            getString(R.string.tab_disco),
            getString(R.string.tab_classic),
            getString(R.string.tab_jazz),
            getString(R.string.tab_hiphop),
            getString(R.string.tab_electronic),
            getString(R.string.tab_country),
            getString(R.string.tab_blues),
            getString(R.string.tab_metal),
            getString(R.string.tab_folk)
        )

        val adapter = MusicViewPagerAdapter(genres, this)
        binding.viewPager.adapter = adapter

        // Связываем TabLayout с ViewPager2
        com.google.android.material.tabs.TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = genres[position]
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class MusicViewPagerAdapter(
    private val genres: List<String>,
    fragment: Fragment
) : androidx.recyclerview.widget.RecyclerView.Adapter<MusicViewPagerAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contentText = holder.view.findViewById<android.widget.TextView>(R.id.contentText)
        contentText.text = holder.view.context.getString(R.string.music_genre_template, genres[position])
    }

    override fun getItemCount(): Int = genres.size
}