package com.meowney.ui.more

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.meowney.R
import androidx.lifecycle.lifecycleScope
import com.meowney.data.SettingsDataStore
import com.meowney.databinding.FragmentThemingBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ThemingFragment : Fragment() {

    private var _binding: FragmentThemingBinding? = null
    private val binding get() = _binding!!
    private val settingsDataStore by lazy { SettingsDataStore(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThemingBinding.inflate(inflater, container, false)
        val view = binding.root

        // toolbar
        binding.themingToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // theme colors grid
        populateThemeGrid()

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populateThemeGrid() {
        viewLifecycleOwner.lifecycleScope.launch {
            var currentOverlayId = settingsDataStore.themeOverlay.first()
            if (currentOverlayId == 0) {
                currentOverlayId = R.style.ThemeOverlay_Blue
            }
            val styleFields = R.style::class.java.fields
            styleFields.forEach { field ->
                if (field.name.startsWith("ThemeOverlay_")) {
                    val overlayId = field.getInt(null)
                    val colorView = createColorView(overlayId, (currentOverlayId == overlayId))
                    colorView.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            settingsDataStore.saveThemeOverlay(overlayId)
                            requireActivity().recreate()
                        }
                    }
                    binding.colorGrid.addView(colorView)
                }
            }
        }
    }

    private fun createColorView(overlayId: Int, isSelected: Boolean): View {
        val view = View(context)
        val params = GridLayout.LayoutParams().apply {
            width = dpToPx(64)
            height = dpToPx(32)
            val margin = dpToPx(8)
            setMargins(margin, margin, margin, margin)
        }
        view.layoutParams = params

        // Get the colorPrimary from the theme overlay
        val typedValue = TypedValue()
        val contextThemeWrapper = androidx.appcompat.view.ContextThemeWrapper(context, overlayId)
        contextThemeWrapper.theme.resolveAttribute(androidx.appcompat.R.attr.colorPrimary, typedValue, true)
        view.setBackgroundColor(typedValue.data)

        if (isSelected) {
            val strokeWidth = dpToPx(4)
            val drawable = GradientDrawable()
            drawable.setColor(typedValue.data)
            drawable.setStroke(strokeWidth, resources.getColor(R.color.grey_400, null))
            view.background = drawable
        }

        return view
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}