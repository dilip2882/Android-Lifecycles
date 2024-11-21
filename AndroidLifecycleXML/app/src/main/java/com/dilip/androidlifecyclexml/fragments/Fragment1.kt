package com.dilip.androidlifecyclexml.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.dilip.androidlifecyclexml.R
import timber.log.Timber

class Fragment1 : Fragment() {

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)

        // Show Toast when fragment is created
        Toast.makeText(requireContext(), "Fragment 1 - onCreate() called", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")
        if (rootView == null) {
            Timber.i("initializing the view hierarchy")
            rootView = layoutInflater.inflate(R.layout.fragment_1, container, false).apply {
                findViewById<Button>(R.id.btnNextFragment).apply {
                    setOnClickListener {
                        Timber.i("Button clicked: next Fragment")

                        // Show Toast when the button is clicked
                        Toast.makeText(requireContext(), "Fragment 1 - Navigating to Fragment 2", Toast.LENGTH_SHORT).show()

                        requireActivity().supportFragmentManager
                            .beginTransaction()
                            .replace(R.id.fragmentContainer, Fragment2.newInstance(), "fragmentTag")
                            .addToBackStack(null)
                            .commit()
                    }
                }
            }
        }

        // Show Toast when the view is created
        Toast.makeText(requireContext(), "Fragment 1 - onCreateView() called", Toast.LENGTH_SHORT).show()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        // Show Toast when the fragment's view is created
        Toast.makeText(requireContext(), "Fragment 1 - onViewCreated() called", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()

        // Show Toast when fragment is started
        Toast.makeText(requireContext(), "Fragment 1 - onStart() called", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()

        // Show Toast when fragment is resumed
        Toast.makeText(requireContext(), "Fragment 1 - onResume() called", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()

        // Show Toast when fragment is paused
        Toast.makeText(requireContext(), "Fragment 1 - onPause() called", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()

        // Show Toast when fragment is stopped
        Toast.makeText(requireContext(), "Fragment 1 - onStop() called", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        Timber.i("onDestroyView()")
        super.onDestroyView()

        // Show Toast when fragment view is destroyed
        Toast.makeText(requireContext(), "Fragment 1 - onDestroyView() called", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()

        // Show Toast when fragment is destroyed
        Toast.makeText(requireContext(), "Fragment 1 - onDestroy() called", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment1 {
            return Fragment1()
        }
    }
}
