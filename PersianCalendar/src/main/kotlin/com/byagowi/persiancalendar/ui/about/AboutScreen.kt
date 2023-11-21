package com.byagowi.persiancalendar.ui.about

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.util.Linkify
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import androidx.core.text.bold
import androidx.core.text.buildSpannedString
import androidx.core.text.scale
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.byagowi.persiancalendar.BuildConfig
import com.byagowi.persiancalendar.R
import com.byagowi.persiancalendar.databinding.AboutScreenBinding
import com.byagowi.persiancalendar.generated.faq
import com.byagowi.persiancalendar.global.language
import com.byagowi.persiancalendar.ui.utils.bringMarketPage
import com.byagowi.persiancalendar.ui.utils.getAnimatedDrawable
import com.byagowi.persiancalendar.ui.utils.getCompatDrawable
import com.byagowi.persiancalendar.ui.utils.hideToolbarBottomShadow
import com.byagowi.persiancalendar.ui.utils.isRtl
import com.byagowi.persiancalendar.ui.utils.navigateSafe
import com.byagowi.persiancalendar.ui.utils.onClick
import com.byagowi.persiancalendar.ui.utils.resolveResourceIdFromTheme
import com.byagowi.persiancalendar.ui.utils.setupMenuNavigation
import com.byagowi.persiancalendar.utils.formatNumber
import com.byagowi.persiancalendar.utils.logException
import com.byagowi.persiancalendar.utils.supportedYearOfIranCalendar
import com.google.android.material.chip.Chip

class AboutScreen : Fragment(R.layout.about_screen) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = AboutScreenBinding.bind(view)
        binding.appBar.toolbar.setTitle(R.string.about)
        binding.appBar.toolbar.setupMenuNavigation()
        binding.appBar.toolbar.menu.add(R.string.share).also {
            it.icon = binding.appBar.toolbar.context.getCompatDrawable(R.drawable.ic_baseline_share)
            it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            it.onClick {  }
        }
        binding.appBar.toolbar.menu.add(R.string.device_information).also {
            it.icon = binding.appBar.toolbar.context.getCompatDrawable(R.drawable.ic_device_information)
            it.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            it.onClick {
                findNavController().navigateSafe(AboutScreenDirections.actionAboutToDeviceInformation())
            }
        }
        binding.appBar.root.hideToolbarBottomShadow()

        // app
        val version = buildSpannedString {
            scale(1.5f) { bold { appendLine(getString(R.string.app_name)) } }
            scale(.8f) {
                val version =
                    // Don't formatNumber it if is multi-parted
                    formatNumber("8.5.0")
                   append(getString(R.string.version, version))
            }
            if (language.isUserAbleToReadPersian) {
                appendLine()
                scale(.8f) {
                    append(
                        getString(
                            R.string.about_help_subtitle,
                            formatNumber(supportedYearOfIranCalendar - 1),
                            formatNumber(supportedYearOfIranCalendar)
                        )
                    )
                }
            }
        }
        binding.aboutHeader.text = version
        binding.accessibleVersion.contentDescription = version
        run {

        }

        fun TextView.putLineStartIcon(@DrawableRes icon: Int) {
            if (resources.isRtl) setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0)
            else setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0)
        }

        // licenses
        binding.licenses.setOnClickListener {
            findNavController().navigateSafe(AboutScreenDirections.actionAboutToLicenses())
        }
        binding.licensesTitle.putLineStartIcon(R.drawable.ic_licences)

        // help
        binding.helpCard.isVisible = language.isUserAbleToReadPersian
        binding.helpTitle.putLineStartIcon(R.drawable.ic_help)
        binding.helpSectionsRecyclerView.apply {
            val sections = faq
                .split(Regex("^={4}$", RegexOption.MULTILINE))
                .map { it.trim().lines() }
                .map { lines ->
                    val title = lines.first()
                    val body = SpannableString(lines.drop(1).joinToString("\n").trim())
                    Linkify.addLinks(body, Linkify.WEB_URLS or Linkify.EMAIL_ADDRESSES)
                    title to body
                }
            adapter = ExpandableItemsAdapter(sections)
            layoutManager = LinearLayoutManager(context)
        }

        // report bug




        setupContributorsList(binding)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            binding.contentRoot.updatePadding(bottom = insets.bottom)
            binding.appBar.toolbar.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = insets.top
            }
            WindowInsetsCompat.CONSUMED
        }
    }

    private fun setupContributorsList(binding: AboutScreenBinding) {
        val context = binding.root.context

        val chipsIconTintId =
            context.resolveResourceIdFromTheme(com.google.android.material.R.attr.colorAccent)




        // Chip view inflation crashes in Android 4 as lack RippleDrawable apparently and material's
        // internal bug so let's just hide it there

}
    }
