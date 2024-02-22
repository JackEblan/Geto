package com.android.geto.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API

class GetoIssueRegistry : IssueRegistry() {

    override val issues = listOf(
        TestMethodNameDetector.FORMAT,
        TestMethodNameDetector.PREFIX,
    )

    override val api: Int = CURRENT_API

    override val minApi: Int = 12

    override val vendor: Vendor = Vendor(
        vendorName = "Geto",
        feedbackUrl = "https://github.com/JackEblan/Geto/issues",
        contact = "https://github.com/JackEblan/Geto",
    )
}