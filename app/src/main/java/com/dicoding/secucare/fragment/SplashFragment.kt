package com.dicoding.secucare.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.dicoding.secucare.R

class SplashFragment : Fragment() {
    private var pageNumber = 0

    companion object{
        private const val ARG_PAGE = "page"

        fun newInstance(page: Int): SplashFragment{
            val fragment = SplashFragment()
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments?.getInt(ARG_PAGE) ?: 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        when(pageNumber){
            0 -> {
                val imageView = view.findViewById<ImageView>(androidx.appcompat.R.id.imageView)
            }
        }

        return view
    }
}