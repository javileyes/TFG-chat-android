package es.javiergimenez.chat.service.tool


object ColorTool {

    var pinkColor: Int = es.javiergimenez.chat.R.color.pink
    var yellowColor: Int = es.javiergimenez.chat.R.color.yellow
    var brownColor: Int = es.javiergimenez.chat.R.color.brown
    var blue2Color: Int = es.javiergimenez.chat.R.color.blue2
    var cyanColor: Int = es.javiergimenez.chat.R.color.cyan
    var cyan2Color: Int = es.javiergimenez.chat.R.color.cyan2
    var greenColor: Int = es.javiergimenez.chat.R.color.green
    var purpleColor: Int = es.javiergimenez.chat.R.color.purple
    var redColor: Int = es.javiergimenez.chat.R.color.red
    var orangeColor: Int = es.javiergimenez.chat.R.color.orange

    var colorResources: MutableList<Int> = mutableListOf()


    init {
        colorResources.add(pinkColor)
        colorResources.add(yellowColor)
        colorResources.add(brownColor)
        colorResources.add(blue2Color)
        colorResources.add(cyanColor)
        colorResources.add(cyan2Color)
        colorResources.add(greenColor)
        colorResources.add(purpleColor)
        colorResources.add(redColor)
        colorResources.add(orangeColor)
    }

    fun getColorResourceByText(text: String?): Int {
        return colorResources[Math.abs(text!!.substring(1).hashCode() % colorResources.size)]
    }

}