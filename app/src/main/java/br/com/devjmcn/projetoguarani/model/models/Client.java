package br.com.devjmcn.projetoguarani.model.models;

//NÃO SABIA SE PODERIA USAR LOMBOK, FIZ NA MÃO MESMO.
public class Client {
    String cod;
    String reason;
    String cnpjCpf;
    String adress;
    String number;
    String complement;
    String cep;
    String district;
    String codMunicipality;
    String dtRegister;
    String fantasyName;
    String phone;
    String email;

    public Client(String cod,
                  String reason,
                  String cnpjCpf,
                  String adress,
                  String number,
                  String complement,
                  String cep,
                  String district,
                  String codMunicipality,
                  String dtRegister,
                  String fantasyName,
                  String phone,
                  String email) {
        this.cod = cod;
        this.reason = reason;
        this.cnpjCpf = cnpjCpf;
        this.adress = adress;
        this.number = number;
        this.complement = complement;
        this.cep = cep;
        this.district = district;
        this.codMunicipality = codMunicipality;
        this.dtRegister = dtRegister;
        this.fantasyName = fantasyName;
        this.phone = phone;
        this.email = email;
    }

    public String getCod() {
        return cod;
    }

    public String getReason() {
        return reason;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public String getAdress() {
        return adress;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getCep() {
        return cep;
    }

    public String getDistrict() {
        return district;
    }

    public String getCodMunicipality() {
        return codMunicipality;
    }

    public String getDtRegister() {
        return dtRegister;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
