package com.meowney.ui.more

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.meowney.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.aboutToolbar)
        val navController = findNavController()
        toolbar.setNavigationOnClickListener {
            navController.navigateUp()
        }

        // passing about string as HTML for formatting
        val textView = view.findViewById<TextView>(R.id.textView)
        textView.text = HtmlCompat.fromHtml(getString(R.string.about_text), HtmlCompat.FROM_HTML_MODE_LEGACY)

        return view
    }
}