package com.meowney.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.meowney.R
import com.meowney.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.aboutToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // passing about string as HTML for formatting
        binding.aboutTextView.text = HtmlCompat.fromHtml(getString(R.string.about_text), HtmlCompat.FROM_HTML_MODE_LEGACY)

        return view
    }
}