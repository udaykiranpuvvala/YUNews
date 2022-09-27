package com.unik.yunews.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.unik.yunews.R
import com.unik.yunews.databinding.SingleCategoryBinding
import com.unik.yunews.models.CategoryModel

class CategoriesAdapter(val myContext: Context, val categoriesList: ArrayList<CategoryModel>) : RecyclerView.Adapter<CategoriesAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: SingleCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(categoryModel: CategoryModel) {
            binding.apply {
                txtTitle.text = categoryModel.categoryTitle
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
        val binding: SingleCategoryBinding =
            DataBindingUtil.inflate(view, R.layout.single_category, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.ivCategory.setImageDrawable(ContextCompat.getDrawable(myContext,categoriesList[holder.adapterPosition].categoryImage))
        holder.bind(categoriesList[holder.adapterPosition])
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}