package com.example.demo_study_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment

class PyIndex1Fragment : Fragment() {

    private var listener: OnIndexCompletedListener? = null
    private lateinit var nestedScrollView: NestedScrollView
    private lateinit var nextButton: Button

    interface OnIndexCompletedListener {
        fun onIndexCompleted()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnIndexCompletedListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement OnIndexCompletedListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_py_index1, container, false)

        nestedScrollView = view.findViewById(R.id.nestedScrollView)
        nextButton = view.findViewById(R.id.nextButton)
        nextButton.visibility = View.GONE // Initially hide the button

        nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (!nestedScrollView.canScrollVertically(1)) {
                nextButton.visibility = View.VISIBLE // Show when scrolled to bottom
            } else {
                nextButton.visibility = View.GONE // Hide if user scrolls up
            }
        }

        nextButton.setOnClickListener {
            listener?.onIndexCompleted()
            Toast.makeText(requireContext(), "There are 7 Questions, 5 of 7 should be correct to proceed further", Toast.LENGTH_LONG).show()
            (activity as? PythonActivity)?.loadFragment(PyQuiz1Fragment())
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
