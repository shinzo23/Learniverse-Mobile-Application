package com.example.demo_study_app

import Index
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.compose.ui.res.colorResource
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

class ExpandableIndexAdapter(private val indexList: List<Index>) : RecyclerView.Adapter<ExpandableIndexAdapter.ViewHolder>() {

    private val expandedState = mutableMapOf<Int, Boolean>()

    init {
        for (i in indexList.indices) {
            expandedState[i] = false // Initially all items are collapsed
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_index, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val index = indexList[position]
        holder.bind(index, position)
    }

    override fun getItemCount(): Int {
        return indexList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val indexTitleTextView: TextView = itemView.findViewById(R.id.indexTitleTextView)
        private val subIndexContainer: LinearLayout = itemView.findViewById(R.id.subIndexContainer)

        fun bind(index: Index, position: Int) {
            indexTitleTextView.text = index.title
            // Load Poppins fonts
            val poppinsRegular = ResourcesCompat.getFont(itemView.context, R.font.poppins_regular)
            val poppinsSemiBold = ResourcesCompat.getFont(itemView.context, R.font.poppins_semi_bold)


            // ✅ Customize index title appearance
            indexTitleTextView.textSize = 15f  // Set title font size
            indexTitleTextView.setTypeface(null, Typeface.BOLD)
            indexTitleTextView.typeface = poppinsSemiBold  // Apply Poppins Bold font
            indexTitleTextView.setTextColor(itemView.context.getColor(R.color.black))
            indexTitleTextView.setPadding(14, 8, 6, 7)  // Set padding

            // ✅ Add margin to index title
            val layoutParams = indexTitleTextView.layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.setMargins(8, 10, 8, 8)  // Left, Top, Right, Bottom margin
            indexTitleTextView.layoutParams = layoutParams

            // Toggle expand/collapse state
            val isExpanded = expandedState[position] ?: false
            subIndexContainer.visibility = if (isExpanded) View.VISIBLE else View.GONE

            subIndexContainer.removeAllViews()
            for (subIndex in index.subIndexes) {
                val textView = TextView(itemView.context)
                textView.text = subIndex

                // ✅ Customize subindex text appearance
                textView.textSize = 14f  // Set subindex font size
                textView.typeface = poppinsRegular  // Apply Poppins Regular font
                textView.setTextColor(itemView.context.getColor(R.color.dark_grey))  // Subindex color
                textView.setPadding(16, 4, 10, 4)  // Subindex padding

                // ✅ Add margin to subindex
                val subLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                subLayoutParams.setMargins(32, 4, 32, 4)  // Left, Top, Right, Bottom margin
                textView.layoutParams = subLayoutParams

                subIndexContainer.addView(textView)
            }

            // Toggle the expand/collapse state when title is clicked
            itemView.setOnClickListener {
                expandedState[position] = !isExpanded
                notifyItemChanged(position)
            }
        }

    }
}
