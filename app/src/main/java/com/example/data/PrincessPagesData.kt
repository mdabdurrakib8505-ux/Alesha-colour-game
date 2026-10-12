package com.example.data

import com.example.model.OutlinePathData
import com.example.model.PrincessPage
import com.example.model.RegionPathData

object PrincessPagesData {

    // Page 1: Royal Princess & Castle
    val royalPrincessPage = PrincessPage(
        id = "page_royal_princess",
        title = "Royal Princess & Tiara",
        subtitle = "A lovely princess with golden tiara and royal castle",
        category = "Royal Court",
        emoji = "👑",
        difficulty = "Easy",
        regions = listOf(
            // Background Sky
            RegionPathData(
                id = "sky",
                name = "Castle Sky",
                pathCommands = "M 0,0 L 1000,0 L 1000,450 L 0,450 Z",
                defaultColorArgb = 0xFFF3E5F5
            ),
            // Left Castle Tower
            RegionPathData(
                id = "castle_left",
                name = "Left Castle Tower",
                pathCommands = "M 80,450 L 80,180 L 160,180 L 160,450 Z",
                defaultColorArgb = 0xFFEDE7F6
            ),
            // Left Tower Roof
            RegionPathData(
                id = "roof_left",
                name = "Left Tower Roof",
                pathCommands = "M 60,180 L 120,80 L 180,180 Z",
                defaultColorArgb = 0xFFBA68C8
            ),
            // Right Castle Tower
            RegionPathData(
                id = "castle_right",
                name = "Right Castle Tower",
                pathCommands = "M 840,450 L 840,180 L 920,180 L 920,450 Z",
                defaultColorArgb = 0xFFEDE7F6
            ),
            // Right Tower Roof
            RegionPathData(
                id = "roof_right",
                name = "Right Tower Roof",
                pathCommands = "M 820,180 L 880,80 L 940,180 Z",
                defaultColorArgb = 0xFFBA68C8
            ),
            // Castle Center Wall
            RegionPathData(
                id = "castle_center",
                name = "Castle Wall",
                pathCommands = "M 160,450 L 160,260 L 840,260 L 840,450 Z",
                defaultColorArgb = 0xFFD1C4E9
            ),
            // Castle Gate
            RegionPathData(
                id = "castle_gate",
                name = "Castle Gate",
                pathCommands = "M 420,450 L 420,330 Q 500,280 580,330 L 580,450 Z",
                defaultColorArgb = 0xFF8D6E63
            ),
            // Princess Hair - Back
            RegionPathData(
                id = "hair_back",
                name = "Flowing Royal Hair",
                pathCommands = "M 320,380 C 240,460 220,650 260,780 C 310,690 340,600 370,520 L 630,520 C 660,600 690,690 740,780 C 780,650 760,460 680,380 Z",
                defaultColorArgb = 0xFFFFD54F
            ),
            // Princess Hair - Bangs & Side Curls
            RegionPathData(
                id = "hair_front",
                name = "Hair Front & Curls",
                pathCommands = "M 370,330 Q 500,380 630,330 C 650,420 620,480 600,500 C 560,420 530,390 500,410 C 470,390 440,420 400,500 C 380,480 350,420 370,330 Z",
                defaultColorArgb = 0xFFFFCA28
            ),
            // Princess Face & Neck
            RegionPathData(
                id = "face",
                name = "Princess Face & Neck",
                pathCommands = "M 410,400 Q 500,380 590,400 C 590,490 550,550 500,560 C 450,550 410,490 410,400 Z",
                defaultColorArgb = 0xFFFFE4C4
            ),
            // Tiara Base
            RegionPathData(
                id = "tiara_base",
                name = "Tiara Crown Band",
                pathCommands = "M 390,320 Q 500,300 610,320 L 605,335 Q 500,315 395,335 Z",
                defaultColorArgb = 0xFFFFD700
            ),
            // Tiara Spikes
            RegionPathData(
                id = "tiara_spikes",
                name = "Tiara Crown Spikes",
                pathCommands = "M 400,320 L 430,230 L 465,290 L 500,200 L 535,290 L 570,230 L 600,320 Z",
                defaultColorArgb = 0xFFFFC107
            ),
            // Tiara Central Gem
            RegionPathData(
                id = "tiara_gem_center",
                name = "Crown Star Gem",
                pathCommands = "M 500,230 L 515,250 L 500,270 L 485,250 Z",
                defaultColorArgb = 0xFFFF4081
            ),
            // Princess Bodice / Dress Top
            RegionPathData(
                id = "dress_bodice",
                name = "Ballgown Bodice",
                pathCommands = "M 440,555 C 470,550 530,550 560,555 L 575,660 Q 500,685 425,660 Z",
                defaultColorArgb = 0xFFFF69B4
            ),
            // Dress Puff Sleeves - Left
            RegionPathData(
                id = "sleeve_left",
                name = "Left Puff Sleeve",
                pathCommands = "M 440,555 Q 360,560 375,620 Q 430,650 440,600 Z",
                defaultColorArgb = 0xFFFF80AB
            ),
            // Dress Puff Sleeves - Right
            RegionPathData(
                id = "sleeve_right",
                name = "Right Puff Sleeve",
                pathCommands = "M 560,555 Q 640,560 625,620 Q 570,650 560,600 Z",
                defaultColorArgb = 0xFFFF80AB
            ),
            // Dress Ribbon Waist Bow
            RegionPathData(
                id = "dress_bow",
                name = "Royal Ribbon Bow",
                pathCommands = "M 480,665 Q 500,655 520,665 L 545,690 Q 500,680 455,690 Z",
                defaultColorArgb = 0xFFFFD700
            ),
            // Grand Ball Gown Skirt
            RegionPathData(
                id = "dress_skirt",
                name = "Grand Royal Skirt",
                pathCommands = "M 425,660 Q 500,685 575,660 C 650,750 820,880 870,980 L 130,980 C 180,880 350,750 425,660 Z",
                defaultColorArgb = 0xFFF06292
            ),
            // Skirt Center Flounce
            RegionPathData(
                id = "skirt_flounce",
                name = "Skirt Lace Flounce",
                pathCommands = "M 460,680 Q 500,720 540,680 C 580,780 630,900 660,980 L 340,980 C 370,900 420,780 460,680 Z",
                defaultColorArgb = 0xFFF8BBD0
            )
        ),
        outlines = listOf(
            // Castle Outlines
            OutlinePathData("M 80,450 L 80,180 L 160,180 L 160,450", 4f),
            OutlinePathData("M 60,180 L 120,80 L 180,180 Z", 4f),
            OutlinePathData("M 120,80 L 120,40 L 150,55 L 120,70", 3f), // Left flag
            OutlinePathData("M 840,450 L 840,180 L 920,180 L 920,450", 4f),
            OutlinePathData("M 820,180 L 880,80 L 940,180 Z", 4f),
            OutlinePathData("M 880,80 L 880,40 L 910,55 L 880,70", 3f), // Right flag
            OutlinePathData("M 160,260 L 840,260", 4f),
            OutlinePathData("M 420,450 L 420,330 Q 500,280 580,330 L 580,450", 4f),
            // Princess Hair Outlines
            OutlinePathData("M 320,380 C 240,460 220,650 260,780", 4f),
            OutlinePathData("M 680,380 C 760,460 780,650 740,780", 4f),
            OutlinePathData("M 370,330 Q 500,380 630,330 C 650,420 620,480 600,500 C 560,420 530,390 500,410 C 470,390 440,420 400,500 C 380,480 350,420 370,330 Z", 4f),
            // Princess Face & Features
            OutlinePathData("M 410,400 Q 500,380 590,400 C 590,490 550,550 500,560 C 450,550 410,490 410,400 Z", 4f),
            // Eyes, Smile & Blush
            OutlinePathData("M 445,455 Q 460,445 475,455", 3.5f), // Left eye
            OutlinePathData("M 525,455 Q 540,445 555,455", 3.5f), // Right eye
            OutlinePathData("M 445,445 Q 460,435 475,445", 2.5f), // Left eyebrow
            OutlinePathData("M 525,445 Q 540,435 555,445", 2.5f), // Right eyebrow
            OutlinePathData("M 485,505 Q 500,520 515,505", 3.5f), // Smile
            // Tiara
            OutlinePathData("M 390,320 Q 500,300 610,320 L 605,335 Q 500,315 395,335 Z", 3.5f),
            OutlinePathData("M 400,320 L 430,230 L 465,290 L 500,200 L 535,290 L 570,230 L 600,320 Z", 4f),
            OutlinePathData("M 500,230 L 515,250 L 500,270 L 485,250 Z", 3f),
            // Dress & Sleeves
            OutlinePathData("M 440,555 C 470,550 530,550 560,555 L 575,660 Q 500,685 425,660 Z", 4f),
            OutlinePathData("M 440,555 Q 360,560 375,620 Q 430,650 440,600", 4f),
            OutlinePathData("M 560,555 Q 640,560 625,620 Q 570,650 560,600", 4f),
            OutlinePathData("M 425,660 Q 500,685 575,660 C 650,750 820,880 870,980 L 130,980 C 180,880 350,750 425,660", 4.5f),
            OutlinePathData("M 460,680 Q 500,720 540,680 C 580,780 630,900 660,980 M 460,680 C 420,780 370,900 340,980", 3.5f)
        )
    )

    // Page 2: Mermaid Princess of the Sea
    val mermaidPrincessPage = PrincessPage(
        id = "page_mermaid_princess",
        title = "Mermaid Princess & Starfish",
        subtitle = "Magical undersea princess with pearl crown and coral friends",
        category = "Ocean Magic",
        emoji = "🧜‍♀️",
        difficulty = "Medium",
        regions = listOf(
            // Ocean Background
            RegionPathData(
                id = "ocean_bg",
                name = "Deep Ocean Waters",
                pathCommands = "M 0,0 L 1000,0 L 1000,1000 L 0,1000 Z",
                defaultColorArgb = 0xFFE0F7FA
            ),
            // Coral Reef - Left
            RegionPathData(
                id = "coral_left",
                name = "Sea Coral Left",
                pathCommands = "M 0,1000 L 0,720 Q 60,690 90,750 Q 140,680 160,780 L 180,1000 Z",
                defaultColorArgb = 0xFFFF8A65
            ),
            // Coral Reef - Right
            RegionPathData(
                id = "coral_right",
                name = "Sea Coral Right",
                pathCommands = "M 1000,1000 L 1000,740 Q 940,710 910,770 Q 860,700 840,800 L 820,1000 Z",
                defaultColorArgb = 0xFFFF7043
            ),
            // Mermaid Hair
            RegionPathData(
                id = "mermaid_hair",
                name = "Ocean Wave Hair",
                pathCommands = "M 380,240 C 260,320 220,540 250,700 C 310,650 330,550 380,480 L 620,480 C 670,550 690,650 750,700 C 780,540 740,320 620,240 Z",
                defaultColorArgb = 0xFFE91E63
            ),
            // Sea Star Crown
            RegionPathData(
                id = "crown_star",
                name = "Pearl Sea Crown",
                pathCommands = "M 420,210 Q 500,180 580,210 L 570,235 Q 500,205 430,235 Z",
                defaultColorArgb = 0xFFFFD700
            ),
            // Face & Torso
            RegionPathData(
                id = "mermaid_body",
                name = "Princess Face & Body",
                pathCommands = "M 420,260 Q 500,240 580,260 C 580,350 550,420 500,430 C 450,420 420,350 420,260 Z",
                defaultColorArgb = 0xFFFFE4C4
            ),
            // Torso Below Neck
            RegionPathData(
                id = "mermaid_torso",
                name = "Torso",
                pathCommands = "M 450,425 L 550,425 L 540,550 Q 500,565 460,550 Z",
                defaultColorArgb = 0xFFFFE4C4
            ),
            // Seashell Bra - Left
            RegionPathData(
                id = "shell_left",
                name = "Left Seashell",
                pathCommands = "M 440,460 Q 480,440 495,475 Q 470,505 440,490 Z",
                defaultColorArgb = 0xFFBA68C8
            ),
            // Seashell Bra - Right
            RegionPathData(
                id = "shell_right",
                name = "Right Seashell",
                pathCommands = "M 560,460 Q 520,440 505,475 Q 530,505 560,490 Z",
                defaultColorArgb = 0xFFBA68C8
            ),
            // Mermaid Tail - Scales Body
            RegionPathData(
                id = "tail_body",
                name = "Shimmering Mermaid Tail",
                pathCommands = "M 460,550 Q 500,565 540,550 C 570,670 540,780 470,850 C 420,780 430,670 460,550 Z",
                defaultColorArgb = 0xFF4DD0E1
            ),
            // Mermaid Fluke Fin - Left
            RegionPathData(
                id = "fin_left",
                name = "Left Tail Fin",
                pathCommands = "M 470,850 C 420,880 340,900 320,980 C 390,980 450,920 470,870 Z",
                defaultColorArgb = 0xFF80DEEA
            ),
            // Mermaid Fluke Fin - Right
            RegionPathData(
                id = "fin_right",
                name = "Right Tail Fin",
                pathCommands = "M 470,850 C 520,880 600,900 620,980 C 550,980 490,920 470,870 Z",
                defaultColorArgb = 0xFF80DEEA
            ),
            // Starfish Friend
            RegionPathData(
                id = "starfish",
                name = "Friendly Sea Star",
                pathCommands = "M 220,420 L 235,455 L 270,455 L 240,475 L 255,510 L 220,490 L 185,510 L 200,475 L 170,455 L 205,455 Z",
                defaultColorArgb = 0xFFFFAB91
            )
        ),
        outlines = listOf(
            // Coral Reef Outlines
            OutlinePathData("M 0,1000 L 0,720 Q 60,690 90,750 Q 140,680 160,780 L 180,1000", 3.5f),
            OutlinePathData("M 1000,1000 L 1000,740 Q 940,710 910,770 Q 860,700 840,800 L 820,1000", 3.5f),
            // Mermaid Hair & Body
            OutlinePathData("M 380,240 C 260,320 220,540 250,700", 4f),
            OutlinePathData("M 620,240 C 740,320 780,540 750,700", 4f),
            OutlinePathData("M 420,260 Q 500,240 580,260 C 580,350 550,420 500,430 C 450,420 420,350 420,260 Z", 4f),
            OutlinePathData("M 450,425 L 450,550 M 550,425 L 550,550", 4f),
            // Eyes & Smile
            OutlinePathData("M 455,320 Q 470,310 485,320", 3.5f),
            OutlinePathData("M 515,320 Q 530,310 545,320", 3.5f),
            OutlinePathData("M 485,365 Q 500,380 515,365", 3.5f),
            // Shells
            OutlinePathData("M 440,460 Q 480,440 495,475 Q 470,505 440,490 Z", 3.5f),
            OutlinePathData("M 560,460 Q 520,440 505,475 Q 530,505 560,490 Z", 3.5f),
            // Tail & Fins
            OutlinePathData("M 460,550 Q 500,565 540,550 C 570,670 540,780 470,850 C 420,780 430,670 460,550 Z", 4.5f),
            OutlinePathData("M 470,850 C 420,880 340,900 320,980 C 390,980 450,920 470,870", 4f),
            OutlinePathData("M 470,850 C 520,880 600,900 620,980 C 550,980 490,920 470,870", 4f),
            // Starfish
            OutlinePathData("M 220,420 L 235,455 L 270,455 L 240,475 L 255,510 L 220,490 L 185,510 L 200,475 L 170,455 L 205,455 Z", 3.5f),
            // Sea Bubbles
            OutlinePathData("M 780,280 Q 800,260 820,280 Q 820,300 800,300 Q 780,300 780,280", 2.5f),
            OutlinePathData("M 820,220 Q 835,205 850,220 Q 850,235 835,235 Q 820,235 820,220", 2.5f)
        )
    )

    // Page 3: Ice Princess & Crystal Snowflake
    val icePrincessPage = PrincessPage(
        id = "page_ice_princess",
        title = "Ice Princess & Snow Magic",
        subtitle = "Winter royalty holding a sparkling crystal snowflake",
        category = "Frozen Realm",
        emoji = "❄️",
        difficulty = "Medium",
        regions = listOf(
            // Mist Sky
            RegionPathData(
                id = "ice_sky",
                name = "Winter Sky",
                pathCommands = "M 0,0 L 1000,0 L 1000,1000 L 0,1000 Z",
                defaultColorArgb = 0xFFE0F7FA
            ),
            // Ice Crystal Tiara
            RegionPathData(
                id = "ice_tiara",
                name = "Crystal Ice Crown",
                pathCommands = "M 410,210 L 440,130 L 470,180 L 500,110 L 530,180 L 560,130 L 590,210 Z",
                defaultColorArgb = 0xFF80DEEA
            ),
            // Braided Platinum Hair
            RegionPathData(
                id = "ice_hair",
                name = "Platinum Braid Hair",
                pathCommands = "M 400,230 Q 500,200 600,230 C 640,330 630,420 590,460 C 660,540 680,680 630,850 C 580,780 570,680 570,550 C 530,460 410,430 400,230 Z",
                defaultColorArgb = 0xFFFFF9C4
            ),
            // Face & Neck
            RegionPathData(
                id = "ice_face",
                name = "Princess Face",
                pathCommands = "M 420,250 Q 500,230 580,250 C 580,340 550,400 500,410 C 450,400 420,340 420,250 Z",
                defaultColorArgb = 0xFFFFF0E1
            ),
            // Ice Cape - Flowing Left & Right
            RegionPathData(
                id = "ice_cape",
                name = "Crystal Snow Cape",
                pathCommands = "M 390,440 L 610,440 C 720,600 840,820 880,980 L 120,980 C 160,820 280,600 390,440 Z",
                defaultColorArgb = 0xFFB2EBF2
            ),
            // Ice Gown Bodice
            RegionPathData(
                id = "ice_bodice",
                name = "Sparkling Ice Bodice",
                pathCommands = "M 440,430 L 560,430 L 550,560 Q 500,580 450,560 Z",
                defaultColorArgb = 0xFF29B6F6
            ),
            // Ice Gown Skirt
            RegionPathData(
                id = "ice_skirt",
                name = "Glistening Ice Skirt",
                pathCommands = "M 450,560 Q 500,580 550,560 C 600,680 680,840 710,980 L 290,980 C 320,840 400,680 450,560 Z",
                defaultColorArgb = 0xFF0288D1
            ),
            // Giant Magic Snowflake Held in Hand
            RegionPathData(
                id = "snowflake_core",
                name = "Magic Crystal Snowflake",
                pathCommands = "M 300,520 L 320,500 L 340,520 L 320,540 Z",
                defaultColorArgb = 0xFFFFFFFF
            )
        ),
        outlines = listOf(
            OutlinePathData("M 410,210 L 440,130 L 470,180 L 500,110 L 530,180 L 560,130 L 590,210 Z", 4f),
            OutlinePathData("M 420,250 Q 500,230 580,250 C 580,340 550,400 500,410 C 450,400 420,340 420,250 Z", 4f),
            OutlinePathData("M 400,230 Q 500,200 600,230 C 640,330 630,420 590,460", 4f),
            OutlinePathData("M 590,460 C 660,540 680,680 630,850 C 580,780 570,680 570,550", 4f),
            OutlinePathData("M 455,300 Q 470,290 485,300", 3.5f),
            OutlinePathData("M 515,300 Q 530,290 545,300", 3.5f),
            OutlinePathData("M 485,350 Q 500,365 515,350", 3.5f),
            OutlinePathData("M 440,430 L 560,430 L 550,560 Q 500,580 450,560 Z", 4f),
            OutlinePathData("M 450,560 C 400,680 320,840 290,980", 4f),
            OutlinePathData("M 550,560 C 600,680 680,840 710,980", 4f),
            OutlinePathData("M 390,440 C 280,600 160,820 120,980", 3.5f),
            OutlinePathData("M 610,440 C 720,600 840,820 880,980", 3.5f),
            // Magic 6-armed Snowflake Star
            OutlinePathData("M 320,460 L 320,580 M 260,520 L 380,520 M 275,475 L 365,565 M 275,565 L 365,475", 4f),
            OutlinePathData("M 320,480 L 305,495 M 320,480 L 335,495 M 320,560 L 305,545 M 320,560 L 335,545", 3f)
        )
    )

    // Page 4: Fairy Princess & Butterfly Wings
    val fairyPrincessPage = PrincessPage(
        id = "page_fairy_princess",
        title = "Fairy Princess & Butterfly Wings",
        subtitle = "Forest fairy princess with fluttering wings and magic wand",
        category = "Enchanted Woods",
        emoji = "✨",
        difficulty = "Medium",
        regions = listOf(
            RegionPathData(
                id = "forest_bg",
                name = "Enchanted Meadow",
                pathCommands = "M 0,0 L 1000,0 L 1000,1000 L 0,1000 Z",
                defaultColorArgb = 0xFFF1F8E9
            ),
            // Upper Left Wing
            RegionPathData(
                id = "wing_top_left",
                name = "Upper Left Fairy Wing",
                pathCommands = "M 420,420 C 320,240 120,180 80,300 C 50,420 220,500 420,520 Z",
                defaultColorArgb = 0xFFCE93D8
            ),
            // Upper Right Wing
            RegionPathData(
                id = "wing_top_right",
                name = "Upper Right Fairy Wing",
                pathCommands = "M 580,420 C 680,240 880,180 920,300 C 950,420 780,500 580,520 Z",
                defaultColorArgb = 0xFFCE93D8
            ),
            // Lower Left Wing
            RegionPathData(
                id = "wing_bottom_left",
                name = "Lower Left Fairy Wing",
                pathCommands = "M 420,530 C 260,540 140,640 180,740 C 220,800 360,720 440,650 Z",
                defaultColorArgb = 0xFFE1BEE7
            ),
            // Lower Right Wing
            RegionPathData(
                id = "wing_bottom_right",
                name = "Lower Right Fairy Wing",
                pathCommands = "M 580,530 C 740,540 860,640 820,740 C 780,800 640,720 560,650 Z",
                defaultColorArgb = 0xFFE1BEE7
            ),
            // Fairy Hair
            RegionPathData(
                id = "fairy_hair",
                name = "Flower Fairy Hair",
                pathCommands = "M 380,260 Q 500,220 620,260 C 670,360 660,460 620,520 C 580,450 560,420 500,430 C 440,420 420,450 380,520 C 340,460 330,360 380,260 Z",
                defaultColorArgb = 0xFFFFAB91
            ),
            // Face & Ears
            RegionPathData(
                id = "fairy_face",
                name = "Fairy Face",
                pathCommands = "M 420,280 Q 500,260 580,280 C 580,360 550,420 500,430 C 450,420 420,360 420,280 Z",
                defaultColorArgb = 0xFFFFE4C4
            ),
            // Flower Garland Crown
            RegionPathData(
                id = "flower_crown",
                name = "Blossom Crown",
                pathCommands = "M 410,250 Q 500,230 590,250 L 585,275 Q 500,255 415,275 Z",
                defaultColorArgb = 0xFF81C784
            ),
            // Fairy Petal Dress
            RegionPathData(
                id = "fairy_dress",
                name = "Petal Leaf Dress",
                pathCommands = "M 440,440 L 560,440 L 580,560 C 650,680 720,850 740,960 L 260,960 C 280,850 350,680 420,560 Z",
                defaultColorArgb = 0xFFA7FFEB
            ),
            // Magic Star Wand
            RegionPathData(
                id = "wand_star",
                name = "Magic Wand Star",
                pathCommands = "M 740,540 L 755,575 L 790,575 L 760,595 L 775,630 L 740,610 L 705,630 L 720,595 L 690,575 L 725,575 Z",
                defaultColorArgb = 0xFFFFD700
            )
        ),
        outlines = listOf(
            // Wings
            OutlinePathData("M 420,420 C 320,240 120,180 80,300 C 50,420 220,500 420,520", 4f),
            OutlinePathData("M 580,420 C 680,240 880,180 920,300 C 950,420 780,500 580,520", 4f),
            OutlinePathData("M 420,530 C 260,540 140,640 180,740 C 220,800 360,720 440,650", 4f),
            OutlinePathData("M 580,530 C 740,540 860,640 820,740 C 780,800 640,720 560,650", 4f),
            // Face & Hair
            OutlinePathData("M 420,280 Q 500,260 580,280 C 580,360 550,420 500,430 C 450,420 420,360 420,280 Z", 4f),
            OutlinePathData("M 455,330 Q 470,320 485,330", 3.5f),
            OutlinePathData("M 515,330 Q 530,320 545,330", 3.5f),
            OutlinePathData("M 485,375 Q 500,390 515,375", 3.5f),
            // Dress & Wand
            OutlinePathData("M 440,440 L 560,440 L 580,560 C 650,680 720,850 740,960 L 260,960 C 280,850 350,680 420,560 Z", 4f),
            OutlinePathData("M 740,540 L 755,575 L 790,575 L 760,595 L 775,630 L 740,610 L 705,630 L 720,595 L 690,575 L 725,575 Z", 3.5f),
            OutlinePathData("M 740,610 L 640,780", 4f) // Wand stick
        )
    )

    // Page 5: Royal Ballerina Princess
    val ballerinaPrincessPage = PrincessPage(
        id = "page_ballerina_princess",
        title = "Royal Ballerina Princess",
        subtitle = "Graceful dancing princess in sparkling tulle tutu",
        category = "Grand Ballroom",
        emoji = "🩰",
        difficulty = "Medium",
        regions = listOf(
            RegionPathData(
                id = "stage_bg",
                name = "Grand Theater Stage",
                pathCommands = "M 0,0 L 1000,0 L 1000,1000 L 0,1000 Z",
                defaultColorArgb = 0xFFFCE4EC
            ),
            // Ballerina Ballet Bun Hair
            RegionPathData(
                id = "bun_hair",
                name = "Ballet Bun & Curls",
                pathCommands = "M 440,160 Q 500,110 560,160 Q 590,240 500,240 Q 410,240 440,160 Z",
                defaultColorArgb = 0xFF8D6E63
            ),
            // Ballet Tiara
            RegionPathData(
                id = "swan_tiara",
                name = "Swan Feather Tiara",
                pathCommands = "M 460,200 L 500,150 L 540,200 Z",
                defaultColorArgb = 0xFFFFD700
            ),
            // Face & Arms in 5th Position
            RegionPathData(
                id = "ballerina_face_arms",
                name = "Ballerina & Arms",
                pathCommands = "M 440,240 Q 500,220 560,240 C 560,320 540,360 500,370 C 460,360 440,320 440,240 Z",
                defaultColorArgb = 0xFFFFE4C4
            ),
            // Corset Bodice
            RegionPathData(
                id = "ballet_bodice",
                name = "Sparkling Corset",
                pathCommands = "M 460,370 L 540,370 L 530,500 L 470,500 Z",
                defaultColorArgb = 0xFFFF4081
            ),
            // Layer 1 Tutu Fluff
            RegionPathData(
                id = "tutu_top",
                name = "Fluffy Tulle Tutu",
                pathCommands = "M 470,500 L 530,500 C 650,520 780,560 840,640 L 160,640 C 220,560 350,520 470,500 Z",
                defaultColorArgb = 0xFFFF80AB
            ),
            // Layer 2 Tutu Under
            RegionPathData(
                id = "tutu_bottom",
                name = "Tutu Lace Frills",
                pathCommands = "M 200,640 L 800,640 C 740,710 650,740 500,740 C 350,740 260,710 200,640 Z",
                defaultColorArgb = 0xFFF8BBD0
            ),
            // Ballet Pointe Legs & Slippers
            RegionPathData(
                id = "ballet_legs",
                name = "Pointe Shoes & Ribbons",
                pathCommands = "M 470,740 L 495,740 L 490,960 L 470,960 Z",
                defaultColorArgb = 0xFFFFE4C4
            )
        ),
        outlines = listOf(
            OutlinePathData("M 440,160 Q 500,110 560,160 Q 590,240 500,240 Q 410,240 440,160 Z", 4f),
            OutlinePathData("M 440,240 Q 500,220 560,240 C 560,320 540,360 500,370 C 460,360 440,320 440,240 Z", 4f),
            OutlinePathData("M 465,280 Q 480,270 495,280", 3.5f),
            OutlinePathData("M 505,280 Q 520,270 535,280", 3.5f),
            OutlinePathData("M 485,320 Q 500,335 515,320", 3.5f),
            // Arms curved over head
            OutlinePathData("M 440,360 C 360,320 340,180 430,120 C 470,90 530,90 570,120 C 660,180 640,320 560,360", 4f),
            // Tutu
            OutlinePathData("M 460,370 L 540,370 L 530,500 L 470,500 Z", 4f),
            OutlinePathData("M 470,500 C 350,520 220,560 160,640 L 840,640 C 780,560 650,520 530,500", 4f),
            OutlinePathData("M 200,640 C 260,710 350,740 500,740 C 650,740 740,710 800,640", 3.5f),
            // Legs on pointe
            OutlinePathData("M 480,740 L 480,950 L 495,950 L 495,740", 3.5f),
            OutlinePathData("M 515,740 L 525,920 L 540,920 L 530,740", 3.5f)
        )
    )

    // Page 6: Princess & Royal Unicorn
    val unicornPrincessPage = PrincessPage(
        id = "page_unicorn_princess",
        title = "Princess & Royal Unicorn",
        subtitle = "Magical princess and her gentle celestial unicorn friend",
        category = "Fairy Companions",
        emoji = "🦄",
        difficulty = "Easy",
        regions = listOf(
            // Rainbow Sky
            RegionPathData(
                id = "unicorn_sky",
                name = "Rainbow Heaven",
                pathCommands = "M 0,0 L 1000,0 L 1000,1000 L 0,1000 Z",
                defaultColorArgb = 0xFFFFF8E1
            ),
            // Unicorn Body
            RegionPathData(
                id = "unicorn_body",
                name = "Gentle Unicorn Coat",
                pathCommands = "M 480,480 C 480,360 580,280 680,280 C 760,280 820,340 850,420 C 880,500 860,650 820,780 C 740,840 640,820 580,750 C 500,700 480,580 480,480 Z",
                defaultColorArgb = 0xFFFFFFFF
            ),
            // Unicorn Horn
            RegionPathData(
                id = "unicorn_horn",
                name = "Spiral Alicorn Horn",
                pathCommands = "M 740,240 L 820,90 L 780,220 Z",
                defaultColorArgb = 0xFFFFD700
            ),
            // Unicorn Rainbow Mane
            RegionPathData(
                id = "unicorn_mane",
                name = "Rainbow Mane",
                pathCommands = "M 660,260 C 620,320 600,420 620,520 C 570,440 580,320 660,260 Z",
                defaultColorArgb = 0xFFFF80AB
            ),
            // Princess Hair
            RegionPathData(
                id = "princess_hair_uni",
                name = "Princess Curls",
                pathCommands = "M 220,380 Q 320,320 420,380 C 450,460 420,560 380,620 C 320,580 300,540 280,480 Z",
                defaultColorArgb = 0xFFFFD54F
            ),
            // Princess Face
            RegionPathData(
                id = "princess_face_uni",
                name = "Princess Face",
                pathCommands = "M 270,390 Q 340,370 400,390 C 400,470 370,520 335,530 C 300,520 270,470 270,390 Z",
                defaultColorArgb = 0xFFFFE4C4
            ),
            // Princess Tiara
            RegionPathData(
                id = "princess_tiara_uni",
                name = "Princess Tiara",
                pathCommands = "M 290,360 L 315,310 L 335,340 L 355,310 L 380,360 Z",
                defaultColorArgb = 0xFFFFD700
            ),
            // Princess Gown
            RegionPathData(
                id = "princess_gown_uni",
                name = "Princess Dress",
                pathCommands = "M 280,530 L 390,530 C 440,650 480,800 500,940 L 160,940 C 180,800 220,650 280,530 Z",
                defaultColorArgb = 0xFFBA68C8
            )
        ),
        outlines = listOf(
            // Unicorn Head & Horn
            OutlinePathData("M 740,240 L 820,90 L 780,220 Z", 4f),
            OutlinePathData("M 780,220 C 760,280 820,340 850,420 C 880,500 860,650 820,780", 4.5f),
            OutlinePathData("M 660,260 C 620,320 600,420 620,520", 4f),
            OutlinePathData("M 780,360 Q 795,350 810,360", 3.5f), // Unicorn eye
            // Princess
            OutlinePathData("M 290,360 L 315,310 L 335,340 L 355,310 L 380,360 Z", 3.5f),
            OutlinePathData("M 270,390 Q 340,370 400,390 C 400,470 370,520 335,530 C 300,520 270,470 270,390 Z", 4f),
            OutlinePathData("M 295,430 Q 310,420 325,430", 3.5f),
            OutlinePathData("M 345,430 Q 360,420 375,430", 3.5f),
            OutlinePathData("M 320,475 Q 335,490 350,475", 3.5f),
            OutlinePathData("M 280,530 C 220,650 180,800 160,940 L 500,940 C 480,800 440,650 390,530", 4f)
        )
    )

    // Page 7: Blank Magic Canvas
    val blankCanvasPage = PrincessPage(
        id = "page_blank_canvas",
        title = "Magic Free Draw",
        subtitle = "Create your own fairy tale princess masterpieces from imagination",
        category = "Blank Canvas",
        emoji = "🎨",
        difficulty = "Creative",
        isBlankCanvas = true
    )

    val allPages = listOf(
        royalPrincessPage,
        mermaidPrincessPage,
        icePrincessPage,
        fairyPrincessPage,
        ballerinaPrincessPage,
        unicornPrincessPage,
        blankCanvasPage
    )
}
