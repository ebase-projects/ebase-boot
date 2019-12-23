package me.dwliu.framework.core.tool.util;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 自定义信任管理器。
 *
 * @author ray
 */
public class MyX509TrustManager implements X509TrustManager {


    /**
     * 用于检查客户端的证书，若不信任则抛出异常。由于我们不需要对客户端进行认证，可以不做任何处理。
     *
     * @param chain
     * @param authType
     * @throws CertificateException
     */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {
    }

    /**
     * 该方法用于检查服务端的证书，若不信任则抛出异常。通过实现该方法，可以使之信任指定的任何证书。
     * 在实现该方法时，可以不做任何处理，即一个空的方法实现，由于不会抛出任何异常，它就会信任任何证书。
     *
     * @param chain
     * @param authType
     * @throws CertificateException
     */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType)
            throws CertificateException {

    }

    /**
     * 返回受信任的X509证书组。
     *
     * @return
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

}
