package me.dwliu.lab.security.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import me.dwliu.lab.common.code.SystemResultCode;

import java.io.IOException;
import java.util.Date;

/**
 * {@link org.springframework.security.oauth2.common.exceptions.OAuth2ExceptionJackson2Serializer}
 *
 * @author liudw
 * @date 2019-08-16 10:21
 **/
public class CustomOAuth2ExceptionSerializer extends StdSerializer<CustomOAuth2Exception> {

    public CustomOAuth2ExceptionSerializer() {
        super(CustomOAuth2Exception.class);
    }

    @Override
    public void serialize(CustomOAuth2Exception value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeObjectField("code", SystemResultCode.FAILURE.getCode());
        gen.writeStringField("msg", value.getMessage());
        gen.writeObjectField("timestamp", new Date());
        gen.writeStringField("data", value.getOAuth2ErrorCode());

        gen.writeEndObject();
    }


}
