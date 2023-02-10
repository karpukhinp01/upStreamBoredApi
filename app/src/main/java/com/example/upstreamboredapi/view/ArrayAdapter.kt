package com.example.upstreamboredapi.view

import android.content.Context
import android.widget.ArrayAdapter
import com.example.upstreamboredapi.model.ActionActivity

open class CardsAdapter(context: Context, resourceId: Int, actionActivities: List<ActionActivity>): ArrayAdapter<ActionActivity>(context, resourceId, actionActivities) {
}