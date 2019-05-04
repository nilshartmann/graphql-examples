package nh.graphql.beeradvisor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class Utils {

    public static List<String> listOf(String... elements) {
        List<String> result = new LinkedList<String>();
        Collections.addAll(result, elements);

        return result;
    }

}
