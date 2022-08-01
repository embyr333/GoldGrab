# GoldGrab

First commit & upload of last-made version/intermediate of provisionally-shelved ~2015 experiment with an idea of a non-very-exciting 'rapid estimation' challenge (I wouldn't dare call it a game). (Executable JAR is linked at http://embyrne.c1.biz/. Development was not previously under version control, so previous code intermediates are just store in a primitive fashion on my laptop.

The GoldGrab player is burgling a stash of gold bars and must drag what they want to touch the top of the window. They must also leave an at-least-equal quantity of gold to give to the mafia. Estimation can be made easier by lining the bars dargged to the top or bottom up next to each other as they are 'blocked' from moving over each other there, though, due to a technical glitch, this requires release and re-depression of the mouse button. The player aims to get as near as possible to half - any more and they are executed.

Dependencies: just JDK (made with version 7).
Resources: 3 sound files.

Note re build tools etc.: While I plan to use Maven in the future, for tecnhnical reasons I am not in a position to do so yet, so am just uploading source code files for some programs here for the moment. Also, the files for this program have not (yet) been put in package(s).

Technical notes mainly to self: There are probably some out-by-one errors in expressions representing positions of right and bottom of components, though I think these would only make barely noticeable differences in positions to which the bars are dragged. Will correct if doing further work. Also, ‘Divvier’ classes could be replaced with versions I modified recently (mid-2022), in Divvier program to change class and variable names.
