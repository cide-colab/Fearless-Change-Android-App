#!/bin/bash

# TODO: ignore warning, if folder exists
# TODO: not only pngs
# TODO: support various file names (with spaces and hypen..)

echo "creating drawable folders"
mkdir drawable-ldpi drawable-mdpi drawable-hdpi drawable-xhdpi drawable-xxhdpi drawable-xxxhdpi


# for each file
for pngfile in *.png
do
	echo "processing: $pngfile"
	#copy file to drawable-xxxhdpi
	cp $pngfile drawable-xxxhdpi/$pngfile
	convert $pngfile -resize 75% drawable-xxhdpi/$pngfile
	convert $pngfile -resize 50% drawable-xhdpi/$pngfile
	convert $pngfile -resize 37.5% drawable-hdpi/$pngfile
	convert $pngfile -resize 25% drawable-mdpi/$pngfile
	convert $pngfile -resize 18.75% drawable-ldpi/$pngfile
done