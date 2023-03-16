package com.awais.raza.car.wmr.ui.fragment.premium.viewType

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.adapter.OnItemClickListener
import com.awais.raza.car.wmr.base.adapter.ViewType
import com.awais.raza.car.warm.databinding.PremiumListViewTypeBinding
import com.awais.raza.car.wmr.model.PremiumPackageData


class PremiumListViewType(
    private val data: PremiumPackageData,
    private val context: Context,
    private val listener: OnPremiumItemSelected
) : ViewType<PremiumPackageData> {
    override fun layoutId(): Int {
        return R.layout.premium_list_view_type
    }

    override fun data(): PremiumPackageData {
        return data
    }

    override fun isUserInteractionEnabled(): Boolean {
        return true
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun bind(bi: ViewDataBinding, position: Int, onClickListener: OnItemClickListener<*>) {
        (bi as PremiumListViewTypeBinding).also { binding ->
            binding.mainView.setOnClickListener {
                listener.onPremiumItemSelected(position, data.isChecked)
                setViewBackground(binding)
            }
            binding.radioBtn.isChecked = data.isChecked
            binding.radioBtn.setOnClickListener {
                listener.onPremiumItemSelected(position, data.isChecked)
                setViewBackground(
                    binding
                )
            }
            setViewBackground(binding)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setViewBackground(
        binding: PremiumListViewTypeBinding
    ) {
        if (data.isChecked) {
            binding.mainView.background =
                ContextCompat.getDrawable(
                    context,
                    R.drawable.card_view_selected_bg
                )
            binding.textViewTitle.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )
            binding.textViewDiscount.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.white
                )
            )

            binding.textViewPrice.setTextColor(
                ContextCompat.getColor(
                    context, R.color.white

                )
            )
            binding.radioBtn.buttonTintList =
                ContextCompat.getColorStateList(context, R.color.white)
            binding.radioBtn.isChecked = data.isChecked

        } else {
            binding.mainView.background =
                ContextCompat.getDrawable(context, R.drawable.card_view_unselected_bg)
            binding.textViewTitle.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.color_title
                )
            )
            binding.textViewDiscount.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.color_title
                )
            )

            binding.textViewPrice.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.color_title
                )
            )
            binding.radioBtn.buttonTintList =
                ContextCompat.getColorStateList(context, R.color.color_title)
            binding.radioBtn.isChecked = false
        }
    }
}

interface OnPremiumItemSelected {
    fun onPremiumItemSelected(position: Int, isChecked: Boolean)
}