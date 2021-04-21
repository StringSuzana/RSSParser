package hr.santolin.rssparser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import hr.santolin.rssparser.databinding.FragmentArticlesBinding

class ArticlesFragment : Fragment(){
    private var _binding: FragmentArticlesBinding? = null
    val binding: FragmentArticlesBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentArticlesBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}