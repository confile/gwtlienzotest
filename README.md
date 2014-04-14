gwtlienzotest
=============

A comparison between [Lienzo 1.2.4][1] and [KineticJS 5.10][2]




  [1]: https://github.com/emitrom/lienzo
  [2]: https://github.com/ericdrowell/KineticJS

###Load image into canvas


####Test 1 (Dev Mode): Image jpg 7.6 MB

Lienzo: 1752 ms
KineticJS: 200 ms

KineticJS is 8.76 times faster in loading images into the canvas.

####Test 2 (Prod Mode): Image jpg 7.6 MB

Lienzo: 1580 ms
KineticJS: 180 ms
