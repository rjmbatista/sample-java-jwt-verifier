package com.auth0.example;


import com.auth0.AuthenticationController;
import com.auth0.SessionUtils;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.InvalidPublicKeyException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@WebServlet(urlPatterns = {"/verify"})
public class VerifyServlet extends HttpServlet {

    private AuthenticationController authenticationController;
    private String domain;
    private String audience;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        domain = config.getServletContext().getInitParameter("com.auth0.domain");
        audience = config.getServletContext().getInitParameter("com.auth0.audience");
        try {
            authenticationController = AuthenticationControllerProvider.getInstance(config);
        } catch (UnsupportedEncodingException e) {
            throw new ServletException("Couldn't create the AuthenticationController instance. Check the configuration.", e);
        }
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        final String token = (String) SessionUtils.get(req, "accessToken");
        req.setAttribute("token", token);

        try {

            // get the kid from the access token
            DecodedJWT jwt = JWT.decode(token);
            String keyId = jwt.getKeyId();
            req.setAttribute("tokenKeyId", keyId);

            // get the kid from https://{AUTH0_DOMAIN}/.well-known/jwks.json
            JwkProvider provider = new UrlJwkProvider(domain);
            Jwk jwk = provider.get(keyId);

            // only the public key is needed to verify the token
            RSAPublicKey publicKey = (RSAPublicKey)jwk.getPublicKey();
            RSAPrivateKey privateKey = null;

            // verify the token
            Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
            JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(domain)
                .withAudience(audience) 
                .acceptLeeway(15) 
                .build();
                
            DecodedJWT decodedJWT = verifier.verify(token);

            req.setAttribute("decodedJWTKeyId", decodedJWT.getKeyId());
            req.setAttribute("decodedJWTToken", decodedJWT.getToken());
            req.setAttribute("decodedJWTHeader", decodedJWT.getHeader());
            req.setAttribute("decodedJWTPayload", decodedJWT.getPayload());
            req.setAttribute("decodedJWTSignature", decodedJWT.getSignature());

        } catch (JWTDecodeException exception){
            //Invalid token
            throw new ServletException("Invalid access token", exception);
        } catch (JWTVerificationException exception){
            throw new ServletException("JWTVerificationException", exception);
        } catch (InvalidPublicKeyException exception) { 
            throw new ServletException("InvalidPublicKeyException", exception);
        } catch (JwkException exception) { 
            throw new ServletException("JwkException", exception);
        }
        
        req.getRequestDispatcher("/WEB-INF/jsp/verify.jsp").forward(req, res);
    }

}
