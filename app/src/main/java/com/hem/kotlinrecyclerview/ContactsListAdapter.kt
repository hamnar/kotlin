package com.hem.kotlinrecyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_row_item.view.*
import java.util.*

class ContactsListAdapter(val context: Context, val listener: ContactsClickListener) :
    RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder>(),
    Filterable {
    private lateinit var contactItemList: Contacts
    private var contactListFiltered: List<ContactsItem> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactsListAdapter.ContactViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.user_row_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun getItemCount(): Int {
        return contactListFiltered.size
    }

    override fun onBindViewHolder(holder: ContactsListAdapter.ContactViewHolder, position: Int) {
        holder.bindItem(contactListFiltered[position], listener = listener)
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(
            contact: ContactsItem,
            listener: ContactsClickListener
        ) {
//            itemView.thumbnail.text = contact.image
            itemView.name.text = contact.name
            itemView.phone.text = contact.phone

        }
        //click listener for collapsed view

    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint.toString()

                contactListFiltered = if (charString.isEmpty()) {
                    contactItemList
                } else {
                    val filteredList = arrayListOf<ContactsItem>()
                    for (item in contactItemList) {
                        if (item.name.toLowerCase(Locale.getDefault()).contains(
                                charString.toLowerCase(
                                    Locale.getDefault()
                                )
                            )
                            || item.phone.toLowerCase(Locale.getDefault()).contains(
                                charString.toLowerCase(
                                    Locale.getDefault()
                                )
                            )
                        ) {
                            filteredList.add(item)
                        }
                    }
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = contactListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                contactListFiltered = results?.values as List<ContactsItem>
                notifyDataSetChanged()
            }

        }
    }

    fun setData(contact: Contacts) {
        this.contactItemList = contact
        this.contactListFiltered = contact
        notifyDataSetChanged()

    }

    interface ContactsClickListener {
        fun onItemClicked(contact: Contact)
    }


}