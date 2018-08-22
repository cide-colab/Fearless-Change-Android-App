package de.thkoeln.fherborn.fearlesschange.v2.data.persistance.cardkeyword

import de.thkoeln.fherborn.fearlesschange.persistance.models.CardKeyword
import de.thkoeln.fherborn.fearlesschange.v2.data.persistance.DataInitializer

class CardKeywordInitializer: DataInitializer<CardKeyword>("card_keyword") {

    override fun getItemValues(item: CardKeyword?) = hashMapOf<String, Any?>(
        "cardId" to item?.cardId,
        "keywordId" to item?.keywordId
    )

    private fun getValues() = hashMapOf(
            0 to listOf(1,2,3,4,5,6),
            1 to listOf(7,8,9,10,11,12,13,14),
            2 to listOf(15,16,17,18,19,20,21),
            3 to listOf(22,23,24,25,26,27,28),
            4 to listOf(29,30,31,32),
            5 to listOf(33,34,35,36,37,38),
            6 to listOf(39,40,41,42),
            7 to listOf(43,44,45,46,47,48,49,50,51),
            8 to listOf(52,53,54,55,56,57,58),
            9 to listOf(59,60,61,62,63,64,65,66,67,68,69,70,71,72),
            10 to listOf(73,74,75,76,77,78),
            11 to listOf(79,80,81,82,83,84,85),
            12 to listOf(86,87,88,89,90,91,92,93,94,95,96,97,98,99),
            13 to listOf(100,102,103,104,105,106),
            14 to listOf(107,108,109,110,111,112,113,114),
            15 to listOf(115,116,117,118,119,120,121,122,123,124),
            16 to listOf(125,126,127,128,129,130,131,132),
            17 to listOf(133,134,135,136,137,138,139,140),
            18 to listOf(141,142,143,144,145,146,147,148,149),
            19 to listOf(150,151,152,153,154,155,156,157),
            20 to listOf(158,159,160,161,162,163,164,165,166,167,168),
            21 to listOf(169,170,171,172,173,174,175,176,177),
            22 to listOf(178,179,180,181,182,183,184,185,186,187),
            23 to listOf(188,189,190,191,192,193,194,195),
            24 to listOf(196,197,198,199),
            25 to listOf(200,201,202,203,204,205,206,207),
            26 to listOf(208,209,210,211,212,213,214,215),
            27 to listOf(216,217,218,219,220,221,222,223),
            28 to listOf(224,225,226,227,228,229,230,231),
            29 to listOf(232,233,234,235,236,237,238),
            30 to listOf(239,240,241,242,243,244,245),
            31 to listOf(246,247,248,249,250,251,252,253,254),
            32 to listOf(255,256,257,258,259,260,261,262),
            33 to listOf(263,264,265,266,267,268,269,270),
            34 to listOf(271,272,273,274,275,276,277,278,279),
            35 to listOf(280,281,282,283,284,285,286,287,288),
            36 to listOf(289,290,291,292,293,294,295,296,297,298,299),
            37 to listOf(300,301,302,303,304,305,306),
            38 to listOf(307,308,309,310,311,312,313,314,315,316,317),
            39 to listOf(318,319,320,321,322,323),
            40 to listOf(324,325,326,327,328,329,330,331),
            41 to listOf(410,411,412),
            42 to listOf(332,333,334),
            43 to listOf(335,336,337,338),
            44 to listOf(339,340,341),
            45 to listOf(342,343,344,345),
            46 to listOf(346,347,348,349,350,351,352,353),
            47 to listOf(354,355,356,357,358,359),
            48 to listOf(360,361,362,363,364,365,366),
            49 to listOf(367,368,369,370),
            50 to listOf(371,372,373,374,375),
            51 to listOf(376,377,378),
            52 to listOf(379,380,381,382),
            53 to listOf(383,384,385),
            54 to listOf(386,387,388),
            55 to listOf(389,390,391,392),
            56 to listOf(393,394,395,396),
            57 to listOf(397,398,399,400),
            58 to listOf(401,402,403),
            59 to listOf(404,405,406),
            60 to listOf(407,408,409)
    )

    override fun getItems() = getValues().flatMap { cardKeywordList -> cardKeywordList.value.map { CardKeyword(cardKeywordList.key.toLong(), it.toLong()) } }
}