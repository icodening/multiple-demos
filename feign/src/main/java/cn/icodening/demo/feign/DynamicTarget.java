package cn.icodening.demo.feign;

import feign.Request;
import feign.RequestTemplate;
import feign.Target;

import java.util.function.Supplier;

/**
 * @author icodening
 * @date 2025.07.02
 */
public class DynamicTarget<T> implements Target<T> {

    private final Class<T> type;

    private final String name;

    private final Supplier<String> urlSupplier;

    public DynamicTarget(Class<T> type, Supplier<String> urlSupplier) {
        this.type = type;
        this.name = type.getSimpleName();
        this.urlSupplier = urlSupplier;
    }

    @Override
    public Class<T> type() {
        return type;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String url() {
        String url = urlSupplier.get();
        if (url == null) {
            throw new IllegalArgumentException("url is null");
        }
        return url;
    }

    @Override
    public Request apply(RequestTemplate input) {
        if (input.url().indexOf("http") != 0) {
            input.target(url());
        }
        return input.request();
    }
}
