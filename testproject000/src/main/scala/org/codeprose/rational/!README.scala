package org.codeprose.rational.text
/*
 * Readme 
 * ======
 * 
 * Test for an **README Text**
 *
 * Content
 * -------
 * 
 * 1. Introduction
 * 2. Design
 * 3. How to Use
 *
 * Introduction
 * ------------
 *
 * Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. 
 *
 * Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. 
 *
 * Design
 * ------
 *
 * Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. 
 *
 * Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. 
 *
 *Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis. 
 *
 * How to Use
 * ----------
 *
 * At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat. 
 *
 * Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. 
 *
 *
 * 
 */
/*
 * 
 * 
 * 
 * Markdown Feature Overview
 * =========================
 * 
 *
 * This is taken from [John Gruber's markdown basics on daringfireball](http://daringfireball.net/projects/markdown/basics).
 *
 * 
 * A First Level Header
 * ====================
 * 
 * A Second Level Header
 * ---------------------
 * 
 * Now is the time for all good men to come to
 * the aid of their country. This is just a
 * regular paragraph.
 * 
 * The quick brown fox jumped over the lazy
 * dog's back.
 * 
 * ### Header 3 
 * 
 * > This is a blockquote.
 * > 
 * > This is the second paragraph in the blockquote.
 * >
 * > ## This is an H2 in a blockquote
 * 
 * 
 * ### Phrase Emphasis
 * 
 * Some of these words *are emphasized*.
 * Some of these words _are emphasized also_.
 * 
 * Use two asterisks for **strong emphasis**.
 * Or, if you prefer, __use two underscores instead__.
 * 
 * Lists
 * -----
 * 
 * ### Unordered Lists
 * 
 * *   Candy.
 * *   Gum.
 * *   Booze.
 * 
 * +   Candy.
 * +   Gum.
 * +   Booze
 * 
 * -   Candy.
 * -   Gum.
 * -   Booze.
 * 
 * ### Ordered Lists
 * 
 * 1. Red 
 * 2. Green
 * 3. Blue
 * 
 * 
 * ### List with paragraphs
 * 
 * *   A list item.
 * 
 *     With multiple paragraphs.
 * 
 * *   Another item in the list.
 * 
 * Links
 * -----
 * 
 * This is an [example link](http://example.com/).
 * 
 * ### Reference style
 * 
 * I get 10 times more traffic from [Google][1] than from
 * [Yahoo][2] or [MSN][3].
 * 
 * [1]: http://google.com/        "Google"
 * [2]: http://search.yahoo.com/  "Yahoo Search"
 * [3]: http://search.msn.com/    "MSN Search"
 * 
 * 
 * Code 
 * ----
 *
 * I strongly recommend against using any `<blink>` tags. 
 *
 * I wish SmartyPants used named entities like `&mdash;`
 * instead of decimal-encoded entites like `&#8212;`.
 *
 *
 */


