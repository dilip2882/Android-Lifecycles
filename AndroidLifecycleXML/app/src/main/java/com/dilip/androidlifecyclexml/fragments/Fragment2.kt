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

class Fragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)

        // Show Toast when fragment is created
        Toast.makeText(requireContext(), "Fragment 2 - onCreate() called", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")

        // Show Toast when the view is created
        Toast.makeText(requireContext(), "Fragment 2 - onCreateView() called", Toast.LENGTH_SHORT).show()

        return layoutInflater.inflate(R.layout.fragment_2, container, false).apply {
            findViewById<Button>(R.id.btnPreviousFragment).setOnClickListener {
                Timber.i("Button clicked: previous Fragment")

                // Show Toast when navigating back to the previous fragment
                Toast.makeText(requireContext(), "Fragment 2 - Navigating to Previous Fragment", Toast.LENGTH_SHORT).show()

                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        // Show Toast when the fragment's view is created
        Toast.makeText(requireContext(), "Fragment 2 - onViewCreated() called", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()

        // Show Toast when fragment is started
        Toast.makeText(requireContext(), "Fragment 2 - onStart() called", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()

        // Show Toast when fragment is resumed
        Toast.makeText(requireContext(), "Fragment 2 - onResume() called", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()

        // Show Toast when fragment is paused
        Toast.makeText(requireContext(), "Fragment 2 - onPause() called", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()

        // Show Toast when fragment is stopped
        Toast.makeText(requireContext(), "Fragment 2 - onStop() called", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        Timber.i("onDestroyView()")
        super.onDestroyView()

        // Show Toast when fragment view is destroyed
        Toast.makeText(requireContext(), "Fragment 2 - onDestroyView() called", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()

        // Show Toast when fragment is destroyed
        Toast.makeText(requireContext(), "Fragment 2 - onDestroy() called", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment2 {
            return Fragment2()
        }
    }
}
