package br.com.devjmcn.projetoguarani.model.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Client implements Parcelable {
    String cod;
    String reason;
    String cnpjCpf;
    String address;
    String number;
    String complement;
    String cep;
    String district;
    String dtRegister;
    String fantasyName;
    String email;
    String secEmail;

    public Client(String cod,
                  String reason,
                  String cnpjCpf,
                  String address,
                  String number,
                  String complement,
                  String cep,
                  String district,
                  String dtRegister,
                  String fantasyName,
                  String email,
                  String secEmail) {
        this.cod = cod;
        this.reason = reason;
        this.cnpjCpf = cnpjCpf;
        this.address = address;
        this.number = number;
        this.complement = complement;
        this.cep = cep;
        this.district = district;
        this.dtRegister = dtRegister;
        this.fantasyName = fantasyName;
        this.email = email;
        this.secEmail = secEmail;
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

    public String getAddress() {
        return address;
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

    public String getDtRegister() {
        return dtRegister;
    }

    public String getFantasyName() {
        return fantasyName;
    }

    public String getEmail() {
        return email;
    }

    public String getSecEmail() {
        return secEmail;
    }

    protected Client(Parcel parcel) {
        cod = parcel.readString();
        reason = parcel.readString();
        cnpjCpf = parcel.readString();
        address = parcel.readString();
        number = parcel.readString();
        complement = parcel.readString();
        cep = parcel.readString();
        district = parcel.readString();
        dtRegister = parcel.readString();
        fantasyName = parcel.readString();
        email = parcel.readString();
        secEmail = parcel.readString();
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
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(cod);
        parcel.writeString(reason);
        parcel.writeString(cnpjCpf);
        parcel.writeString(address);
        parcel.writeString(number);
        parcel.writeString(complement);
        parcel.writeString(cep);
        parcel.writeString(district);
        parcel.writeString(dtRegister);
        parcel.writeString(fantasyName);
        parcel.writeString(email);
        parcel.writeString(secEmail);
    }
}
