package ru.aston.notification.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Компонент для обработки шаблонов писем.
 * Загружает шаблоны из classpath и заменяет плейсхолдеры на реальные значения.
 */
@Component
public class TemplateProcessor {

    /**
     * Загружает шаблон из classpath и заменяет плейсхолдеры на указанные значения.
     *
     * @param templatePath путь к файлу шаблона в classpath
     * @param placeholders map плейсхолдеров, где ключ - имя плейсхолдера,
     *                     а значение - текст для замены
     * @return обработанный шаблон с подставленными значениями
     * @throws IOException если возникла ошибка при чтении файла шаблона
     * @throws IllegalArgumentException если templatePath или placeholders равны null
     */
    public String loadAndFillTemplate(String templatePath, Map<String, String> placeholders) throws IOException {
        if (templatePath == null || placeholders == null) {
            throw new IllegalArgumentException("Параметры templatePath и placeholders не могут быть null");
        }

        var resource = new ClassPathResource(templatePath);
        String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        return content;
    }
}