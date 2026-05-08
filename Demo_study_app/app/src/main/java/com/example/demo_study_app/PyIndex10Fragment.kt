package com.example.demo_study_app

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment

class PyIndex10Fragment : Fragment() {

    private var listener: OnIndexCompletedListener? = null

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
        val view = inflater.inflate(R.layout.fragment_py_index10, container, false)

        val nextButton = view.findViewById<Button>(R.id.nextButton)
        val previousButton = view.findViewById<Button>(R.id.previousButton)
        val nestedScrollView = view.findViewById<NestedScrollView>(R.id.nestedScrollView)

        // Initially hide the Next button
        nextButton.visibility = View.GONE
        previousButton.visibility = View.GONE

        // Ensure child view height is ready before using it
        nestedScrollView.viewTreeObserver.addOnGlobalLayoutListener {
            val contentView = nestedScrollView.getChildAt(0)
            if (contentView != null) {
                val scrollHeight = contentView.measuredHeight - nestedScrollView.measuredHeight

                nestedScrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
                    if (scrollY >= scrollHeight) {
                        // Fully scrolled, show the Next button
                        nextButton.visibility = View.VISIBLE
                        previousButton.visibility = View.VISIBLE
                    } else {
                        // Not fully scrolled, hide the Next button
                        nextButton.visibility = View.GONE
                        previousButton.visibility = View.GONE
                    }
                }
            }
        }

        // Handle "Previous" button click
        previousButton.setOnClickListener {
            (activity as? PythonActivity)?.loadFragment(PyIndex9Fragment())
        }

        // Handle "Next" button click
        nextButton.setOnClickListener {
            listener?.onIndexCompleted()
            (activity as? PythonActivity)?.loadFragment(PyQuiz10Fragment())
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}
