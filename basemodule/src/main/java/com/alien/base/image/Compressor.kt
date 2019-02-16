package com.alien.base.image

object Compressor {

    fun computeSize(originalWidth: Int, originalHeight: Int): Int {
        val srcWidth = if (originalWidth % 2 == 1) originalWidth + 1 else originalWidth
        val srcHeight = if (originalHeight % 2 == 1) originalHeight + 1 else originalHeight

        val longSide = Math.max(srcWidth, srcHeight)
        val shortSide = Math.min(srcWidth, srcHeight)

        val scale = shortSide.toFloat() / longSide
        return if (scale <= 1 && scale > 0.5625) {
            if (longSide < 1664) {
                1
            } else if (longSide < 4990) {
                2
            } else if (longSide in 4991..10239) {
                4
            } else {
                if (longSide / 1280 == 0) 1 else longSide / 1280
            }
        } else if (scale <= 0.5625 && scale > 0.5) {
            if (longSide / 1280 == 0) 1 else longSide / 1280
        } else {
            Math.ceil(longSide / (1280.0 / scale)).toInt()
        }
    }
}