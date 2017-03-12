package me.bbb1991.servlet;

import javax.ws.rs.core.Application;
import java.util.Collections;
import java.util.Set;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class Resources extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        return Collections.singleton(EntryPoint.class);
    }
}