package com.example.demo_study_app

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    private lateinit var searchBar: EditText
    private lateinit var pythonCourse: LinearLayout
    private lateinit var javaCourse: LinearLayout
    private lateinit var htmlCourse: LinearLayout
    private lateinit var cssCourse: LinearLayout
    private lateinit var jsCourse: LinearLayout
    private lateinit var tvProgrammingLanguages: TextView
    private lateinit var tvWebDevelopment: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initialize views
        searchBar = view.findViewById(R.id.searchBar)
        pythonCourse = view.findViewById(R.id.python_course)
        javaCourse = view.findViewById(R.id.java_course)
        htmlCourse = view.findViewById(R.id.html_course)
        cssCourse = view.findViewById(R.id.css_course)
        jsCourse = view.findViewById(R.id.js_course)
        tvProgrammingLanguages = view.findViewById(R.id.tvProgrammingLanguages)
        tvWebDevelopment = view.findViewById(R.id.tvWebDevelopment)

        // Set click listeners for courses
        pythonCourse.setOnClickListener {
            Toast.makeText(requireContext(), "Python Course Selected!", Toast.LENGTH_SHORT).show()
            val intent = Intent(requireContext(), PythonIndexActivity::class.java)
            startActivity(intent)
        }

        javaCourse.setOnClickListener {
            Toast.makeText(requireContext(), "Java Course Selected!", Toast.LENGTH_SHORT).show()
            // Add intent for Java course activity if needed
        }

        htmlCourse.setOnClickListener {
            Toast.makeText(requireContext(), "HTML Course Selected!", Toast.LENGTH_SHORT).show()
            // Add intent for HTML course activity if needed
        }

        cssCourse.setOnClickListener {
            Toast.makeText(requireContext(), "CSS Course Selected!", Toast.LENGTH_SHORT).show()
            // Add intent for CSS course activity if needed
        }

        jsCourse.setOnClickListener {
            Toast.makeText(requireContext(), "JavaScript Course Selected!", Toast.LENGTH_SHORT).show()
            // Add intent for JavaScript course activity if needed
        }

        // Add search functionality
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterCourses(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }

    private fun filterCourses(query: String) {
        val programmingCourses = listOf(
            Pair(pythonCourse, "Python"),
            Pair(javaCourse, "Java")
        )

        val webDevelopmentCourses = listOf(
            Pair(htmlCourse, "HTML"),
            Pair(cssCourse, "CSS"),
            Pair(jsCourse, "JavaScript")
        )

        // Filter programming courses

        
        var hasVisibleProgrammingCourses = false
        for ((courseView, courseName) in programmingCourses) {
            if (courseName.contains(query, ignoreCase = true)) {
                courseView.visibility = View.VISIBLE
                hasVisibleProgrammingCourses = true
            } else {
                courseView.visibility = View.GONE
            }
        }

        // Filter web development courses
        var hasVisibleWebDevelopmentCourses = false
        for ((courseView, courseName) in webDevelopmentCourses) {
            if (courseName.contains(query, ignoreCase = true)) {
                courseView.visibility = View.VISIBLE
                hasVisibleWebDevelopmentCourses = true
            } else {
                courseView.visibility = View.GONE
            }
        }

        // Show/hide section headers based on visibility of courses
        tvProgrammingLanguages.visibility = if (hasVisibleProgrammingCourses) View.VISIBLE else View.GONE
        tvWebDevelopment.visibility = if (hasVisibleWebDevelopmentCourses) View.VISIBLE else View.GONE

        // Adjust layout for programming courses
        if (hasVisibleProgrammingCourses) {
            val params = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                marginEnd = 8.dpToPx(requireContext())
            }

            // Apply params to the parent LinearLayout of the CardView
            (pythonCourse.parent as? LinearLayout)?.layoutParams = params
            (javaCourse.parent as? LinearLayout)?.layoutParams = params
        }

        // Adjust layout for web development courses
        if (hasVisibleWebDevelopmentCourses) {
            val params = LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1f
            ).apply {
                marginEnd = 8.dpToPx(requireContext())
            }

            // Apply params to the parent LinearLayout of the CardView
            (htmlCourse.parent as? LinearLayout)?.layoutParams = params
            (cssCourse.parent as? LinearLayout)?.layoutParams = params
            (jsCourse.parent as? LinearLayout)?.layoutParams = params
        }
    }

    // Extension function to convert dp to pixels
    private fun Int.dpToPx(context: android.content.Context): Int {
        return (this * context.resources.displayMetrics.density).toInt()
    }
}