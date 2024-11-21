package com.dilip.androidlifecyclexml.saveandrestore

import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.savedstate.SavedStateRegistryOwner
import com.google.android.material.checkbox.MaterialCheckBox
import com.dilip.androidlifecyclexml.BackgroundDetector
import com.dilip.androidlifecyclexml.CustomApplication
import com.dilip.androidlifecyclexml.R
import timber.log.Timber
import android.widget.Toast

class SaveAndRestoreActivity : AppCompatActivity() {

    private lateinit var backgroundDetector: BackgroundDetector
    private lateinit var viewModel: SaveAndRestoreViewModel
    private lateinit var txtTimeCount: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var txtCheckBoxCaption: TextView
    private lateinit var checkBoxMaterial: MaterialCheckBox
    private lateinit var txtCheckBoxMaterialCaption: TextView
    private lateinit var edtNumber: EditText
    private lateinit var btnAddNumber: Button
    private lateinit var recyclerNumbers: RecyclerView
    private lateinit var numbersAdapter: NumbersAdapter
    private lateinit var txtSum: TextView

    private var sum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.i("onCreate()")
        super.onCreate(savedInstanceState)

        // Show Toast when activity is created
        Toast.makeText(this, "Activity Created", Toast.LENGTH_SHORT).show()

        val application = this.applicationContext as Application
        backgroundDetector = (application as CustomApplication).backgroundDetector

        viewModel = ViewModelProvider(this, MyViewModelFactory(this)).get(SaveAndRestoreViewModel::class.java)

        setContentView(R.layout.activity_save_and_restore)

        txtTimeCount = findViewById(R.id.txtTimeCount)
        checkBox = findViewById(R.id.checkBox)
        txtCheckBoxCaption = findViewById(R.id.txtCheckBoxCaption)
        checkBoxMaterial = findViewById(R.id.checkBoxMaterial)
        txtCheckBoxMaterialCaption = findViewById(R.id.txtCheckBoxMaterialCaption)
        edtNumber = findViewById(R.id.edtNumber)
        btnAddNumber = findViewById(R.id.btnAddNumber)
        recyclerNumbers = findViewById(R.id.recycler)
        txtSum = findViewById(R.id.txtSum)

        recyclerNumbers.layoutManager = LinearLayoutManager(this)
        numbersAdapter = NumbersAdapter()
        recyclerNumbers.adapter = numbersAdapter

        savedInstanceState?.let {
            sum = it.getInt(SAVED_STATE_SUM)
            numbersAdapter.bindNumbers(it.getIntArray(SAVED_STATE_NUMBERS)!!.toList())
        }

        viewModel.secondsElapsed.observe(this) { secondsElapsed ->
            updateTimeCount(secondsElapsed)
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            updateCheckBoxCaption(isChecked)
        }

        checkBoxMaterial.setOnCheckedChangeListener { _, isChecked ->
            updateCheckBoxMaterialCaption(isChecked)
        }

        btnAddNumber.setOnClickListener {
            val number = try {
                edtNumber.text.toString().toInt()
            } catch (e: NumberFormatException) {
                return@setOnClickListener
            }
            numbersAdapter.addNumber(number)
            sum = numbersAdapter.numbers.sum()
            updateSum()
        }

        updateCheckBoxCaption(checkBox.isChecked)
        updateCheckBoxMaterialCaption(checkBoxMaterial.isChecked)
        updateSum()
        updateTimeCount(viewModel.secondsElapsed.value!!)

        // Show Toast when activity is created
        Toast.makeText(this, "Activity UI Created", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        Timber.i("onStart()")
        super.onStart()
        backgroundDetector.activityStarted()

        // Show Toast when activity is started
        Toast.makeText(this, "Activity Started", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        Timber.i("onStop()")
        super.onStop()
        backgroundDetector.activityStopped()

        // Show Toast when activity is stopped
        Toast.makeText(this, "Activity Stopped", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        Timber.i("onResume()")
        super.onResume()

        // Show Toast when activity is resumed
        Toast.makeText(this, "Activity Resumed", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        Timber.i("onPause()")
        super.onPause()

        // Show Toast when activity is paused
        Toast.makeText(this, "Activity Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        Timber.i("onDestroy()")
        super.onDestroy()

        // Show Toast when activity is destroyed
        Toast.makeText(this, "Activity Destroyed", Toast.LENGTH_SHORT).show()
    }

    override fun onTopResumedActivityChanged(isTopResumedActivity: Boolean) {
        super.onTopResumedActivityChanged(isTopResumedActivity)
        Timber.i("onTopResumedActivityChanged(); isTopResumed: $isTopResumedActivity")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVED_STATE_SUM, sum)
        outState.putIntArray(SAVED_STATE_NUMBERS, numbersAdapter.numbers.toIntArray())
    }

    private fun updateCheckBoxCaption(isChecked: Boolean) {
        if (isChecked) {
            txtCheckBoxCaption.text = "Checkbox is checked"
        } else {
            txtCheckBoxCaption.text = "Checkbox is unchecked"
        }
    }

    private fun updateCheckBoxMaterialCaption(isChecked: Boolean) {
        if (isChecked) {
            txtCheckBoxMaterialCaption.text = "CheckboxMaterial is checked"
        } else {
            txtCheckBoxMaterialCaption.text = "CheckboxMaterial is unchecked"
        }
    }

    private fun updateSum() {
        txtSum.text = "Total: ${sum.toString()}"
    }

    private fun updateTimeCount(seconds: Int) {
        txtTimeCount.text = "${seconds}s"
    }

    companion object {
        private const val SAVED_STATE_SUM = "sum"
        private const val SAVED_STATE_NUMBERS = "numbers"

        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, SaveAndRestoreActivity::class.java)
            context.startActivity(intent)
        }
    }
}

private class NumbersAdapter: RecyclerView.Adapter<NumberViewHolder>() {
    val numbers = mutableListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.number_recycler_item, parent, false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.numberText.text = numbers[position].toString()
    }

    override fun getItemCount() = numbers.size

    fun bindNumbers(numbers: List<Int>) {
        this.numbers.clear()
        this.numbers.addAll(numbers)
        notifyDataSetChanged()
    }

    fun addNumber(number: Int) {
        numbers.add(number)
        notifyItemInserted(itemCount)
    }
}

private class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val numberText: TextView = itemView.findViewById(R.id.txtNumber)
}

private class MyViewModelFactory(
    owner: SavedStateRegistryOwner,
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return SaveAndRestoreViewModel(handle) as T
    }
}
