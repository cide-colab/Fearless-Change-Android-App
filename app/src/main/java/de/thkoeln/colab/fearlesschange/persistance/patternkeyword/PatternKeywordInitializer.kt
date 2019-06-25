package de.thkoeln.colab.fearlesschange.persistance.patternkeyword

import de.thkoeln.colab.fearlesschange.persistance.DataInitializer

class PatternKeywordInitializer : DataInitializer<PatternKeyword>("pattern_keyword") {

    override fun getItemValues(item: PatternKeyword?) = hashMapOf<String, Any?>(
            "patternId" to item?.patternId,
            "keywordId" to item?.keywordId
    )

    fun getValues() = hashMapOf(
            0 to listOf(1,2,3,4,5,6),
            1 to listOf(7,8,9,10,11,12,13,14),
            2 to listOf(15,16,17,18,19,20,21),
            3 to listOf(22,23,24,25,26,27,28),
            4 to listOf(29,30,31,32),
            5 to listOf(33,34,35,36,37,38),
            6 to listOf(39,40,41,42),
            7 to listOf(43,44,40,41,47,48,49,50,51),
            8 to listOf(52,39,54,55,56,57,58),
            9 to listOf(59,60,61,62,63,64,65,66,67,68,69,70,71,72),
            10 to listOf(73,74,75,76,77,78),
            11 to listOf(79,80,81,82,83,84,85),
            12 to listOf(86,87,88,47,90,91,92,93,94,95,96,97,98,99),
            13 to listOf(100,102,103,104,105,106),
            14 to listOf(107,108,109,110,111,112,18,114),
            15 to listOf(115,43,40,41,47,50,51,122,123,124),
            16 to listOf(125,126,127,128,33,130,131,10),
            17 to listOf(102,134,135,111,26,138,139,140),
            18 to listOf(141,142,143,144,145,146,147,148,149),
            19 to listOf(150,151,152,153,154,155,156,157),
            20 to listOf(158,159,160,161,162,163,164,40,41,167,168),
            21 to listOf(147,144,140,172,173,174,175,40,41),
            22 to listOf(178,140,106,181,182,183,184,185,47,40),
            23 to listOf(188,189,190,191,192,193,194,55),
            24 to listOf(196,197,198,199),
            25 to listOf(200,152,151,157,204,192,206,207),
            26 to listOf(146,75,210,211,212,40,41,140),
            27 to listOf(216,217,211,219,220,221,222,101),
            28 to listOf(63,29,22,227,228,229,230,195),
            29 to listOf(232,233,234,235,237,238),
            30 to listOf(239,240,1,242,243,244,245),
            31 to listOf(246,247,248,249,250,251,245,253,254),
            32 to listOf(145,41,257,258,248,254,87,262),
            33 to listOf(145,264,265,258,142,268,269,106),
            34 to listOf(271,272,195,274,29,276,18,278,279),
            35 to listOf(220,281,282,41,40,285,219,217,245),
            36 to listOf(289,290,291,182,293,294,245,296,297,40,41),
            37 to listOf(300,301,302,303,304,227,306),
            38 to listOf(300,308,309,310,311,312,313,314,315,316,317),
            39 to listOf(318,319,182,321,322,106),
            40 to listOf(324,102,326,327,109,329,330,331),
            41 to listOf(410,411,412),
            42 to listOf(332,333,334),
            43 to listOf(335,336,337,338),
            44 to listOf(339,340,341),
            45 to listOf(342,343,344,345),
            46 to listOf(346,347,348,349,350,351,352,353),
            47 to listOf(354,355,356,357,358,359),
            48 to listOf(360,361,362,363,153,365,366),
            49 to listOf(367,368,369,370),
            50 to listOf(371,372,373,374,375),
            51 to listOf(376,377,378),
            52 to listOf(379,217,105,382),
            53 to listOf(383,384,385),
            54 to listOf(386,387,388),
            55 to listOf(389,390,391,392),
            56 to listOf(393,394,395,396),
            57 to listOf(397,398,399,400),
            58 to listOf(401,338,403),
            59 to listOf(404,405,406),
            60 to listOf(407,408,409)
    )

    override fun getItems() =
            getValues().flatMap { patternKeywordList ->
                patternKeywordList.value.map {
                    PatternKeyword(patternKeywordList.key.toLong(), it.toLong())
                }
            }
}