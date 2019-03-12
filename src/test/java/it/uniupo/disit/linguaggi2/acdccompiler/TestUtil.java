package it.uniupo.disit.linguaggi2.acdccompiler;

import java.net.URL;

public class TestUtil {

    public static String getFile(String fileName) {
        ClassLoader classLoader = TestUtil.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource != null ? resource.getFile() : null;
    }

}
