package com.awais.raza.car.wmr.ui.fragment.chatList.viewType

import androidx.databinding.ViewDataBinding
import androidx.preference.PreferenceManager
import com.awais.raza.car.warm.R
import com.awais.raza.car.wmr.base.adapter.OnItemClickListener
import com.awais.raza.car.wmr.base.adapter.ViewType
import com.awais.raza.car.warm.databinding.ChatListViewTypeBinding
import com.awais.raza.car.wmr.model.ChatData
import com.awais.raza.car.wmr.utils.getTime
import com.awais.raza.car.wmr.utils.gone
import com.awais.raza.car.wmr.utils.show

class ChatListViewType(
    private val data: ChatData,
) : ViewType<ChatData> {
    override fun layoutId(): Int {
        return R.layout.chat_list_view_type
    }

    override fun data(): ChatData {
        return data
    }

    override fun isUserInteractionEnabled(): Boolean {
        return true
    }

    override fun bind(bi: ViewDataBinding, position: Int, onClickListener: OnItemClickListener<*>) {
        (bi as ChatListViewTypeBinding).also { binding ->

            val prefs = PreferenceManager.getDefaultSharedPreferences(binding.ImageViewUserImage.context)

            val lastMessage = prefs.getString(data.user, "")
            if(lastMessage == data.message){
                binding.cardViewNewMessage.gone()
            }else{
                binding.cardViewNewMessage.show()
            }
            if(data.isGroup){
                binding.ImageViewUserImage.setImageResource(R.drawable.ic_chat_user_group)
            }else{
                binding.ImageViewUserImage.setImageResource(R.drawable.ic_chat_user)
            }
            binding.textViewDate.text = data.time.getTime()
        }
    }


}