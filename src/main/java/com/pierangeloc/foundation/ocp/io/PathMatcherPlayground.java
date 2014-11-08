package com.pierangeloc.foundation.ocp.io;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;

/**
 * Created by pierangeloc on 8-11-14.
 */
public class PathMatcherPlayground {


    private static boolean matches(Path p, String glob) {
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(glob);
        return matcher.matches(p);
    }

    private static void checkGlobs() {
        //matching works differently per FS so the matcher must come from FileSystems and not from Files
        Path p  = Paths.get("com/pierangeloc/foundation/ocp/io/PathMatcherPlayground.java");
        System.out.println("path: " + p);
        System.out.println("matches glob:*.* : " + matches(p, "glob:*.*")); //false
        System.out.println("matches glob:*.java : " + matches(p, "glob:*.java")); //false
        System.out.println("matches glob:**.java : " + matches(p, "glob:**.java")); //true
        System.out.println("matches glob:com/*/*/ocp/**.java : " + matches(p, "glob:com/*/*/ocp/**.java")); //true
        System.out.println("matches glob:**ocp** : " + matches(p, "glob:**ocp**")); //true

        Path fileJava = Paths.get("program.java");
        Path fileJawa = Paths.get("program.jawa");
        Path fileJa5a = Paths.get("program.ja5a");
        System.out.println("program.java matches glob:program.???? : " + matches(fileJava, "glob:program.????")); //true
        System.out.println("program.jawa matches glob:program.???? : " + matches(fileJawa, "glob:program.????")); //true
        System.out.println("program.java matches glob:program.{java,jawa} : " + matches(fileJava, "glob:program.{java,jawa}")); //true
        System.out.println("program.jawa matches glob:program.{java,jawa} : " + matches(fileJawa, "glob:program.{java,jawa}")); //true
        System.out.println("program.java matches glob:program.ja[vw]a : " + matches(fileJava, "glob:program.ja[vw]a")); //true
        System.out.println("program.jawa matches glob:program.ja[vw]a : " + matches(fileJawa, "glob:program.ja[vw]a")); //true
        System.out.println("program.jawa matches glob:ja[0-9a-zA-Z]a : " + matches(fileJawa, "glob:program.ja[0-9a-zA-Z]a")); //true
        System.out.println("program.jawa matches glob:ja[0-9a-zA-Z]a : " + matches(fileJa5a, "glob:program.ja[0-9a-zA-Z]a")); //true

    }


    public static void main(String[] args) {
        checkGlobs();
    }


}
