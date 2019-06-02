<?php
class Instituicao {
    private $email;
    private $senha;
    private $cnpj;
    private $razao;
    private $endereco;
    private $num_ende;
    private $bairro;
    private $cidade;
    private $uf;
    private $cep;
    private $telefone;
    
    function getTelefone() {
        return $this->telefone;
    }

    function setTelefone($telefone) {
        $this->telefone = $telefone;
    }

                
    function getEmail() {
        return $this->email;
    }

    function getSenha() {
        return $this->senha;
    }

    function getCnpj() {
        return $this->cnpj;
    }

    function getRazao() {
        return $this->razao;
    }

    function getEndereco() {
        return $this->endereco;
    }

    function getNum_ende() {
        return $this->num_ende;
    }

    function getBairro() {
        return $this->bairro;
    }

    function getCidade() {
        return $this->cidade;
    }

    function getUf() {
        return $this->uf;
    }

    function getCep() {
        return $this->cep;
    }

    function setEmail($email) {
        $this->email = $email;
    }

    function setSenha($senha) {
        $this->senha = $senha;
    }

    function setCnpj($cnpj) {
        $this->cnpj = $cnpj;
    }

    function setRazao($razao) {
        $this->razao = $razao;
    }

    function setEndereco($endereco) {
        $this->endereco = $endereco;
    }

    function setNum_ende($num_ende) {
        $this->num_ende = $num_ende;
    }

    function setBairro($bairro) {
        $this->bairro = $bairro;
    }

    function setCidade($cidade) {
        $this->cidade = $cidade;
    }

    function setUf($uf) {
        $this->uf = $uf;
    }

    function setCep($cep) {
        $this->cep = $cep;
    }
}