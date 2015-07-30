package org.keycloak.jose.jws;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class JWSHeader implements Serializable {
    @JsonProperty("alg")
    private Algorithm algorithm;

    @JsonProperty("typ")
    private String type;

    @JsonProperty("cty")
    private String contentType;

    @JsonProperty("kid")
    private String keyId;

    public JWSHeader() {
    }

    public JWSHeader(Algorithm algorithm, String type, String contentType) {
        this.algorithm = algorithm;
        this.type = type;
        this.contentType = contentType;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public String getType() {
        return type;
    }

    public String getContentType() {
        return contentType;
    }

    public String getKeyId() {
        return keyId;
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

    }

    public String toString() {
        try {
            return mapper.writeValueAsString(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
