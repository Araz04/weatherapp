package com.example.weathertaskapp.ui.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.weathertaskapp.R

class DayForecastView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val imageView: ImageView
    private val textView1: TextView
    private val textView2: TextView
    private val textViewEnd: TextView

    private val defaultTextSizePx = context.resources.getDimensionPixelSize(R.dimen.text_size_main)
    private val defaultTextColor = context.getColor(R.color.text_color_white)

    init {
        // Set orientation to horizontal
        orientation = HORIZONTAL

        // Create and add ImageView
        imageView = ImageView(context)
        addView(imageView)

        // Create and add first TextView
        textView1 = TextView(context)
        textView1.text = "Yesterday"
        addView(textView1)

        // Create and add second TextView
        textView2 = TextView(context)
        textView2.text = "Cloudy"
        addView(textView2)

        // Create and add third TextView with layout params to align it to the end
        textViewEnd = TextView(context)
        textView2.text = "20 / 17"
        val layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParams.weight = 1f
        textViewEnd.layoutParams = layoutParams
        addView(textViewEnd)

        textView1.textSize = defaultTextSizePx.toFloat()
        textView1.setTextColor(defaultTextColor)

        textView2.textSize = defaultTextSizePx.toFloat()
        textView2.setTextColor(defaultTextColor)

        textViewEnd.textSize = defaultTextSizePx.toFloat()
        textViewEnd.setTextColor(defaultTextColor)

        // Apply custom attributes
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomView)
        val textSize = typedArray.getDimensionPixelSize(R.styleable.CustomView_textSize, -1)
        val textColor = typedArray.getColor(R.styleable.CustomView_textColor, -1)
        typedArray.recycle()

        if (textSize != -1) {
            textView1.textSize = textSize.toFloat()
            textView2.textSize = textSize.toFloat()
            textViewEnd.textSize = textSize.toFloat()
        }

        if (textColor != -1) {
            textView1.setTextColor(textColor)
            textView2.setTextColor(textColor)
            textViewEnd.setTextColor(textColor)
        }
    }

    // Setter methods for setting image and text values
    fun setImageResource(resourceId: Int) {
        imageView.setImageResource(resourceId)
    }

    fun setText1(text: String) {
        textView1.text = text
    }

    fun setText2(text: String) {
        textView2.text = text
    }

    fun setTextEnd(text: String) {
        textViewEnd.text = text
    }
}