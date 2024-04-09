package com.example.weathertaskapp.ui.dialogs

import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.weathertaskapp.R

class SimpleErrorMessageDialog : DialogFragment() {
    private var positiveClickListener: View.OnClickListener? = null
    private var negativeClickListener: View.OnClickListener? = null

    var title: String = ""
    var message: String = ""
    var positiveButtonText = "";
    var isPositiveVisible = false;
    var negativeButtonText = "";
    var isNegativeVisible = false;
    private var tvMessage: TextView? = null
    private var btnPositive: TextView? = null
    private var btnNegative: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        configureDialog()
        val view: View = inflater.inflate(R.layout.dialog_app_error, container, false)
        initView(view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (positiveClickListener != null) {
            btnPositive?.setOnClickListener { positiveClickListener!!.onClick(btnPositive) }
        }
        if (negativeClickListener != null) {
            btnNegative?.setOnClickListener { negativeClickListener!!.onClick(btnNegative) }
        }
    }

    private fun configureDialog() {
        if (dialog!!.window != null) {
            dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)
            dialog!!.window!!.setBackgroundDrawableResource(R.drawable.custom_dialog_bg)
//            dialog!!.window!!.attributes.windowAnimations = R.style.DialogAnimation
        }
        isCancelable = false
        dialog!!.setCanceledOnTouchOutside(false)
    }

    fun setDialogView(
        message: String,
        positiveButtonText: String,
        isPositiveVisible: Boolean,
        negativeButtonText: String,
        isNegativeVisible: Boolean
    ) {
        this.message = message;
        this.positiveButtonText = positiveButtonText;
        this.isPositiveVisible = isPositiveVisible;
        this.negativeButtonText = negativeButtonText;
        this.isNegativeVisible = isNegativeVisible;
    }

    private fun initView(view: View) {
        //todo add view
        tvMessage = view.findViewById(R.id.tvDialogMessage)
        btnPositive = view.findViewById(R.id.btnPositive)
        btnNegative = view.findViewById(R.id.btnNegative)
        initMessage()
        initPositiveButton()
        initNegativeButton()
    }

    private fun initMessage() {
        if (message.isNotBlank()) {
            tvMessage?.text = message
        }
    }

    private fun initPositiveButton() {
        if (isPositiveVisible) {
            btnPositive?.visibility = View.VISIBLE
            btnPositive?.text = positiveButtonText
        } else {
            btnPositive?.visibility = View.GONE
        }
    }

    private fun initNegativeButton() {
        if (isNegativeVisible) {
            btnNegative?.visibility = View.VISIBLE
            btnNegative?.text = negativeButtonText
        } else {
            btnNegative?.visibility = View.GONE
        }
    }

    fun setPositiveClickListener(clickListener: View.OnClickListener) {
        positiveClickListener = clickListener
    }

    fun setNegativeClickListener(clickListener: View.OnClickListener) {
        negativeClickListener = clickListener
    }

    override fun onResume() {
        if (dialog!!.window != null) {
            val params: ViewGroup.LayoutParams = dialog!!.window!!.attributes
            params.width = RelativeLayout.LayoutParams.MATCH_PARENT
            params.height = RelativeLayout.LayoutParams.WRAP_CONTENT
            dialog!!.window!!.attributes = params as WindowManager.LayoutParams
        }
        super.onResume()
    }

    public fun showDialog(){
        this.show(parentFragmentManager, SimpleErrorMessageDialog::class.java.simpleName)
    }
}