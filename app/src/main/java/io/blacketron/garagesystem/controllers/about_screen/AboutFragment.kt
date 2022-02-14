package io.blacketron.garagesystem.controllers.about_screen

import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import io.blacketron.garagesystem.R

/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //To support drawables on devices running lower than API 21
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        // Inflate the layout for this fragment
        val root:View = inflater.inflate(R.layout.fragment_about, container, false)

        val gitHub = root.findViewById<TextView>(R.id.about_github)
        val aboutBody = root.findViewById<TextView>(R.id.about_body)

        gitHub.movementMethod = LinkMovementMethod.getInstance()

        setBodyContent(aboutBody)

        return root
    }

    private fun setBodyContent(textView: TextView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            val builder = SpannableStringBuilder()
            builder.append(resources.getString(R.string.menu_about_crafted))
                .append(" ") // Left margin
                .append(" ")
            builder.setSpan(
                ImageSpan(requireContext(), R.drawable.ic_pixel_heart),
                builder.length - 1, builder.length, 0
            )
            builder.append(" ") // Right margin
            builder.append(resources.getString(R.string.menu_about_author))
            textView.text = builder
        } else {
            val builder = SpannableStringBuilder()
            builder.append(resources.getString(R.string.menu_about_crafted))
                .append(" ") // Left margin
                .append(" ", ImageSpan(requireContext(), R.drawable.ic_pixel_heart), 0)
                .append(" ") // Right margin
                .append(resources.getString(R.string.menu_about_author))
            textView.text = builder
        }
    }
}