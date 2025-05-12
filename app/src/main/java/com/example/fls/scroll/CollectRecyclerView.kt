package com.example.fls.scroll

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fls.R

class CollectRecyclerView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val collectHelper = CollectScrollHelper()

    private var recyclerView : RecyclerView? = null

    private var minLeft: Int = 0

    private var maxLeft: Int = 0

    private var detailMaxWidth : Int = 0

    private var detailMeasuredWidth : Int = 0

    private var currentLeft = 0

    private var adapter: CollectDetailAdapter? = null


    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(context)
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_collect_recyclerview, this, true)
        recyclerView = findViewById(R.id.recyclerView)

        recyclerView?.layoutManager = linearLayoutManager

    }

    fun bindData(list: List<String>) {

        adapter = CollectDetailAdapter(dp2px(100F), list)
        recyclerView?.adapter = adapter

        recyclerView?.post {
            val llDetailItem = linearLayoutManager.getChildAt(0)?.findViewById<LinearLayout>(R.id.llDetail)
            maxLeft = llDetailItem?.paddingStart ?: 0
            detailMeasuredWidth = llDetailItem?.width ?: 0
            for (i in 0 until (llDetailItem?.childCount ?: 0)) {
                detailMaxWidth += (llDetailItem?.getChildAt(i)?.width ?: 0)
            }
            minLeft = (recyclerView?.width ?:0) - detailMaxWidth
            currentLeft = maxLeft
        }

    }


    var startX = 0.0F
    var startY = 0.0F

    private val touchSlop = ViewConfiguration.get(context).scaledTouchSlop

    private var direction = 0;// 0 竖向 1 横向 -1 都不是

    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
        when(e?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = e.x
                startY = e.y
                direction = -1
                parent?.requestDisallowInterceptTouchEvent(true)
            }

            MotionEvent.ACTION_MOVE -> {
                val distanceX = e.x - startX
                val distanceY = e.y - startY
                if (direction == -1) {
                    if (Math.abs(distanceX) > Math.abs(distanceY)) {
                        if (Math.abs(distanceX) > touchSlop) {
                            direction = 1
                        }
                    } else {
                        if (Math.abs(distanceY) > touchSlop) {
                            direction = 0
                        }
                    }
                }
                if (direction == 1) {
                    var result = true

                    parent?.requestDisallowInterceptTouchEvent(true)

                    val toPaddingStart = currentLeft + distanceX.toInt()
                    currentLeft = if (toPaddingStart < minLeft) {
                        result = false
                        minLeft
                    } else if (toPaddingStart > maxLeft) {
                        result = false
                        maxLeft
                    } else {
                        result = true
                        startX = e.x
                        toPaddingStart
                    }
                    adapter?.detailPaddingStart = currentLeft
                    adapter?.notifyDataSetChanged()

                    return if (result) true else {
                        parent?.requestDisallowInterceptTouchEvent(false)
                        super.dispatchTouchEvent(e)
                    }
                }

            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                direction = -1
                parent?.requestDisallowInterceptTouchEvent(false)
            }
        }
        return super.dispatchTouchEvent(e)
    }


    fun dp2px(dpValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}
