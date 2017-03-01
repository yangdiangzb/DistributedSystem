package partC;

import org.apache.ignite.Ignite;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 *
 */
public class ExamplesUtils {
    /**
     * Checks if cluster has server nodes.
     *
     * @param ignite Ignite instance.
     * @return {@code True} if cluster has server nodes, {@code false} otherwise.
     */
    public static boolean hasServerNodes(Ignite ignite) {
        if (ignite.cluster().forServers().nodes().isEmpty()) {
            System.err.println("Server nodes not found (start data nodes with ExampleNodeStartup class)");

            return false;
        }

        return true;
    }

    /**
     * Convenience method for printing query results.
     *
     * @param res Query results.
     */
    public static void printQueryResults(List<?> res) {
        if (res == null || res.isEmpty())
            System.out.println("Query result set is empty.");
        else {
            try {
                PrintWriter outputStream = new PrintWriter(new FileOutputStream("../../../../../log/log-partC.txt", true));
                Long time = System.currentTimeMillis()/1000L;
                for (Object row : res) {
                    if (row instanceof List) {
                        outputStream.print(time + ":");
                        System.out.print(time + ":");
                        List<?> l = (List)row;

                        for (int i = 0; i < l.size(); i++) {
                            Object o = l.get(i);

                            if (o instanceof Double || o instanceof Float) {
                                outputStream.printf("%.2f", o);
                                System.out.printf("%.2f", o);
                            }
                            else {
                                outputStream.print(l.get(i));
                                System.out.print(l.get(i));
                            }
                            if (i + 1 != l.size()) {
                                outputStream.print(':');
                                System.out.print(':');
                            }
                        }
                        outputStream.println("");
                        System.out.println("");
                    }
                    else {
                        outputStream.print("  " + row);
                        System.out.println("  " + row);
                    }
                }
                outputStream.close();
            } catch (IOException e) {

            }

        }
    }
}
