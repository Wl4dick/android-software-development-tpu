package ru.wladislavshcherbakov.lab17

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.wladislavshcherbakov.lab17.databinding.FragmentBooksBinding

class BooksFragment : Fragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBooksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
    }

    private fun setupTabs() {
        val tabs = listOf(
            getString(R.string.tab_new),
            getString(R.string.tab_read)
        )

        // Добавляем вкладки
        tabs.forEach { tabTitle ->
            val tab = binding.tabLayout.newTab()
            tab.text = tabTitle
            binding.tabLayout.addTab(tab)
        }

        // Показываем первую вкладку по умолчанию
        showBooksContent(tabs[0])

        // Обработчик выбора вкладки
        binding.tabLayout.addOnTabSelectedListener(object : com.google.android.material.tabs.TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: com.google.android.material.tabs.TabLayout.Tab?) {
                tab?.text?.let { showBooksContent(it.toString()) }
            }

            override fun onTabUnselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
            override fun onTabReselected(tab: com.google.android.material.tabs.TabLayout.Tab?) {}
        })
    }

    private fun showBooksContent(category: String) {
        val fragment = ContentFragment.newInstance(
            requireContext().getString(R.string.books_category_template, category)
        )
        childFragmentManager.beginTransaction()
            .replace(R.id.books_content, fragment)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}