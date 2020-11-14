package com.example.samplekotlinapplication.responses

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class TestApiResponse(
    @SerializedName("response")
    @Expose
    var response: Response
)
{


}
