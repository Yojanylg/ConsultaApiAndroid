package com.example.examen

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen.databinding.ActivityMainBinding
import com.example.examen.model.Producto
import com.example.examen.model.ProductoResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductoAdapter
    private val productoList = mutableListOf<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initRecyclerView()
        fetchData("products")

    }

    private fun initRecyclerView() {
        adapter = ProductoAdapter(productoList)
        binding.rvProducto.layoutManager = LinearLayoutManager(this)
        binding.rvProducto.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://peticiones.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun fetchData(query : String){
        CoroutineScope(Dispatchers.IO).launch {

            val call: Response<ProductoResponse> = getRetrofit().create(ProductoApiService::class.java).getAll(query)
            val cuerpo : ProductoResponse? = call.body()
            val results : List<Producto>

            if(cuerpo != null){
                results = cuerpo.results
                for(miproducto : Producto in results){
                    Log.v("Producto ", miproducto.toString())
                }
            }

            runOnUiThread{
                if(call.isSuccessful){
                    val productosL = cuerpo?.results ?: emptyList()
                    productoList.clear()
                    productoList.addAll(productosL)
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

}