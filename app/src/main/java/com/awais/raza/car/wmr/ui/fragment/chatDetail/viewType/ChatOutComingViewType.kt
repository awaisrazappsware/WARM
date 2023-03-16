package com.awais.raza.car.wmr.ui.fragment.chatDetail.viewType

import androidx.databinding.ViewDataBinding
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.adapter.OnItemClickListener
import com.awais.raza.car.wmr.base.adapter.ViewType
import com.awais.raza.car.warm.databinding.ChatOutcomingViewTypeBinding
import com.awais.raza.car.wmr.model.ChatData
import com.awais.raza.car.wmr.utils.getTime

class ChatOutComingViewType(
    private val data: ChatData
) : ViewType<ChatData> {
    override fun layoutId(): Int {
        return R.layout.chat_outcoming_view_type
    }

    override fun data(): ChatData {
        return data
    }

    override fun isUserInteractionEnabled(): Boolean {
        return true
    }

    override fun bind(bi: ViewDataBinding, position: Int, onClickListener: OnItemClickListener<*>) {
        (bi as ChatOutcomingViewTypeBinding).also { binding ->
            binding.textViewDate.text = data.time.getTime()
        }
    }


}