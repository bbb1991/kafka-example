package me.bbb1991.service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEEncrypter;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.JWTClaimsSet;
import me.bbb1991.config.PropertyReader;
import me.bbb1991.config.PropertyReaderImpl;
import me.bbb1991.helper.KeyReader;
import me.bbb1991.model.Employee;
import me.bbb1991.producer.SimpleProducer;

import java.io.IOException;
import java.security.interfaces.RSAPublicKey;

/**
 * Created by bbb1991 on 3/12/17.
 *
 * @author Bagdat Bimaganbetov
 * @author bagdat.bimaganbetov@gmail.com
 */
public class BaseService {

    private SimpleProducer<Integer, String> producer;

    public BaseService() throws IOException {
        PropertyReader reader = new PropertyReaderImpl("application.properties");
        producer = new SimpleProducer<>(reader.getPropsAsProperties());
    }

    public void sendObjectToConsumers(Employee employee) throws Exception {
        RSAPublicKey publicKey = (RSAPublicKey) KeyReader.getPublicKey("public.der");

        JWEEncrypter encrypter = new RSAEncrypter(publicKey);

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
        builder.claim("employee", employee);

        JWTClaimsSet claimsSet = builder.build();

        JWEHeader header = new JWEHeader(JWEAlgorithm.RSA_OAEP, EncryptionMethod.A128GCM);

        EncryptedJWT jwt = new EncryptedJWT(header, claimsSet);

        jwt.encrypt(encrypter);

        String s = jwt.serialize();

        producer.produce(s);
    }
}
