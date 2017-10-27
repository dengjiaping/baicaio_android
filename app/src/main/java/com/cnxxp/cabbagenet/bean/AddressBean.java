package com.cnxxp.cabbagenet.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 2017/4/6 0006.
 */

public class AddressBean implements Parcelable {

    /**
     * id : 9
     * consignee : 张三
     * address : 梅溪湖创新中心2505
     * zip : 123456
     * mobile : 18555555555
     */

    private String id;
    private String consignee;
    private String address;
    private String zip;
    private String mobile;
    private boolean normaladdress;

    public boolean isNormaladdress() {
        return normaladdress;
    }

    public void setNormaladdress(boolean normaladdress) {
        this.normaladdress = normaladdress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.consignee);
        dest.writeString(this.address);
        dest.writeString(this.zip);
        dest.writeString(this.mobile);
        dest.writeByte(this.normaladdress ? (byte) 1 : (byte) 0);
    }

    public AddressBean() {
    }

    protected AddressBean(Parcel in) {
        this.id = in.readString();
        this.consignee = in.readString();
        this.address = in.readString();
        this.zip = in.readString();
        this.mobile = in.readString();
        this.normaladdress = in.readByte() != 0;
    }

    public static final Parcelable.Creator<AddressBean> CREATOR = new Parcelable.Creator<AddressBean>() {
        @Override
        public AddressBean createFromParcel(Parcel source) {
            return new AddressBean(source);
        }

        @Override
        public AddressBean[] newArray(int size) {
            return new AddressBean[size];
        }
    };
}
