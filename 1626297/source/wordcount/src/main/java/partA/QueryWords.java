package partA;

import org.apache.ignite.*;
import org.apache.ignite.cache.query.SqlFieldsQuery;
import org.apache.ignite.configuration.CacheConfiguration;
import java.util.List;


/**
 * Created by s1626297 on 07/11/16.
 */
public class QueryWords {
    public static void main(String[] args) throws Exception {
        // Mark this cluster member as client.
        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start("examples/config/example-ignite-no-discovery.xml")) {
            if(!ExamplesUtils.hasServerNodes(ignite))
                return;
            CacheConfiguration<String,Long> cfg = CacheConfig.wordCache();
            try(IgniteCache<String, Long> stmCache = ignite.getOrCreateCache(cfg)) {
                // Select top 10 words.
                SqlFieldsQuery top10Qry = new SqlFieldsQuery(
                        //"select _key, count(_key) as cnt from Long group by _key order by cnt desc limit 10",
                        "select _val, _key  from Long order by _val desc limit 10",
                        true);

                while (true) {
                    // Execute queries.
                    List<List<?>> top10 = stmCache.query(top10Qry).getAll();

                    // Print top 10 words.
                    ExamplesUtils.printQueryResults(top10);

                    Thread.sleep(10000);
                }
            }
            finally {
                ignite.destroyCache(cfg.getName());
            }
        }
    }
}
