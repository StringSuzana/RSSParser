package hr.santolin.rssparser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.santolin.rssparser.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    //viewbinding
    private var _binding: FragmentAboutBinding? = null
    val binding: FragmentAboutBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}