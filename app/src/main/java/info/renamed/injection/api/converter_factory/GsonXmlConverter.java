package info.renamed.injection.api.converter_factory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class GsonXmlConverter extends Converter.Factory {

    private final Converter.Factory xml;
    private final Converter.Factory json;

    public GsonXmlConverter(Converter.Factory xml, Converter.Factory json) {
        this.xml = xml;
        this.json = json;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        for (Annotation annotation : annotations) {
            if (annotation.annotationType() == Xml.class) {
                return xml.responseBodyConverter(type, annotations, retrofit);
            }

            if (annotation.annotationType() == Json.class) {
                return json.responseBodyConverter(type, annotations, retrofit);
            }
        }
        return null;
    }
}
