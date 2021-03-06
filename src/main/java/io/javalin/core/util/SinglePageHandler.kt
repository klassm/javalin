/*
 * Javalin - https://javalin.io
 * Copyright 2017 David Åse
 * Licensed under Apache 2.0: https://github.com/tipsy/javalin/blob/master/LICENSE
 */

package io.javalin.core.util

import io.javalin.Context

class SinglePageHandler {

    private val pathPageMap = mutableMapOf<String, String>()

    fun add(path: String, filePath: String) {
        pathPageMap[path] = Util.getResource(filePath.removePrefix("/"))?.readText()
                ?: throw IllegalArgumentException("File at '$filePath' not found. Path should be relative to resource folder.")
    }

    fun handle(ctx: Context): Boolean { // this could be more idiomatic
        if (!ContextUtil.acceptsHtml(ctx)) return false
        for (entry in pathPageMap) {
            if (ctx.path().startsWith(entry.key)) {
                ctx.html(entry.value)
                return true
            }
        }
        return false
    }

}
