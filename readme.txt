GUI ïalekoh¾ad

Návod na pouitie

1. spustenie aplikácie
- na vyvoj aplikacie bolo pouzite IDE Idea a build-automation nastroj Gradle.
- vo vyvoji sa da pokracovat v Idea-y jednoducho pomocou nasledujuceho navodu: https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start.

2. práca s aplikáciou
- aplikácie je rozdelená do blokov Axis (Polar & Declination), Dome, Filterwheel, Target, Camera, Graphics, Operations, Others

a) Axis
- ovládanie Polar a Declination axis
- tlaèidlá bez vstupnıch hodnôt : Enable Motors, Stop RA, Stop DE, Stop RA & DE,
    Calibrate to zenith, Calibrate, Correction 
- tlaèidlá so vstupnım po¾om : Slew RA, Slew DE
- v èasti Shortcuts je moné si pozrie všetky klávesové skratky aplikácie na vykonanie 
    jednotlivıch úkonov a zároveò hodnota vstupov pre dané úkony (v prípade, e je oèakávanı 
    vstup. V inom prípade sa vyhpdí notifikácia o neplatnom vstupe)

b) Dome
- ovládanie Dome
- tlaèidlá bez vstupnıch hodnôt: Dome West, Dome Stop, Dome East, Synchronize
- tlaèidlá so vstupnımi hodnotami: Frequency, Azimuth
- podobne ako v èasti "a)", klávesové skratky a vstupy sa nachádzajú v èasti Shortcuts

c) Target
- ovládanie Target
- tlaèidlá bez vstupnıch hodnôt: Go To/Cancel target, Pole crossing ON/OFF
- tlaèidlá so vstupnımi hodnotami: Load target RA, Load target DE (zodpovedajú GoTo funkcii, ktorá akceptuje
    vstupy vo formáte DD.DDD / HH:MM:SS.S - zatia¾ neimplementovaná na backende)
- podobne ako v èasti "a)", klávesové skratky a vstupy sa nachádzajú v èasti Shortcuts


d) FilterWheel
- ovládanie Filterwheel
- tlaèidlo select box filter: je moné si vybra ¾ubovo¾nı filter, ktorı je oznaèenı písmenom + farbou
- podobne ako v èasti "a)", klávesové skratky a vstupy sa nachádzajú v èasti Shortcuts


e) Camera
- ovládanie Camera
- tlaèidlo select box Image type: je moné si vybra ¾ubovo¾nı typ snímky
- tlaèidlo select box Camera mode: je moné si vybra ¾ubovo¾nı mód kamery
- tlaèidlá bez vstupnıch hodnôt: Expose sequence, Expose from nearest second, Abort imaging
- tlaèidlá so vstupnımi hodnotami: Exposure time, Cooler setpoint, Exposure delay, Sequence repeats, Notes, Observer, Object
- podobne ako v èasti "a)", klávesové skratky a vstupy sa nachádzajú v èasti Shortcuts


f) Graphics
- zobrazenie grafiky sklonu nad horizontom (vlavo)
- zobrazenie smerovania ïalekoh¾adu a kupoly (vpravo)

g) Operations
- obsahuje správy z backendu
- zabrazujú sa všetky správy a nie len posledná správa (scrollable)

h) Others
- stav aplikácie (connected/disconnected)
- Shortcuts - presunutie sa do okna zoznamu klávesovıch skratiek
- Exit - ukonèenie aplikácie
- Scheduler - Start/Load scheduling + progress bar
- v ¾avom hornom rohu je zobrazenı èas, leapsec, p a T