package com.byagowi.persiancalendar.ui.Other_products

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.byagowi.persiancalendar.databinding.FragmentOtherproductsBinding
import com.byagowi.persiancalendar.ui.MainActivity


class OtherproductsFragment : Fragment() {

    private lateinit var binding : FragmentOtherproductsBinding
    private val packageName1 = "636506106702"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtherproductsBinding.inflate(inflater, container, false)

        binding.Otherproducts.apply {
            // باز کردن بازار
            val bazaarIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("bazaar://collection?slug=by_author&aid=$packageName1")
                setPackage("com.farsitel.bazaar")
            }
            startActivity(bazaarIntent)

            // بستن اکتیویتی میزبان


            // دوباره باز کردن MainActivity

            requireActivity().finish()
        }

        return binding.root
    }

}


