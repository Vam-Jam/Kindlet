package com.test.app;

import com.amazon.kindle.kindlet.KindletContext;
import com.amazon.kindle.kindlet.ui.KTextArea;
import com.cowlark.kindlet.KindletWrapper;

import com.test.jni.Example;

public class Main extends KindletWrapper {
  @Override
  public void onKindletStart() {
    KindletContext context = getContext();
    KTextArea kta = new KTextArea("Hello, world!");
    context.getRootContainer().add(kta);

    Example exp = new Example();

    // Return a simple string
    exp.PrintMessage("Hello from java!");

    int id = exp.GetPokemonId("lopunny");

    exp.PrintMessage("Got ".concat(Integer.toString(id)).concat(" from the api!"));
  }

  @Override
  public void onKindletDestroy() {
    // Restart the CVM on exit
    // We need to do this because the JNI lib still exists after we exit, but we can
    // never load it back in
    //
    // There are some 'hacks' to load the library using our own System, but they
    // will crash the CVM if we edit the JNI in any way
    System.exit(0);
  }
}
