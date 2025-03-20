import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.wladislavshcherbakov.lab12.Product
import ru.wladislavshcherbakov.lab12.R

class ProductAdapter(
    private val products: List<Product>,
    private val context: Context
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // Внутренний класс ViewHolder
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.title)
        val price: TextView = itemView.findViewById(R.id.subhead)
        val imageProduct: ImageView = itemView.findViewById(R.id.header_image)
        val addImage: ImageButton = itemView.findViewById(R.id.buttonAddToCart)
    }

    // Создание ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    // Связывание данных с ViewHolder
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        // Устанавливаем данные в элементы интерфейса
        holder.name.text = product.name.text
        holder.price.text = product.price.text
        holder.imageProduct.setImageDrawable(product.imageProduct.drawable)

        // Устанавливаем иконку кнопки в зависимости от состояния
        if (product.isAddedToCart) {
            holder.addImage.setImageResource(R.drawable.shopping_cart)
            holder.addImage.backgroundTintList = ContextCompat.getColorStateList(context, R.color.green)
        } else {
            holder.addImage.setImageResource(R.drawable.shopping_cart_add)
            holder.addImage.backgroundTintList = ContextCompat.getColorStateList(context, R.color.light_blue)
        }

        // Обработка нажатия на кнопку
        holder.addImage.setOnClickListener {
            // Меняем состояние товара
            product.isAddedToCart = !product.isAddedToCart

            // Показываем сообщение пользователю
            if (product.isAddedToCart) {
                Toast.makeText(context, "Добавлено: ${product.name.text}", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Удалено: ${product.name.text}", Toast.LENGTH_SHORT).show()
            }

            // Обновляем иконку кнопки
            if (product.isAddedToCart) {
                holder.addImage.setImageResource(R.drawable.shopping_cart)
                holder.addImage.backgroundTintList = ContextCompat.getColorStateList(context, R.color.green)
            } else {
                holder.addImage.setImageResource(R.drawable.shopping_cart_add)
                holder.addImage.backgroundTintList = ContextCompat.getColorStateList(context, R.color.light_blue)
            }
        }
    }

    // Количество элементов в списке
    override fun getItemCount(): Int {
        return products.size
    }
}