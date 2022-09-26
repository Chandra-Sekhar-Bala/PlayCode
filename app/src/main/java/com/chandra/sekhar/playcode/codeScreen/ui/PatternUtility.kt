package com.chandra.sekhar.playcode.codeScreen.ui

import java.util.regex.Pattern

object PatternUtility {
    //Language Keywords
     val Type_1: Pattern = Pattern.compile(
        "\\b(abstract|boolean|break|byte|case|catch" +
                "|char|class|continue|default|do|double|else" +
                "|enum|extends|final|finally|float|for|if" +
                "|implements|import|instanceof|int|interface" +
                "|long|native|new|null|package|private|protected" +
                "|public|return|short|static|strictfp|super|switch" +
                "|synchronized|this|throw|transient|try|void|volatile|while)\\b"
    )
}