package dev.yuizho.springhtmlx.sse;

import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.util.HtmlUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

@Service
public class SseAsyncTaskService {
    private final ResourceLoader resourceLoader;
    private static final String DOC_FILE_PATH = "classpath:file/chika_seikatsushano_shuki.txt";

    public SseAsyncTaskService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Async
    public void readLongDoc(SseEmitter emitter) {
        try (var is = resourceLoader.getResource(DOC_FILE_PATH).getInputStream();
             var reader = new BufferedReader(new InputStreamReader(is, Charset.forName("SHIFT-JIS")));
        ) {
            String line;
            // Thread.sleepとかかけるのでlines()は使わずreadLine()は古い書き方で
            while ((line = reader.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    String data = String.format(
                            "<span>%s</span>",
                            HtmlUtils.htmlEscape(String.valueOf(c))
                    );
                    emitter.send(SseEmitter.event().name("message").data(data));
                    Thread.sleep(50);
                }
                emitter.send(SseEmitter.event().name("message").data("<br>"));
            }
            emitter.complete();
        } catch (IOException | InterruptedException e) {
            emitter.completeWithError(e);
        }
    }
}
