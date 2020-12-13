GUI �alekoh�ad

N�vod na pou�itie

1. spustenie aplik�cie
- na vyvoj aplikacie bolo pouzite IDE Idea a build-automation nastroj Gradle.
- vo vyvoji sa da pokracovat v Idea-y jednoducho pomocou nasledujuceho navodu: https://www.jetbrains.com/help/idea/gradle.html#gradle_import_project_start.

2. pr�ca s aplik�ciou
- aplik�cie je rozdelen� do blokov Axis (Polar & Declination), Dome, Filterwheel, Target, Camera, Graphics, Operations, Others

a) Axis
- ovl�danie Polar a Declination axis
- tla�idl� bez vstupn�ch hodn�t : Enable Motors, Stop RA, Stop DE, Stop RA & DE,
    Calibrate to zenith, Calibrate, Correction 
- tla�idl� so vstupn�m po�om : Slew RA, Slew DE
- v �asti Shortcuts je mo�n� si pozrie� v�etky kl�vesov� skratky aplik�cie na vykonanie 
    jednotliv�ch �konov a z�rove� hodnota vstupov pre dan� �kony (v pr�pade, �e je o�ak�van� 
    vstup. V inom pr�pade sa vyhpd� notifik�cia o neplatnom vstupe)

b) Dome
- ovl�danie Dome
- tla�idl� bez vstupn�ch hodn�t: Dome West, Dome Stop, Dome East, Synchronize
- tla�idl� so vstupn�mi hodnotami: Frequency, Azimuth
- podobne ako v �asti "a)", kl�vesov� skratky a vstupy sa nach�dzaj� v �asti Shortcuts

c) Target
- ovl�danie Target
- tla�idl� bez vstupn�ch hodn�t: Go To/Cancel target, Pole crossing ON/OFF
- tla�idl� so vstupn�mi hodnotami: Load target RA, Load target DE (zodpovedaj� GoTo funkcii, ktor� akceptuje
    vstupy vo form�te DD.DDD / HH:MM:SS.S - zatia� neimplementovan� na backende)
- podobne ako v �asti "a)", kl�vesov� skratky a vstupy sa nach�dzaj� v �asti Shortcuts


d) FilterWheel
- ovl�danie Filterwheel
- tla�idlo select box filter: je mo�n� si vybra� �ubovo�n� filter, ktor� je ozna�en� p�smenom + farbou
- podobne ako v �asti "a)", kl�vesov� skratky a vstupy sa nach�dzaj� v �asti Shortcuts


e) Camera
- ovl�danie Camera
- tla�idlo select box Image type: je mo�n� si vybra� �ubovo�n� typ sn�mky
- tla�idlo select box Camera mode: je mo�n� si vybra� �ubovo�n� m�d kamery
- tla�idl� bez vstupn�ch hodn�t: Expose sequence, Expose from nearest second, Abort imaging
- tla�idl� so vstupn�mi hodnotami: Exposure time, Cooler setpoint, Exposure delay, Sequence repeats, Notes, Observer, Object
- podobne ako v �asti "a)", kl�vesov� skratky a vstupy sa nach�dzaj� v �asti Shortcuts


f) Graphics
- zobrazenie grafiky sklonu nad horizontom (vlavo)
- zobrazenie smerovania �alekoh�adu a kupoly (vpravo)

g) Operations
- obsahuje spr�vy z backendu
- zabrazuj� sa v�etky spr�vy a nie len posledn� spr�va (scrollable)

h) Others
- stav aplik�cie (connected/disconnected)
- Shortcuts - presunutie sa do okna zoznamu kl�vesov�ch skratiek
- Exit - ukon�enie aplik�cie
- Scheduler - Start/Load scheduling + progress bar
- v �avom hornom rohu je zobrazen� �as, leapsec, p a T