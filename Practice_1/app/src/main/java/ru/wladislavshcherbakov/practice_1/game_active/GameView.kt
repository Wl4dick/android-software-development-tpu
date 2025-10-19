package ru.wladislavshcherbakov.practice_1.game_active

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import ru.wladislavshcherbakov.practice_1.objects.Cat
import ru.wladislavshcherbakov.practice_1.objects.Cheese
import ru.wladislavshcherbakov.practice_1.objects.Mouse
import kotlin.math.max
import kotlin.math.min

class GameView(
    context: Context,
    private val onGameOver: (Int) -> Unit
) : View(context) {

    var onCheeseEaten: (() -> Unit)? = null
    var onScoreChanged: ((Int) -> Unit)? = null

    private val mouse = Mouse(200f, 200f)
    private val cats = mutableListOf<Cat>()
    private val cheeses = mutableListOf<Cheese>()
    private var score = 0

    private val paintMouse = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.DKGRAY }
    private val paintCat = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.RED }
    private val paintCheese = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.YELLOW }
    private val scorePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.WHITE; textSize = 48f
    }

    private var running = false
    private val frameRunnable = object : Runnable {
        override fun run() {
            if (!running) return
            updatePhysics()
            invalidate()
            postDelayed(this, 16L) // ~60 FPS
        }
    }

    init {
        spawnCats(3)
        postDelayed({ randomSpawnCheese() }, 800L)
    }

    fun resume() { running = true; post(frameRunnable) }
    fun pause() { running = false; removeCallbacks(frameRunnable) }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mouse.x = w / 2f
        mouse.y = h / 2f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Сыры
        cheeses.forEach { c ->
            paintCheese.alpha = (c.alpha * 255).toInt()
            canvas.drawCircle(c.x, c.y, c.radius, paintCheese)
        }

        // Кошки
        cats.forEach { cat ->
            canvas.drawCircle(cat.x, cat.y, cat.radius, paintCat)
        }

        // Мышь
        canvas.drawCircle(mouse.x, mouse.y, mouse.radius, paintMouse)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                // Мышь следует за пальцем с плавностью (speed)
                mouse.x += (event.x - mouse.x) * mouse.speed
                mouse.y += (event.y - mouse.y) * mouse.speed

                // Не даём выйти за экран
                mouse.x = min(max(mouse.x, mouse.radius), width - mouse.radius)
                mouse.y = min(max(mouse.y, mouse.radius), height - mouse.radius)

                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun updatePhysics() {
        moveCats()
        checkCheeseCollisions()
        checkCatCollisions()
        animateCheeseFade()
        maybeSpawnCheese()
    }

    private fun moveCats() {
        val w = width.toFloat(); val h = height.toFloat()
        cats.forEach { cat ->
            cat.x += cat.vx; cat.y += cat.vy
            if (cat.x - cat.radius < 0 || cat.x + cat.radius > w) cat.vx = -cat.vx
            if (cat.y - cat.radius < 0 || cat.y + cat.radius > h) cat.vy = -cat.vy
        }
    }

    private fun checkCheeseCollisions() {
        cheeses.forEach { cheese ->
            if (collides(mouse.x, mouse.y, mouse.radius, cheese.x, cheese.y, cheese.radius)) {
                score += 1
                onScoreChanged?.invoke(score)
                onCheeseEaten?.invoke()
                // Запускаем исчезновение сыра
                if (cheese.alpha == 1f) cheese.alpha = 0.99f
            }
        }
    }

    private fun animateCheeseFade() {
        val iterator = cheeses.iterator()
        while (iterator.hasNext()) {
            val c = iterator.next()
            if (c.alpha < 1f) {
                c.alpha -= 0.08f
                if (c.alpha <= 0f) iterator.remove()
            }
        }
    }

    private fun maybeSpawnCheese() {
        if (cheeses.size < 3 && Math.random() < 0.02) randomSpawnCheese()
    }

    private fun checkCatCollisions() {
        for (cat in cats) {
            if (collides(mouse.x, mouse.y, mouse.radius, cat.x, cat.y, cat.radius)) {
                pause()
                onGameOver(score)
                break
            }
        }
    }

    private fun collides(x1: Float, y1: Float, r1: Float, x2: Float, y2: Float, r2: Float): Boolean {
        val dx = x1 - x2; val dy = y1 - y2
        return dx * dx + dy * dy <= (r1 + r2) * (r1 + r2)
    }

    private fun spawnCats(count: Int) {
        val w = width.takeIf { it > 0 } ?: 800
        val h = height.takeIf { it > 0 } ?: 1200
        repeat(count) {
            val vx = (Math.random().toFloat() * 6f + 2f) * if (Math.random() < 0.5) 1 else -1
            val vy = (Math.random().toFloat() * 6f + 2f) * if (Math.random() < 0.5) 1 else -1
            cats += Cat(
                x = (Math.random().toFloat() * (w - 120) + 60),
                y = (Math.random().toFloat() * (h - 120) + 60),
                radius = 45f, vx = vx, vy = vy
            )
        }
    }

    private fun randomSpawnCheese() {
        val w = width.takeIf { it > 0 } ?: 800
        val h = height.takeIf { it > 0 } ?: 1200
        cheeses += Cheese(
            x = (Math.random().toFloat() * (w - 120) + 60),
            y = (Math.random().toFloat() * (h - 120) + 60),
            radius = 30f,
            alpha = 1f
        )
    }
}