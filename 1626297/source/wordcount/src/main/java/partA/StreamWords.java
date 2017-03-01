package partA;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.IgniteDataStreamer;
import org.apache.ignite.Ignition;
import org.apache.ignite.stream.StreamTransformer;

import javax.cache.processor.MutableEntry;
import java.lang.Long;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by s1626297 on 07/11/16.
 */
public class StreamWords {

    public static void main(String[] args) throws Exception {
        String filePath = args[0];
        // Mark this cluster member as client.
        Ignition.setClientMode(true);

        try (Ignite ignite = Ignition.start("examples/config/example-ignite-no-discovery.xml")) {
            if ((!ExamplesUtils.hasServerNodes(ignite)) )
                return;

            // The cache is configured with sliding window holding 1 second of the streaming data.
            IgniteCache<String, Long> stmCache = ignite.getOrCreateCache(CacheConfig.wordCache());


            try (IgniteDataStreamer<String,Long> stmr = ignite.dataStreamer(stmCache.getName())) {
                stmr.allowOverwrite(true);
                //stmr.receiver(new StreamingExampleCacheEntryProcessor());
                stmr.receiver(new StreamTransformer<String, Long>() {
                    @Override public Object process(MutableEntry<String, Long> e, Object... args) {
                        // Get current count.
                        Long val = e.getValue();
                        Long num = (long)args[0];
                        e.setValue(val == null ? num : val + num);
                        return null;
                    }
                });

                while (true) {
                    //Path path = Paths.get(StreamWords.class.getResource("/pagecounts-20160801-000000").toURI());
                    Path path = Paths.get(filePath);

                    // Read words from a text file.
                    try (Stream<String> lines = Files.lines(path)) {
                        lines.forEach(line -> {
                            Stream<String> words = Stream.of(line.split(" "));
                            List<String> temps = new ArrayList<String>();
                            // Stream words into Ignite streamer.
                            words.forEach(word -> {
                                if (!word.trim().isEmpty())
                                    temps.add(word);
                            });
				if(temps.size()== 4){
                            stmr.addData(temps.get(1), Long.parseLong(temps.get(2)));
				}
                        });
                    }
                }
            }
        }
    }
}
