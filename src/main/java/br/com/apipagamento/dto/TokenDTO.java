package br.com.apipagamento.dto;

public class TokenDTO {

    private String token;

    // ✅ CONSTRUTOR
    public TokenDTO(String token) {
        this.token = token;
    }

    // opcional (bom ter)
    public TokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}