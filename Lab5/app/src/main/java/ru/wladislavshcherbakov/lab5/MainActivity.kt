import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.wladislavshcherbakov.lab5.R
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayout: GridLayout
    private val tileCount = 9 // Количество плиток
    private lateinit var tiles: Array<TextView>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayout = findViewById(R.id.gridLayout)
        createTiles()
        changeTileColors() // Изменяем цвета при запуске
    }

    private fun createTiles() {
        tiles = Array(tileCount) { TextView(this) }

        for (i in tiles.indices) {
            tiles[i].apply {
                layoutParams = GridLayout.LayoutParams().apply {
                    columnSpec = GridLayout.spec(i % 3, 1f) // Занимает 1/3 ширины
                    rowSpec = GridLayout.spec(i / 3, 1f) // Занимает 1/3 высоты
                }
                setBackgroundColor(Color.argb(255, 255, 255, 255)) // Белый цвет
                setOnClickListener { changeTileColors() } // Смена цвета по клику
            }
            gridLayout.addView(tiles[i])
        }
    }

    private fun changeTileColors() {
        val red = Random.nextInt(0, 256) // Генерация случайного красного
        val green = Random.nextInt(0, 256) // Генерация случайного зеленого
        val blue = Random.nextInt(0, 256) // Генерация случайного синего
        for (i in tiles.indices) {
            var alphaValue = (i + 1) * 25 // Уникальный альфа для каждого элемента
            val color = Color.argb(alphaValue, red, green, blue)
            tiles[i].setBackgroundColor(color)
        }
    }

    override fun onResume() {
        super.onResume()
        changeTileColors() // Смена цвета при возвращении к приложению
    }
}
