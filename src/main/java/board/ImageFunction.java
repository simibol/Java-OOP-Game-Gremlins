package board;

import processing.core.PImage;

@FunctionalInterface
public interface ImageFunction {
    PImage get();
}
