package com.test.jni;

public class Example {
  static {
    try {
      System.loadLibrary("example");
    } catch (Exception e) {
      throw new RuntimeException("Could not load Example JNI Lib");
    }
  }

  // Send a string to get printed
  public native void PrintMessage(String message);

  // Do a network API call and return the pokemons id (0 if it does not exist)
  public native Integer GetPokemonId(String name);

}
