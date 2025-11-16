package mock;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ezy.collect.utils.JsonHelper;

public class MvcMock {

    private static final MappingJackson2HttpMessageConverter messageConverter;

    static {
        messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(JsonHelper.getObjectMapper());
    }

    public static MockMvc getMockMvc(Object setup) {
        return MockMvcBuilders
                .standaloneSetup(setup)
                .setMessageConverters(messageConverter)
                .build();
    }

    public static MockMvc getMockMvc(Object setup, Object... advices) {
        return MockMvcBuilders
                .standaloneSetup(setup)
                .setControllerAdvice(advices)
                .setMessageConverters(messageConverter)
                .build();
    }
}
