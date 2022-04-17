package cn.tragoedia.bbs.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static com.google.code.kaptcha.Constants.*;

@Configuration
public class KaptchaConfiguration {

    @Bean
    public Producer KaptchaProducer() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, "100");
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, "40");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, "32");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, "0,0,0");
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        properties.setProperty(KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
