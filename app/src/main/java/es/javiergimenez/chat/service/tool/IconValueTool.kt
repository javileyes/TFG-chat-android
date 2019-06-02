package es.javiergimenez.chat.service.tool

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder.IconValue


object IconValueTool {


    var githubIconValue: IconValue = IconValue.GITHUB_FACE
    var androidIconValue: IconValue = IconValue.ANDROID
    var cellphoneIconValue: IconValue = IconValue.CELLPHONE_ANDROID
    var anvilIconValue: IconValue = IconValue.ANVIL
    var archIconValue: IconValue = IconValue.ARCH
    var barleyIconValue: IconValue = IconValue.BARLEY
    var baseballIconValue: IconValue = IconValue.BASEBALL
    var biohazardIconValue: IconValue = IconValue.BIOHAZARD
    var bowlingIconValue: IconValue = IconValue.BOWLING
    var bugIconValue: IconValue = IconValue.BUG
    var cakeIconValue: IconValue = IconValue.CAKE_VARIANT
    var candycaneIconValue: IconValue = IconValue.CANDYCANE
    var clubIconValue: IconValue = IconValue.CARDS_CLUB
    var diamonIconValue: IconValue = IconValue.CARDS_DIAMOND
    var spadeIconValue: IconValue = IconValue.CARDS_SPADE
    var heartIconValue: IconValue = IconValue.CARDS_HEART
    var bishopIconValue: IconValue = IconValue.CHESS_BISHOP
    var kingIconValue: IconValue = IconValue.CHESS_KING
    var knightIconValue: IconValue = IconValue.CHESS_KNIGHT
    var pawnIconValue: IconValue = IconValue.CHESS_PAWN
    var queenIconValue: IconValue = IconValue.CHESS_QUEEN
    var rookIconValue: IconValue = IconValue.CHESS_ROOK
    var maskIconValue: IconValue = IconValue.GUY_FAWKES_MASK
    var hopsIconValue: IconValue = IconValue.HOPS
    var ghostIconValue: IconValue = IconValue.GHOST
    var leafIconValue: IconValue = IconValue.LEAF
    var owlIconValue: IconValue = IconValue.OWL


    var iconValues: MutableList<IconValue> = mutableListOf()


    init {
        iconValues.add(githubIconValue)
        iconValues.add(androidIconValue)
        iconValues.add(cellphoneIconValue)
        iconValues.add(anvilIconValue)
        iconValues.add(archIconValue)
        iconValues.add(barleyIconValue)
        iconValues.add(baseballIconValue)
        iconValues.add(biohazardIconValue)
        iconValues.add(bowlingIconValue)
        iconValues.add(bugIconValue)
        iconValues.add(cakeIconValue)
        iconValues.add(candycaneIconValue)
        iconValues.add(clubIconValue)
        iconValues.add(diamonIconValue)
        iconValues.add(spadeIconValue)
        iconValues.add(heartIconValue)
        iconValues.add(bishopIconValue)
        iconValues.add(kingIconValue)
        iconValues.add(knightIconValue)
        iconValues.add(pawnIconValue)
        iconValues.add(queenIconValue)
        iconValues.add(rookIconValue)
        iconValues.add(maskIconValue)
        iconValues.add(hopsIconValue)
        iconValues.add(ghostIconValue)
        iconValues.add(leafIconValue)
        iconValues.add(owlIconValue)
    }

    fun getIconValueByText(text: String?): IconValue {
        return iconValues[Math.abs(text!!.substring(0, text.length - 1).hashCode() % iconValues.size)]
    }


}