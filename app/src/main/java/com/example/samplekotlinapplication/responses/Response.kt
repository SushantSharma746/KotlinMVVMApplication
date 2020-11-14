package com.example.samplekotlinapplication.responses

import java.util.ArrayList

class Response(
    var docs: ArrayList<Docs>? = null

){
    override fun toString(): String {
        return "Response(docs=$docs)"
    }
}