package br.com.devjmcn.projetoguarani.model.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
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
    String secEmail;

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
                  String email,
                  String secEmail) {
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
        this.secEmail = secEmail;
    }

    public String getCod() { return cod; }
    public String getReason() { return reason; }
    public String getCnpjCpf() { return cnpjCpf; }
    public String getAdress() { return adress; }
    public String getNumber() { return number; }
    public String getComplement() { return complement; }
    public String getCep() { return cep; }
    public String getDistrict() { return district; }
    public String getCodMunicipality() { return codMunicipality; }
    public String getDtRegister() { return dtRegister; }
    public String getFantasyName() { return fantasyName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getSecEmail() { return secEmail; }

    protected Client(Parcel in) {
        cod = in.readString();
        reason = in.readString();
        cnpjCpf = in.readString();
        adress = in.readString();
        number = in.readString();
        complement = in.readString();
        cep = in.readString();
        district = in.readString();
        codMunicipality = in.readString();
        dtRegister = in.readString();
        fantasyName = in.readString();
        phone = in.readString();
        email = in.readString();
        secEmail = in.readString();
    }

    public static final Creator<Client> CREATOR = new Creator<Client>() {
        @Override
        public Client createFromParcel(Parcel in) {
            return new Client(in);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cod);
        dest.writeString(reason);
        dest.writeString(cnpjCpf);
        dest.writeString(adress);
        dest.writeString(number);
        dest.writeString(complement);
        dest.writeString(cep);
        dest.writeString(district);
        dest.writeString(codMunicipality);
        dest.writeString(dtRegister);
        dest.writeString(fantasyName);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(secEmail);
    }
}
