#! /bin/bash
cd public/assets/beer && rm *-thumb.jpg; for i in *.jpg; do convert -thumbnail 256x256\^ -gravity center -extent 256x256 $i `basename $i .jpg`-256x256-thumb.jpg; done
