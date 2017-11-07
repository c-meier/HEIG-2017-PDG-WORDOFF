package ch.heigvd.wordoff.server.Security;

public interface SecurityConst {
    String AUTH_HEADER = "Authorization";
    String TOKEN_PREFIX = "Bearer ";
    String TOKEN_SECRET = "fpjo+i3nie0*v98ij3&2f)(a";
    int TOKEN_LENGTH_LIFE = 3_600_000;
}
