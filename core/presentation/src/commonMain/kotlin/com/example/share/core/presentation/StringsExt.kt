package com.example.share.core.presentation

import androidx.compose.runtime.Composable
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.stringResource
import dev.icerock.moko.resources.desc.ResourceFormatted
import dev.icerock.moko.resources.desc.StringDesc


@Composable
fun appString(id: StringResource): String {
    return stringResource(id)
}

@Composable
fun appString(id: StringResource, vararg args: Any): String {
    return StringDesc.ResourceFormatted(
        id,
        *args.map { it.toString() }.toTypedArray()
    ).localized()
}


