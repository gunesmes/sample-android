package co.omisego.omgshop.pages.history.viewholder

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import co.omisego.omgshop.R
import co.omisego.omisego.model.pagination.Paginable
import co.omisego.omisego.model.transaction.list.Transaction
import co.omisego.omisego.model.transaction.list.TransactionSource
import kotlinx.android.synthetic.main.viewholder_transaction_record.view.*
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*


/*
 * OmiseGO
 *
 * Created by Phuchit Sirimongkolsathien on 22/6/2018 AD.
 * Copyright © 2017-2018 OmiseGO. All rights reserved.
 */
class TransactionHistoryViewHolder(itemView: View, val myAddress: String) : RecyclerView.ViewHolder(itemView) {
    private val dateFormat by lazy { SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.US) }

    fun bindItem(record: Transaction) {
        with(record) {
            val isSameAddress = isSameAddress(record.from)

            setTransactionDirection(isSameAddress)
            colorizedTransactionAmount(isSameAddress)
            record.setTransactionAddress(isSameAddress)
            record.status.colorizedTransactionStatus()
            record.from.formatTransactionAmount(isSameAddress)
            itemView.tvTransactionStatus.text = "- ${record.status.value.capitalize()}"
            itemView.tvTransactionDate.text = dateFormat.format(record.createdAt)
        }
    }

    private fun Transaction.setTransactionAddress(sameAddress: Boolean) {
        itemView.tvTransactionAddress.text = if (sameAddress) {
            this.to.address
        } else {
            this.from.address
        }
    }

    private fun colorizedTransactionAmount(sameAddress: Boolean) {
        val color = if (sameAddress) {
            ContextCompat.getColor(itemView.context, R.color.colorRed)
        } else {
            ContextCompat.getColor(itemView.context, R.color.colorGreen)
        }

        itemView.tvTransactionAmount.setTextColor(color)
    }

    private fun Paginable.Transaction.TransactionStatus.colorizedTransactionStatus() {
        val color = when (this) {
            Paginable.Transaction.TransactionStatus.PENDING -> ContextCompat.getColor(itemView.context, R.color.colorYellow)
            Paginable.Transaction.TransactionStatus.CONFIRMED -> ContextCompat.getColor(itemView.context, R.color.colorGreen)
            Paginable.Transaction.TransactionStatus.FAILED, Paginable.Transaction.TransactionStatus.UNKNOWN -> ContextCompat.getColor(itemView.context, R.color.colorRed)
        }

        itemView.tvTransactionStatus.setTextColor(color)
    }

    private fun TransactionSource.formatTransactionAmount(sameAddress: Boolean) {
        val amount = String.format("%.1f", this.amount.divide(this.token.subunitToUnit, RoundingMode.FLOOR))
        val prefix = if (sameAddress) {
            "-"
        } else {
            "+"
        }
        itemView.tvTransactionAmount.text = "$prefix $amount OMG"
    }

    private fun setTransactionDirection(sameAddress: Boolean) {
        val direction = if (sameAddress) "To" else "From"
        itemView.tvTransactionDirection.text = direction
    }

    private fun isSameAddress(source: TransactionSource) = myAddress == source.address
}
