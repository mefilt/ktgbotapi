package dev.inmo.tgbotapi.webapps

external interface ThemeParams {
    @JsName("bg_color")
    val backgroundColor: HEXColor?
    @JsName("secondary_bg_color")
    val secondaryBackgroundColor: HEXColor?
    @JsName("text_color")
    val textColor: HEXColor?
    @JsName("hint_color")
    val hintColor: HEXColor?
    @JsName("link_color")
    val linkColor: HEXColor?
    @JsName("button_color")
    val buttonColor: HEXColor?
    @JsName("button_text_color")
    val buttonTextColor: HEXColor?

    @JsName("bg_color")
    val backgroundColorHex: Color.Hex?
    @JsName("secondary_bg_color")
    val secondaryBackgroundColorHex: Color.Hex?
    @JsName("text_color")
    val textColorHex: Color.Hex?
    @JsName("hint_color")
    val hintColorHex: Color.Hex?
    @JsName("link_color")
    val linkColorHex: Color.Hex?
    @JsName("button_color")
    val buttonColorHex: Color.Hex?
    @JsName("button_text_color")
    val buttonTextColorHex: Color.Hex?
}
