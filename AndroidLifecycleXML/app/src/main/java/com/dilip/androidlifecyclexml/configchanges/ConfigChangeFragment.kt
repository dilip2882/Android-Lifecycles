package com.dilip.androidlifecyclexml.configchanges

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.dilip.androidlifecyclexml.R
import timber.log.Timber

class ConfigChangeFragment : Fragment() {

    interface Listener {
        fun onUserInputChanged(userInput: String)
    }

    private var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate(); identity: ${System.identityHashCode(this)}")
        super.onCreate(savedInstanceState)

        // Show a toast message when the fragment is created
        Toast.makeText(context, "onCreate() - Fragment Created", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.i("onCreateView()")

        if (rootView == null) {
            Timber.i("initializing the view hierarchy")
            rootView = inflater.inflate(R.layout.fragment_config_change, container, false).apply {
                val editText = findViewById<EditText>(R.id.edtNumber)

                // Add text change listener
                editText.addTextChangedListener {
                    val userInput = it?.toString() ?: ""

                    // Show a toast message whenever text changes
                    Toast.makeText(context, "onTextChanged() - User input changed: $userInput", Toast.LENGTH_SHORT).show()

                    // Notify activity (if it implements the Listener interface)
                    val activity = requireActivity()
                    if (activity is Listener) {
                        activity.onUserInputChanged(userInput)
                    }
                }
            }
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("onViewCreated()")
        super.onViewCreated(view, savedInstanceState)

        // Show a toast message when the fragment's view is created
        Toast.makeText(context, "onViewCreated() - View Created", Toast.LENGTH_SHORT).show()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        Timber.i("onSaveInstanceState()")
        super.onSaveInstanceState(outState)

        // Show a toast message when the fragment's state is saved
        Toast.makeText(context, "onSaveInstanceState() - State Saved", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()

        // Show a toast message when the fragment is started
        Toast.makeText(context, "onStart() - Fragment Started", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()

        // Show a toast message when the fragment is resumed
        Toast.makeText(context, "onResume() - Fragment Resumed", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()

        // Show a toast message when the fragment is paused
        Toast.makeText(context, "onPause() - Fragment Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()

        // Show a toast message when the fragment is stopped
        Toast.makeText(context, "onStop() - Fragment Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        Timber.i("onDestroyView()")
        super.onDestroyView()

        // Show a toast message when the fragment's view is destroyed
        Toast.makeText(context, "onDestroyView() - View Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Timber.i("onDestroy(); identity: ${System.identityHashCode(this)}")
        super.onDestroy()

        // Show a toast message when the fragment is destroyed
        Toast.makeText(context, "onDestroy() - Fragment Destroyed", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): ConfigChangeFragment {
            return ConfigChangeFragment()
        }
    }
}
