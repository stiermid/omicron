package org.omicron.mobile.core.designsystem

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class OmicronThemeTest {
    @Test
    fun lightTokensPreserveWebAliases() {
        assertEquals(OmicronLightColors.background, OmicronLightColors.backgroundAlt)
        assertEquals(OmicronLightColors.line, OmicronLightColors.background)
        assertEquals(OmicronRadiiTokens.button, OmicronRadiiTokens.radius5)
        assertEquals(OmicronRadiiTokens.input, OmicronRadiiTokens.radius9)
    }

    @Test
    fun darkTokensPreserveWebAliases() {
        assertNotEquals(OmicronDarkColors.background, OmicronDarkColors.backgroundAlt)
        assertNotEquals(OmicronDarkColors.foreground, OmicronDarkColors.foregroundAlt)
        assertEquals(OmicronRadiiTokens.cardSmall, OmicronRadiiTokens.radius10)
        assertEquals(15, OmicronRadiiTokens.radius15.value.toInt())
    }
}
