/*
 * File: SecurityConst.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Security;

/**
 * Security constants used for the generation and authentification of JWT token.
 */
public interface SecurityConst {
    String AUTH_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_SECRET = "fpjo+i3nie0*v98ij3&2f)(a";
    long TOKEN_LENGTH_LIFE = 36_000_000; // 1 hour
}
