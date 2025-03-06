package com.example.examen.model

data class ProductoResponse(val page: Int,
                            val per_page: Int,
                            val total: Int,
                            val results: List<Producto>
)