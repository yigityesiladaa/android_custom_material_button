package com.custombutton

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import androidx.core.content.ContextCompat
import com.custombutton.utils.dpToPx
import com.google.android.material.R.*
import com.google.android.material.button.MaterialButton
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel

enum class ButtonType { PRIMARY, SECONDARY }
enum class ButtonSize { SMALL, LARGE }

class CustomButton : MaterialButton {

    private var buttonType: ButtonType = ButtonType.PRIMARY
    private var customButtonSize: ButtonSize = ButtonSize.LARGE

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    ) {
        initializeToolbarAttributes(attributeSet)
        initView()
        val parentView = parent as? ViewGroup
        parentView?.addView(this)
    }

    private fun initializeToolbarAttributes(attrs: AttributeSet?) {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomButton)
            buttonType = when (typedArray.getInt(R.styleable.CustomButton_customButtonType, 0)) {
                0 -> ButtonType.PRIMARY
                1 -> ButtonType.SECONDARY
                else -> ButtonType.PRIMARY
            }

            customButtonSize = when (typedArray.getInt(R.styleable.CustomButton_customButtonSize, 0)) {
                0 -> ButtonSize.SMALL
                1 -> ButtonSize.LARGE
                else -> ButtonSize.LARGE
            }
            typedArray.recycle()
        }
    }

    private fun initView() {
        setButtonSize()
        setButtonType()
    }

    private fun setButtonType() {
        val quad = when (buttonType) {
            ButtonType.PRIMARY -> if (isEnabled) {
                Quad(
                    backgroundTintRes = R.color.primary_button_color,
                    iconAndTextColorRes = R.color.white,
                    strokeColorRes = android.R.color.transparent
                )
            } else {
                Quad(
                    backgroundTintRes = R.color.disabled_button_color,
                    iconAndTextColorRes = R.color.disabled_color,
                    strokeColorRes = android.R.color.transparent
                )
            }
            ButtonType.SECONDARY -> if (isEnabled) {
                Quad(
                    backgroundTintRes = android.R.color.white,
                    iconAndTextColorRes = R.color.primary_button_color,
                    strokeColorRes = R.color.primary_button_color
                )
            } else {
                Quad(
                    backgroundTintRes = R.color.white,
                    iconAndTextColorRes = R.color.disabled_color,
                    strokeColorRes = R.color.disabled_color
                )
            }
        }

        setupShapeAppearance()
        setupBackground(quad)
        setupIconAndTextColor(quad)
    }

    private fun setupShapeAppearance() {
        val radius = dpToPx(context,24)
        val shapeAppearanceModel = ShapeAppearanceModel.builder(
            context, null, attr.materialButtonStyle, style.Widget_MaterialComponents_Button
        ).setAllCorners(CornerFamily.ROUNDED, radius).build()

        background = MaterialShapeDrawable(shapeAppearanceModel)
    }

    private fun setupBackground(quad: Quad<Int, Int, Int>) {
        val fillColor = ContextCompat.getColor(context, quad.backgroundTintRes)
        (background as? MaterialShapeDrawable)?.fillColor = ColorStateList.valueOf(fillColor)

        val strokeColor = ContextCompat.getColor(context, quad.strokeColorRes)
        (background as? MaterialShapeDrawable)?.setStroke(dpToPx(context,1), strokeColor)
    }

    private fun setupIconAndTextColor(quad: Quad<Int, Int, Int>) {
        iconTintMode = PorterDuff.Mode.SRC_IN
        iconTint = ContextCompat.getColorStateList(context, quad.iconAndTextColorRes)
        setTextColor(ContextCompat.getColor(context, quad.iconAndTextColorRes))
    }


    private fun setButtonSize() {
        setPadding(64, 32, 64, 32)

        when (customButtonSize) {
            ButtonSize.SMALL -> applyStyle(R.style.CustomButton_Small)
            ButtonSize.LARGE -> applyStyle(R.style.CustomButton_Large)
        }
    }

    private fun applyStyle(styleRes: Int) {
        val theme = context.theme
        val typedArray = theme.obtainStyledAttributes(styleRes, R.styleable.CustomButton)

        val width = when (typedArray.getInt(R.styleable.CustomButton_customButtonSize, 1)) {
            0 -> LayoutParams.WRAP_CONTENT
            else -> LayoutParams.MATCH_PARENT
        }

        layoutParams = LayoutParams(width, LayoutParams.WRAP_CONTENT)
        typedArray.recycle()
    }

}
