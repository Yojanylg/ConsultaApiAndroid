package com.example.examen
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.examen.databinding.ItemproductoBinding
import com.example.examen.model.Producto
import com.squareup.picasso.Picasso

class ProductoViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemproductoBinding.bind(view)

    fun displayData(producto : Producto){
        Picasso.get().load(producto.image).resize(450,450).centerCrop().into(binding.ivproducto)
        binding.tvname.setText("Nombre: " + producto.name)
        binding.tvprice.setText("Precio: " + producto.price)
    }
}